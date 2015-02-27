package br.com.ivy.gathering;

import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;

import org.apache.commons.net.whois.WhoisClient;

public class Whois {

	
	public String get(String domain, String ip) throws SocketException, IOException{

		String server = "whois.iana.org";

		WhoisClient whois = new WhoisClient();
		
		StringBuilder result = new StringBuilder();
		
		whois.connect(server);
		//result.append(whois.query(ip));
		
		result.append(whois.query(domain));
		
		whois.disconnect();
		
		server = getWhoisElement("refer", result.toString());
		
		whois.connect(server);
		
		result.append(whois.query(domain));
		
		whois.disconnect();
		
		return result.toString();
	}
	
	
	private String getWhoisElement(String element, String document){
		
		String[] list = document.split("\n");
		
		java.util.Arrays.sort(list);
		
		int index = Arrays.binarySearch(list, element);
		
		return (list[-1 * index - 1].split(":")[1].trim());
	}
}