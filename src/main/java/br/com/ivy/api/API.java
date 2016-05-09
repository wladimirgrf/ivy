package br.com.ivy.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class API extends HttpServlet{
	
	private static final long serialVersionUID = -4761909367235273094L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		prepare(request, response);
		
		execute();

		request.getRequestDispatcher("/public/feed/page.jsp").forward(request, response);
	}
	
	private void clear() {
		this.request = null;
		this.response = null;
	}
	
	private void prepare(HttpServletRequest request, HttpServletResponse response) {
		clear();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) { }
		this.request  = request;
		this.response = response;
	}
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	protected abstract void execute();
}
