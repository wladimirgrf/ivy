package br.com.ivy.service.scan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import br.com.ivy.entity.WhoisScope;
import br.com.ivy.implementation.WhoisScopeImplementation;


public class WhoisScan {
	
	private String extension;
	
	private WhoisScopeImplementation implementation;
	
	private static final String defaultWhoisServer = "whois.iana.org";
	
	public WhoisScan(){
		implementation = new WhoisScopeImplementation();
	}

	public Map<String,String> get(String host) throws IOException, InterruptedException{
		
		String document = getWhoisDocument(host, defaultWhoisServer);
		
		this.extension 	 = getWhoisElement("domain", document);
		
		document = getWhoisDocument(host, null);
		
		return mappingWhois(document, host);
	}
	
	private String getWhoisDocument(String host, String whoisServer) throws InterruptedException, IOException{
		
		StringBuilder command = new StringBuilder();
		
		command.append("whois ");
		
		if (whoisServer != null) command.append(String.format("-h %s ", whoisServer));
				
		command.append(host);
		
		Process process = Runtime.getRuntime().exec(command.toString());

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		StringBuilder document = new StringBuilder();
		
		String line = "";

        while((line = reader.readLine()) != null) {
        	document.append(line + "\n");
        }

		process.destroy();  
		      
		return document.toString();
	}
	
	private Map<String,String> mappingWhois(String document, String host){
		
		Map<String,String> map = null;
		
		WhoisScope scope = implementation.get(this.extension);
		
		if(scope != null) {
			
			if(getWhoisElement(scope.getOwner(), document) != null){
				map = new HashMap<String,String>();
				
				map.put("owner",   getWhoisElement(scope.getOwner(),   document));
				map.put("country", getWhoisElement(scope.getCountry(), document));
				map.put("changed", getWhoisElement(scope.getChanged(), document));
				map.put("person",  getWhoisElement(scope.getPerson(),  document));
				map.put("email",   getWhoisElement(scope.getEmail(),   document));
				
				map.put("host",  host);
			}
		}
		return map;
	}
	
	private String getWhoisElement(String element, String document){
		
		String result = null;
		
		String[] list = document.split("\n");
		
		Arrays.sort(list);
		
		int index = Arrays.binarySearch(list, element);
		
		String[] value = list[-1 * index - 1].split(":");
		
		if(value.length > 1) result = value[1].trim();
			
		return result;
	}
}