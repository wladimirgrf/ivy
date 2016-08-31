package br.com.ivy.api;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import br.com.ivy.util.WebPage;

@WebServlet("/api/injection")
public class InjectionAPI extends API{
	
	private static final long serialVersionUID = 4804775703220751697L;

	@Override
	protected void clear() {
		code  = "'";
		links = new String[]{};
		exceptions = new String[]{"erro","sql","query", "syntax", "mysql"};
	}
	
	@Override
	protected void requestParameters(){
		if (request.getParameter("code") != null) {
			code = request.getParameter("code");
		}
		if (request.getParameter("links") != null) {
			links = request.getParameter("links").split(",");
		}
		if (request.getParameter("exceptions") != null) {
			exceptions = request.getParameter("exceptions").split(",");
		}
	}
	
	@Override
	protected Object requestObject(){				
		Map<String, Boolean> results = new HashMap<String, Boolean>();
		
		for(String originalLink : links){
			String link = WebPage.jsonToUTF8(originalLink);
			
			if(!link.contains("=")) continue;
			
			URL url = null;
			URL domain = null;
			
			try {
				url = new URL(String.format("%s=%s", link.split("=")[0], code));
				domain = new URL(String.format("http://%s",url.getHost()));
			} catch (MalformedURLException e) { }
			
			boolean safe = true;
			
			if(url != null && domain != null && WebPage.isReachable(domain).equals("true")){
				String content = WebPage.getContent(url);
				safe = (content != null ? getResult(content) : true);
			}
			results.put(originalLink, safe);
		}

		return results;
	}
	
	private boolean getResult(String content){
		int error = 0;
		boolean result = true;
		content = content.toLowerCase();

		for(String exception : exceptions){
			if(error >= 3) break;
			if(content.contains(exception)) error++;
		}
		if(error >= 3) result = false;
		
		return result;
	}
	
	
	//Action Properties
	
	private String code;
	
	private String[] links;
	
	private String[] exceptions;
}
