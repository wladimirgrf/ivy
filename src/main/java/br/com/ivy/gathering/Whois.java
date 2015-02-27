package br.com.ivy.gathering;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.whois.WhoisClient;

public class Whois {

	
	public String get(String domain, String ip) throws SocketException, IOException{

		String server = null;

		
		
		String extension = domain.substring(domain.lastIndexOf("."));
		
		switch (extension) {
		
			case ".br":  server = "whois.registro.br";		break;
			case ".co":  server = "whois.nic.co";			break;
			case ".com": server = "whois.verisign-grs.com";	break;
			case ".edu": server = "whois.educause.edu";		break;
			case ".gov": server = "whois.dotgov.gov";		break;
			case ".net": server = "whois.verisign-grs.com";	break;
	
			default: server = "whois.iana.org";	break;
		}
		
		
		WhoisClient whois = new WhoisClient();
		
		StringBuilder result = new StringBuilder();
		
		whois.connect("whois.iana.org");
		//result.append(whois.query(ip));
		
		
		result.append(whois.query(domain));
		
		whois.disconnect();
		
		return result.toString();
	}
}