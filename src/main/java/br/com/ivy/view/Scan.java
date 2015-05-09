package br.com.ivy.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ivy.entity.Target;
import br.com.ivy.implementation.TargetImplementation;
import br.com.ivy.service.injection.SqlInjection;
import br.com.ivy.service.scan.TargetScan;
import br.com.ivy.service.scan.WhoisScan;
import br.com.ivy.util.WebPage;

@WebServlet("/scan")
public class Scan extends HttpServlet {
	
	private static final long serialVersionUID = -2937378935464874585L;

	public Scan() {
        super();
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
			

			
			TargetScan target = new TargetScan();
			if(WebPage.isReachable(domain)){
			
			//System.out.println(new SqlInjection().exploit(target.mappingDomain(WebPage.getHost(domain))));
			
			}
			
			
			
			TargetImplementation targetImplementation = new TargetImplementation();
			
			Target tg = targetImplementation.get(WebPage.getHost(domain).getHost());
			
			if(tg == null){
				
				Map<String, String> whoisMap = null;
				
				try {
					whoisMap = new WhoisScan().get(WebPage.getHost(domain).getHost());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(whoisMap != null) {
					
					targetImplementation.persist(whoisMap);
				}
				
				System.out.println("OK");
			}
		}
		

		
		request.getRequestDispatcher("/").forward(request, response);
	}
}

