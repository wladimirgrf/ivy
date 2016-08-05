package br.com.ivy.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class DefaultAction extends HttpServlet{
	
	private static final long serialVersionUID = 7142098389153216637L;

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
		
		request.setAttribute("model", getModel());
		request.setAttribute("content", getJspPath());
		request.getRequestDispatcher(layoutPath).forward(request, response);
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
	

	
	protected String layoutPath = "/public/default/layout.jsp";
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	protected abstract String getModel();
	
	protected abstract String getJspPath();
}