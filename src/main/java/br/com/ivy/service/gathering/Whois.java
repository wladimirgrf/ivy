package br.com.ivy.service.gathering;

import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.whois.WhoisClient;

import br.com.ivy.entity.WhoisScope;
import br.com.ivy.implementation.WhoisScopeImplementation;


public class Whois {
	
	private String extension;
	private String whoisServer;
	
	private WhoisScopeImplementation scopeImplementation;
	
	public Whois(){
		whoisServer = "whois.iana.org";
		scopeImplementation = new WhoisScopeImplementation();
	}

	public Map<String,String> get(String address) throws SocketException, IOException{
		
		String document = getWhoisDocument(address);
		
		this.extension 	 = getWhoisElement("domain", document);
		this.whoisServer = getWhoisElement("refer", document);
		
		document = getWhoisDocument(address);
		
		return mappingWhois(document);
	}
	
	private String getWhoisDocument(String address) throws SocketException, IOException{
		
		WhoisClient whoisClient = new WhoisClient();
		whoisClient.connect(this.whoisServer);
		
		String document = whoisClient.query(address);
		
		whoisClient.disconnect();
		
		return document;
	}
	
	private Map<String,String> mappingWhois(String document){
		
		Map<String,String> map = null;
		
		WhoisScope scope = scopeImplementation.get(extension);
		
		if(scope != null) {
			map = new HashMap<String,String>();
			
			map.put("owner",   getWhoisElement(scope.getOwner(),   document));
			map.put("country", getWhoisElement(scope.getCountry(),  document));
			map.put("changed", getWhoisElement(scope.getChanged(), document));
			map.put("person",  getWhoisElement(scope.getPerson(),  document));
			map.put("email",   getWhoisElement(scope.getEmail(),   document));
		}
		
		return map;
	}
	
	private String getWhoisElement(String element, String document){
		
		String[] list = document.split("\n");
		
		Arrays.sort(list);
		
		int index = Arrays.binarySearch(list, element);
		
		return (list[-1 * index - 1].split(":")[1].trim());
	}
}