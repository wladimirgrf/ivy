package br.com.ivy.service.injection;

import java.net.MalformedURLException;
import java.net.URL;

import br.com.ivy.util.WebPage;

public abstract class Injection {
	
	protected String code;
	protected String[] exceptions;
	
	public boolean exploit(String link) throws MalformedURLException{
		try{
			return getResult(WebPage.getContent(new URL(String.format("%s=%s", link.split("=")[0], code))));
		}catch(Exception e){ return false; }
	}
	
	public boolean getResult(String content){
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
