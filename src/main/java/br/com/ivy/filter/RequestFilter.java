package br.com.ivy.filter;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import br.com.ivy.util.EntityManagerFactoryUtil;

@WebFilter("/*")
public class RequestFilter implements Filter {
	
	public void init(FilterConfig fConfig) throws ServletException { }
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		EntityManager entityManager = EntityManagerFactoryUtil.getCurrentEntityManager();
		try {
			entityManager.getTransaction().begin();
			chain.doFilter(request, response);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}			
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
	}

	public void destroy() { }
}