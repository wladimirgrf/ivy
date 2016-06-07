package br.com.ivy.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;

import br.com.ivy.util.WebPage;

@WebServlet("/api/injection")
public class InjectionAPI extends API{
	
	private static final long serialVersionUID = 4804775703220751697L;

	private String code =  "'";
	
	private String[] links = null;
	
	private String[] exceptions = new String[]{"erro","sql","select"};
	
	
	public void execute(){
		String object = "ERROR";
		
		setParameters();
		
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
			
			boolean vulnerable = false;
			
			if(url != null && domain != null && WebPage.isReachable(domain)){
				String content = WebPage.getContent(url);
				vulnerable = (content != null ? getResult(content) : false);
			}
			results.put(originalLink, vulnerable);
		}

		object = new Gson().toJson(results);
		request.setAttribute("object", object);
	}
	
	private boolean getResult(String content){
		int error = 0;
		boolean result = false;
		content = content.toLowerCase();

		for(String exception : exceptions){
			if(error >= 2) break;
			if(content.contains(exception)) error++;
		}
		if(error >= 2) result = true;
		
		return result;
	}
	
	public void setParameters(){
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
}
