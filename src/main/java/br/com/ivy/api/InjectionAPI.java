package br.com.ivy.api;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;

import br.com.ivy.util.WebPage;

@WebServlet("/api/injection")
public class InjectionAPI extends API{
	
	private static final long serialVersionUID = 4804775703220751697L;

	private String code =  "'";
	
	private String link = "";
	
	private String[] exceptions = new String[]{"erro","sql","select"};
	
	
	public void execute(){
		String object = "ERROR";
		
		setParameters();

		object = new Gson().toJson(exploit());
		request.setAttribute("object", object);
	}
	
	public boolean exploit() {
		boolean vulnerable = false;
		
		if(link.contains("=")){
			URL url = null;
			
			try {
				url = new URL(String.format("%s=%s", link.split("=")[0], code));
			} catch (MalformedURLException e) { }
			
			if(url != null && WebPage.isReachable(url)){
				String content = WebPage.getContent(url);
				vulnerable = getResult(content);
			}
		}
		return vulnerable;
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
	
	private void setParameters() {
		if (request.getParameter("code") != null) {
			code = request.getParameter("code");
		}
		if (request.getParameter("exceptions") != null) {
			exceptions = request.getParameter("exceptions").split(",");
		}
		if (request.getParameter("link") != null) {
			link = request.getParameter("link");
		}
	}
}
