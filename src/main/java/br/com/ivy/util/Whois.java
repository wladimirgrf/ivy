package br.com.ivy.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import br.com.ivy.entity.Target;
import br.com.ivy.entity.WhoisScope;
import br.com.ivy.implementation.WhoisScopeImplementation;

public class Whois {
	
	private static final String defaultWhoisServer = "whois.iana.org";
	
	private Whois() {}
	
	public static Target get(String host) {
		
		Target target = null;
		
		String document = getDocument(host, defaultWhoisServer);
		
		if(document != null){
			String extension = getElement("domain", document);
			
			document = getDocument(host, null);
			
			if(document != null && extension != null){
				target = mappingTarget(document, host, new WhoisScopeImplementation().get(extension));
			}
		}
		
		return target;
	}
	
	private static String getDocument(String host, String whoisServer) {
		
		StringBuilder command = new StringBuilder();
		
		String result = null;
		
		command.append("whois ");
		
		if (whoisServer != null) command.append(String.format("-h %s ", whoisServer));
				
		command.append(host);
		
		try {
			
			Process process = Runtime.getRuntime().exec(command.toString());
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "ISO-8859-1"));
			
			StringBuilder document = new StringBuilder();
			
			String line = "";

	        while((line = reader.readLine()) != null) {
	        	document.append(line + "\n");
	        }
	        
	        process.destroy();
	        
	        result = document.toString();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	private static String getElement(String element, String document){
		
		String result = null;
		
		String[] list = document.split("\n");
		
		Arrays.sort(list);
		
		int index = Arrays.binarySearch(list, element);
		
		String[] value = list[-1 * index - 1].split(":");
		
		if(value.length > 1) result = value[1].trim();
			
		return result;
	}
	
	private static Target mappingTarget(String document, String host, WhoisScope scope){
		Target target = null;
		
		if(scope != null) {
			if(getElement(scope.getOwner(), document) != null){
				target = new Target();
				
				target.setHost(host);
				target.setOwner(getElement(scope.getOwner(),   	document));
				target.setCountry(getElement(scope.getCountry(),document));
				target.setChanged(getElement(scope.getChanged(),document));
				target.setPerson(getElement(scope.getPerson(),  document));
				target.setEmail(getElement(scope.getEmail(),    document));
			}
		}
		return target;
	}
}
