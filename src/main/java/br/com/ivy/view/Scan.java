package br.com.ivy.view;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ivy.entity.Target;
import br.com.ivy.entity.Url;
import br.com.ivy.implementation.TargetImplementation;
import br.com.ivy.implementation.UrlImplementation;
import br.com.ivy.service.injection.SqlInjection;
import br.com.ivy.service.scan.TargetScan;
import br.com.ivy.util.WebPage;
import br.com.ivy.util.Whois;

@WebServlet("/scan")
public class Scan extends HttpServlet {
	
	private long today;
	
	private static final double week = 1000 * 60 * 60 * 24 * 7;
	
	private static final long serialVersionUID = -2937378935464874585L;

	public Scan() {
        super();
        
        this.today = Calendar.getInstance().getTimeInMillis();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		String domain = null;
		
		if (request.getParameter("domain") != null) {
			domain = request.getParameter("domain");
			
			if(WebPage.isReachable(domain)){
				TargetImplementation targetImplementation = new TargetImplementation();
				
				Target target = targetImplementation.get(WebPage.getHost(domain).getHost());
				
				if(target == null || (today - target.getLastScan() >= week) ){
					
					try {
						target = Whois.get(WebPage.getHost(domain).getHost());
					} catch (InterruptedException e) {}
					
					if(target != null){
						
						Map<String, Boolean> sqlMap = new SqlInjection().exploit(new TargetScan().mappingDomain(WebPage.getHost(domain)));
						
						if(sqlMap != null){

							targetImplementation.persist(target);
							
							UrlImplementation urlImplementation = new UrlImplementation();
							
							for(String key : sqlMap.keySet()){
								Url url = new Url();
								
								url.setPath(key);
								url.setSqlVulnerability(sqlMap.get(key));
								url.setTarget(target);
								urlImplementation.persist(url);
							}
						}
					}
				}
			}
		}
		request.getRequestDispatcher("/").forward(request, response);
	}
}

