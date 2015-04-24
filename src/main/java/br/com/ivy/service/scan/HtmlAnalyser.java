package br.com.ivy.service.scan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class HtmlAnalyser {
	
	private Matcher matcher;
	
	private List<String> links;
	
	private Set<String> sampling;
	
	private static final int tryNumber = 5;
	
	private int listPosition;
	
	private final Pattern linkRegex = Pattern.compile("href=\"(.*?)\"");
	
	public HtmlAnalyser(){
		links 	 = new ArrayList<String>();
		sampling = new HashSet<String>();
	}

	public void linkChecker(URL host) throws IOException{

		String content = getContent(host);

		String[] lines = content.split("\n");
		
		String link = null;

		for (int i=0; i < lines.length; i++) {
			if(sampling.size() >= tryNumber) break;
			
			if(!lines[i].contains("<a")) continue;
			
			this.matcher = this.linkRegex.matcher(lines[i]);
			
			if(matcher.find()) {
				link = linkFormat(matcher.group(1), host.getHost());
				
				if(link != null){
					this.links.add(link);
					if(link.contains("?")) this.sampling.add(link);
				}
			}
		}
		
		
		if(sampling.size() < tryNumber){
			linkChecker(new URL(this.links.get(this.listPosition++)));
		}
		
		
		System.out.println(sampling);
	}

	public String linkFormat(String link, String host){
		
		if(link.startsWith("#") || link.startsWith("\\")) return null;
		
		if(link.startsWith("/")) link = String.format("http://%s%s", host, link);
		
		return link;
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
}
