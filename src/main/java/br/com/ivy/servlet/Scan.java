package br.com.ivy.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ivy.entity.Target;
import br.com.ivy.implementation.TargetImplementation;
import br.com.ivy.service.gathering.Icmp;
import br.com.ivy.service.gathering.Whois;
import br.com.ivy.service.scan.HtmlAnalyser;

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
			
			Icmp icmp = new Icmp();
			if(!icmp.ping(domain)) return;
			
			HtmlAnalyser analyser = new HtmlAnalyser();
			
			analyser.linkChecker(icmp.getHost());
			
			System.out.println(analyser.getSampling());
//			
//			TargetImplementation targetImplementation = new TargetImplementation();
//			
//			Target tg = targetImplementation.get(icmp.getHost());
//			
//			if(tg == null){
//				
//				Map<String, String> whoisMap = null;
//				
//				try {
//					whoisMap = new Whois().get(icmp.getHost());
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				
//				if(whoisMap != null) {
//					
//					targetImplementation.persist(whoisMap);
//				}
//				
//				System.out.println("OK");
//			}
		}
		

		
		request.getRequestDispatcher("/").forward(request, response);
	}
}

