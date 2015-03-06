package br.com.ivy.service.gathering;

import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;

import org.apache.commons.net.whois.WhoisClient;

import br.com.ivy.entity.Target;
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

	public String get(String address) throws SocketException, IOException{
		
		String document = getWhoisDocument(address);
		
		this.extension 	 = getWhoisElement("domain", document);
		this.whoisServer = getWhoisElement("refer", document);
		
		document = getWhoisDocument(address);
		
		return document;
	}
	
	private String getWhoisDocument(String address) throws SocketException, IOException{
		
		WhoisClient whoisClient = new WhoisClient();
		whoisClient.connect(this.whoisServer);
		
		String document = whoisClient.query(address);
		
		whoisClient.disconnect();
		
		return document;
	}
	
	private String getWhoisElement(String element, String document){
		
		String[] list = document.split("\n");
		
		Arrays.sort(list);
		
		int index = Arrays.binarySearch(list, element);
		
		return (list[-1 * index - 1].split(":")[1].trim());
	}
	
	private void mappingWhois(String document){
		
		WhoisScope scope = scopeImplementation.get(extension);
		
	
		
		if(scope == null) return;
		
		Target target = new Target();
		
		target.setOwner(getWhoisElement(scope.getOwner(), document));
		target.setPerson(getWhoisElement(scope.getPerson(), document));
		target.setEmail(getWhoisElement(scope.getEmail(), document));
		target.setRegion(getWhoisElement(scope.getRegion(), document));
		target.setChanged(getWhoisElement(scope.getChanged(), document));
		
	}
}