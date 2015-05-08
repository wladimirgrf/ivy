package br.com.ivy.service.injection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.com.ivy.util.WebPage;

public abstract class Injection {
	
	protected String code;
	protected String[] exceptions;
	
	public Map<String,Boolean> exploit(Set<String> sampling) throws MalformedURLException{
		
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		
		for(String link : sampling){
			map.put(link, getResult(WebPage.getContent(new URL(String.format("%s=%s", link.split("=")[0], code)))));
		}
		return map;
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
