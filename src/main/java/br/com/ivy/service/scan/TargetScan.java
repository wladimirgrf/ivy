package br.com.ivy.service.scan;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.ivy.entity.Url;
import br.com.ivy.util.WebPage;

public class TargetScan {
	
	private static final int linksNumer = 3;
	
	private static final int tryLimit = 20;
	
	private int tryNumber = 0;
	
	
	public List<Url> mappingDomain(URL host) throws IOException{
		
		URL address = host;
		
		Set<String> links = new HashSet<String>();
		Set<String> checked = new HashSet<String>();
		Set<String> sampling = new HashSet<String>();
		
		do{
			links.addAll(WebPage.linkChecker(address));
			links.removeAll(checked);
			
			for (String link : links) {
				if(sampling.size() >= linksNumer) break;
				if(link.contains("?")) sampling.add(link);
			}
			if(sampling.size() < linksNumer && links.size() > 0){
				String link = links.iterator().next();
				checked.add(link);
				links.remove(0);
				address = new URL(link);
			}
			tryNumber++;
			
		}while(sampling.size() < linksNumer && tryNumber < tryLimit );
		
		List<Url> urls = null;
		
		if(sampling.size() > 0){
			urls = new ArrayList<Url>();
			
			for(String path : sampling){
				Url url = new Url();
				url.setPath(path);
				urls.add(url);
			}
		}

		return urls;
	}
}
