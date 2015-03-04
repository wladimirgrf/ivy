package br.com.ivy.gathering;

import java.io.IOException;
import java.net.*; 

public class ICMP {
	
	private String domain;
	private String ip;
	
	public boolean ping(String domain) throws IOException{
		boolean reachable = false;
		
		if (!domain.matches("^(http|https)://.*")) 
			domain = String.format("http://%s", domain);
		
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
				this.domain = url.getHost();
				ip = InetAddress.getByName(this.domain).getHostAddress(); 
			}
		}
		
		return reachable;
	}
	
	public String getDomain() {
		return domain;
	}

	public String getIp() {
		return ip;
	}
}
