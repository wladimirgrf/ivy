package br.com.ivy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter("/restrict/*")
public class SessionFilter implements Filter {

    public SessionFilter() { }

	public void destroy() { }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		if (session != null && session.getAttribute("user") != null) {
			chain.doFilter(request, response);	
		} else {
			req.getRequestDispatcher("/login.jsp").forward(request, response);	
		}
	}

	public void init(FilterConfig fConfig) throws ServletException { }
}