package br.com.ivy.service.gathering;

import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;

import org.apache.commons.net.whois.WhoisClient;


public class Whois {
	
	private static final String defaultWhoisServer = "whois.iana.org";

	public String get(String address) throws SocketException, IOException{
		
		String whoisServer, document;
		
		whoisServer = getWhoisElement("refer", getWhoisDocument(address, defaultWhoisServer));
		
		document = getWhoisDocument(address, whoisServer);
		
		return document;
	}
	
	private String getWhoisDocument(String address, String whoisServer) throws SocketException, IOException{
		
		WhoisClient whoisClient = new WhoisClient();
		whoisClient.connect(whoisServer);
		
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
}