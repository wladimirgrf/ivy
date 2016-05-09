package br.com.ivy.util;

import java.io.BufferedReader;
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
	
	public static boolean isReachable(URL host) {
		boolean reachable = false;
		
	    try{
	    	HttpURLConnection connection = (HttpURLConnection) host.openConnection();
	    	connection.setRequestMethod("HEAD");
	        reachable = connection.getResponseCode() == HttpURLConnection.HTTP_OK;
	        connection.disconnect();
	        
	    } catch (Exception e){ 
	    	e.printStackTrace(); 
	    }
		return reachable;
	}
	
	public static URL getHost(String domain) {
		
		URL url = null;
		
		if (!domain.matches("^(http|https)://.*")) domain = String.format("http://%s", domain);
		
		if(domain.contains("www")) domain = domain.replace("www.", "");
		
		try {
			url = new URL(domain);
		} catch (MalformedURLException e) {
			e.printStackTrace(); 
		}
		return url;
	}
	
	public static String getContent(URL host){
		try{
			String line;
			StringBuilder content = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(host.openStream(), "UTF-8"));
	
			while ((line = reader.readLine()) != null) {
				content.append(String.format("%s\n",line));
			}
			reader.close();
	
			return content.toString();
			
		}catch(Exception e){ 
			e.printStackTrace();
			return null; 
		}
	}
	
	public static Set<String> linkChecker(URL host) {
		String content 	  = getContent(host);
		String[] lines 	  = content.split("\n");
		Set<String> links = new HashSet<String>();
		
		for (int i=0; i < lines.length; i++) {
			if(!lines[i].contains("<a") || !lines[i].contains("href")) continue;
			
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
		link = link.replace(" ", "%20");
		
		if(!link.contains(host) || link.contains("javascript")) link = null;
	
		return link;
	}
}
