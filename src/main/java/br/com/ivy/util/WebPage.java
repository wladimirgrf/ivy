package br.com.ivy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebPage {
	
	private WebPage() {}
	
	public static boolean isReachable(URL host) throws IOException{
		boolean reachable = false;
		
		HttpURLConnection connection = (HttpURLConnection) host.openConnection();
	    connection.setRequestMethod("HEAD");

	    try{
	        reachable = connection.getResponseCode() == HttpURLConnection.HTTP_OK;
	    } catch (Exception e){}

		return reachable;
	}
	
	public static URL getHost(String domain) throws MalformedURLException{
		
		if (!domain.matches("^(http|https)://.*")) domain = String.format("http://%s", domain);
		
		if(domain.contains("www")) domain = domain.replace("www.", "");
		
		return new URL(domain);
	}
	
	public static String getContent(URL host){
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
	
	
	public static Set<String> linkChecker(URL host) throws IOException{
		
		Set<String> links = new HashSet<String>();

		String content = getContent(host);

		String[] lines = content.split("\n");

		for (int i=0; i < lines.length; i++) {
			
			if(!lines[i].contains("<a")) continue;
			
			Matcher matcher = Pattern.compile("href=\"(.*?)\"").matcher(lines[i]);
			
			if(matcher.find()) {
				String link = linkFormat(matcher.group(1), host.getHost());
				if(link != null) links.add(link);
			}
		}
		return links;
	}
	
	private static String linkFormat(String link, String host){
		
		if(Pattern.matches("^[a-zA-Z/].*", link) && !link.startsWith("http")){
			link = String.format("http://%s%s", host, (link.startsWith("/") ? link : String.format("/%s",link)));
		}
		
		if(!link.contains(host)) link = null;
		
		return link;
	}
}
