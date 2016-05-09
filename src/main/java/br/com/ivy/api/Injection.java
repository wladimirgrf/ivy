package br.com.ivy.api;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;

import br.com.ivy.util.WebPage;

@WebServlet("/injection")
public class Injection extends API{
	
	private static final long serialVersionUID = 4804775703220751697L;

	private String code =  "'";
	
	private String link = "";
	
	private String[] exceptions = new String[]{"erro","sql","select"};
	
	public boolean exploit(String link) {
		boolean vulnerable = false;
		
		if(link.contains("=")){
			try {
				String content = WebPage.getContent(new URL(String.format("%s=%s", link.split("=")[0], code)));
				if (content != null && !content.isEmpty()) vulnerable = getResult(content);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return vulnerable;
	}
	
	public void execute(){
		if (request.getParameter("code") != null) {
			code = request.getParameter("code");
		}
		if (request.getParameter("exceptions") != null) {
			exceptions = request.getParameter("exceptions").split(",");
		}
		if (request.getParameter("link") != null) {
			link = request.getParameter("link");
		}
		try {
			if(!link.isEmpty() && WebPage.isReachable(new URL(link))){
				request.setAttribute("object", new Gson().toJson(exploit(link)));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
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
}
