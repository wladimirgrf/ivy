package br.com.ivy.service.gathering;

import java.io.IOException;
import java.net.*; 

public class Icmp {
	
	private String ip;
	private String host;
	
	public boolean ping(String domain) throws IOException{
		boolean reachable = false;
		
		if (!domain.matches("^(http|https)://.*")) 
			domain = String.format("http://%s", domain);
		
		domain = domain.replace("www.", "");
		
		URL url = new URL(domain);
		
		if (url != null){
			
			Process process = Runtime.getRuntime().exec(
					String.format("ping -c 1 %s", url.getHost()));
			
			try{
				int ping = process.waitFor();
				reachable = ( ping == 0 );
			}catch(InterruptedException e){
				System.out.println(e.getMessage());
			}
						
			if(reachable){	
				this.host = url.getHost();
				ip = InetAddress.getByName(this.host).getHostAddress(); 
			}
		}
		
		return reachable;
	}
	
	public String getHost() {
		return host;
	}

	public String getIp() {
		return ip;
	}
}
