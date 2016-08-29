package br.com.ivy.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.annotation.WebServlet;

import br.com.ivy.util.WebPage;

@WebServlet("/api/url-mapping")
public class UrlMappingAPI extends API{

	private static final long serialVersionUID = -4207305214306807800L;
	
	public UrlMappingAPI(){
		character = "";
		linksNumber = 5;
				
		links 	 = new HashSet<String>();
		checked  = new HashSet<String>();
		sampling = new HashSet<String>();
	}
	
	@Override
	protected void clear() {
		host = null;
		
		links.clear();
		checked.clear();
		sampling.clear();
	}
	
	@Override
	protected void requestParameters() {
		if (request.getParameter("character") != null) {
			character = request.getParameter("character");
		}
		if (request.getParameter("host") != null) {
			host = WebPage.getHost(request.getParameter("host")) ;
		}
		if (request.getParameter("linksNumber") != null) {
			try {
				int number = Integer.parseInt(request.getParameter("linksNumber"));
				if(number <= 20) linksNumber = number;	
			} catch (Exception e) { }
		}
	}
	
	@Override
	protected Object requestObject(){
		Set<String> result = null;
		
		if(host != null && WebPage.isReachable(host).equals("true")){
			result = map();
		}
		
		return result;
	}
	
	private Set<String> map() {
		URL address = this.host;
		
		for(int i = 0; i < tryLimit; i++){
			if(address == null) break;
			
			System.out.println(address);
			
			links.addAll(WebPage.linkChecker(address));
			links.removeAll(checked);
			
			if(links.size() == 0) break;
			
			String link = links.iterator().next();
			
			checked.add(link);
			try {
				address = new URL(link);
			} catch (MalformedURLException e) { 
				e.printStackTrace(); 
			}
		}
		
		links.addAll(checked);
		
		for (String link : links) {
			if(character.isEmpty() || link.contains(character)) sampling.add(link);
			if(sampling.size() >= linksNumber) break;
		}
		return sampling;
	}
	
	
	//Action Properties
	
	private URL host;
	
	private int linksNumber;
	
	private String character;
	
	private Set<String> links;
	
	private Set<String> checked;
	
	private Set<String> sampling;
	
	private static final long tryLimit = 40;
}
