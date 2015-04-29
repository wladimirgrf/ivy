package br.com.ivy.service.scan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TargetScan {
	
	private String ip;
	
	private URL host;
	
	private Matcher matcher;
	
	private List<String> links;
	
	private Set<String> sampling;
	
	private static final int tryNumber = 5;
	
	public TargetScan(){
		links 	 = new ArrayList<String>();
		sampling = new HashSet<String>();
	}

	public Set<String> getSampling(){
		return this.sampling;
	}
	
	public boolean isReachable(String domain) throws IOException{
		boolean reachable = false;
		
		if (!domain.matches("^(http|https)://.*")) domain = String.format("http://%s", domain);
		
		if(domain.contains("www")) domain = domain.replace("www.", "");
		
		URL url = new URL(domain);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("HEAD");

	    try{
	        reachable = connection.getResponseCode() == HttpURLConnection.HTTP_OK;
	    } catch (UnknownHostException noInternetConnection){}
	    
	    if(reachable){
        	this.host = url;
			this.ip = InetAddress.getByName(this.host.getHost()).getHostAddress();
        }
		
		return reachable;
	}

	public void linkChecker(URL host) throws IOException{

		String content = getContent(host);

		String[] lines = content.split("\n");

		for (int i=0; i < lines.length; i++) {
			if(sampling.size() >= tryNumber) break;
			
			if(!lines[i].contains("<a")) continue;
			
			this.matcher = Pattern.compile("href=\"(.*?)\"").matcher(lines[i]);
			
			if(matcher.find()) {
				String link = linkFormat(matcher.group(1), host.getHost());
				if(link != null){
					this.links.add(link);
					if(link.contains("?")) this.sampling.add(link);
				}
			}
		}
		
		if(sampling.size() < tryNumber && this.links.size() > 0){
			Collections.shuffle(links);
			String link = this.links.get(0);
			this.links.remove(0);
			linkChecker(new URL(link));
		}
	}
	
	public String getContent(URL host){
		try{
			HttpURLConnection connection = (HttpURLConnection) host.openConnection();
	
			HttpURLConnection.setFollowRedirects(false);
			connection.setRequestMethod("GET");
	
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
	
			String line;
			StringBuilder content = new StringBuilder();
	
			while ((line = reader.readLine()) != null) content.append(String.format("%s\n",line));
	
			reader.close();
			connection.disconnect();
	
			return content.toString();
		}catch(Exception e){ return null; }
	}
	
	public String linkFormat(String link, String host){
		
		if(link.startsWith("#") || link.startsWith("\\")){
			link = null;
		}else if(Pattern.matches("^[a-zA-Z/].*", link)){
			link = String.format("http://%s%s", host, (link.startsWith("/") ? link : String.format("/%s",link)));
		}else if(!link.contains(host)){
			link = null;
		}
		return link;
	}
	
	public URL getHost() {
		return host;
	}

	public String getIp() {
		return ip;
	}
}
