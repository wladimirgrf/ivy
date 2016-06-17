package br.com.ivy.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;

import br.com.ivy.util.WebPage;

@WebServlet("/api/url-mapping")
public class UrlMappingAPI extends API{

	private static final long serialVersionUID = -4207305214306807800L;

	private static final long tryLimit = 50;
	
	private URL host;
	
	private int linksNumber;
	
	private String character;
	
	private Set<String> links;
	
	private Set<String> checked;
	
	private Set<String> sampling;
	
	public UrlMappingAPI(){
		character = "";
		linksNumber = 5;
				
		links 	 = new HashSet<String>();
		checked  = new HashSet<String>();
		sampling = new HashSet<String>();
	}
	
	public void execute(){
		String object = "ERROR";
		
		clear();
		setParameters();
		
		if(host != null && WebPage.isReachable(host)){
			object = new Gson().toJson(map());
		}
		request.setAttribute("object", object);
	}
	
	private void clear(){
		links.clear();
		checked.clear();
		sampling.clear();
	}
	
	private Set<String> map() {
		URL address = this.host;
		
		for(int i = 0; i < tryLimit; i++){
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
		for (String link : checked) {
			if(character.isEmpty() || link.contains(character)) sampling.add(link);
			if(sampling.size() > linksNumber) break;
		}
		return sampling;
	}
	
	private void setParameters() {
		if (request.getParameter("character") != null) {
			character = request.getParameter("character");
		}
		if (request.getParameter("host") != null) {
			host = WebPage.getHost(request.getParameter("host")) ;
		}
		if (request.getParameter("linksNumer") != null) {
			try {
				int number = Integer.parseInt(request.getParameter("linksNumber"));
				if(number <=20) linksNumber = number;	
			} catch (Exception e) { }
		}
	}
}
