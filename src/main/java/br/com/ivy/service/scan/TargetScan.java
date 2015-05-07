package br.com.ivy.service.scan;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.ivy.util.WebPage;

public class TargetScan {
	
	private static final int tryNumber = 5;
	
	
	public Set<String> mappingDomain(URL host) throws IOException{
		
		URL url = host;
		
		List<String> links = new ArrayList<String>();
		
		Set<String> sampling = new HashSet<String>();
		
		do{
			links = WebPage.linkChecker(url);
			
			for (String link : links) {
				if(sampling.size() >= tryNumber) break;
				if(link.contains("?")) sampling.add(link);
			}
			if(sampling.size() < tryNumber && links.size() > 0){
				Collections.shuffle(links);
				String link = links.get(0);
				links.remove(0);
				url = new URL(link);
			}
		}while(sampling.size() < tryNumber);

		return sampling;
	}
}
