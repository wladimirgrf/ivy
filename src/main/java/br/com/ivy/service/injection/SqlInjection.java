package br.com.ivy.service.injection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Set;

import br.com.ivy.util.WebPage;

public class SqlInjection {
	
	
	
	public void exploit(Set<String> sampling) throws MalformedURLException{
		
		for(String link : sampling){
			getResult(WebPage.getContent(new URL(String.format("%s=%s", link.split("=")[0], "'"))));
		}
		
	}
	
	public void getResult(String content){
		int error = 0;
		boolean result = false;
		
		content = content.toLowerCase();
		
		String[] exceptions = new String[]{"error","sql"};
		for(String exception : exceptions){
			if(error >= 2) break;
			if(content.contains(exception)) error++;
		}
		
		if(error >= 2) result = true;
		System.out.println(result);
	}

}
