package br.com.ivy.view;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class ViewAction<E> extends HttpServlet{
	
	private static final long serialVersionUID = 3068351859046703651L;
	
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
		
		if(id > 0){
			get();
		}else{
			list();
		}
		
		request.setAttribute("content", getContentPath());
		request.getRequestDispatcher(getLayoutPath()).forward(request, response);
	}
	
	private void clear() {
		this.request = null;
		this.response = null;
		this.id = 0;
	}
	
	private void prepare(HttpServletRequest request, HttpServletResponse response) {
		clear();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) { }
		this.request  = request;
		this.response = response;

		if (request.getParameter("id") != null) {
			try {
				id = Integer.parseInt(request.getParameter("id"));
			} catch (Exception e) { }
		}
	}
	
	protected void list() {
		request.setAttribute("list", getList());
	}
	
	protected void get() {
		subList();
		request.setAttribute("object", getObject());
	}
	
	protected long id;
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	protected abstract E getObject();
	
	protected abstract List<E> getList();
	
	protected abstract void subList();
	
	protected abstract String getLayoutPath();
	    
	protected abstract String getContentPath();
}
