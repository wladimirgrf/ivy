package br.com.ivy.restrict;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.ivy.entity.User;


public abstract class DefaultAction<E,T> extends HttpServlet{
	
	private static final long serialVersionUID = 4626463527834813889L;
	
	private User user;

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
		
		if (action != null && !action.isEmpty()) {
			request.setAttribute("action", action);
			if (action.equals("list")) {
				list();
			} else if (action.equals("edit")) {
				edit();
			} else if (action.equals("save")) {			
				save();
			} else if (action.equals("delete")) {			
				delete();
			} else if (action.equals("index")) {
				index();
			}
		}
		
		request.setAttribute("isAdmin", isAdmin());
		request.setAttribute("userId",  getUserId());
		
		request.getRequestDispatcher(layoutPath).forward(request, response);
	}
	
	private void clear() {
		this.request = null;
		this.response = null;
		this.id = idEmpty();
	}
	
	private void prepare(HttpServletRequest request, HttpServletResponse response) {
		clear();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) { }
		this.request  = request;
		this.response = response;
		action = request.getParameter("action");
		
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");

		if (request.getParameter("id") != null) {
			id = parse(request.getParameter("id"));
		}
	}
	
	protected void list() {
		request.setAttribute("list", getList());
		request.setAttribute("content", getListJspPath());
	}
	
	protected void edit() {
		if (!id.equals(0) && !id.equals("")) {
			request.setAttribute("object", getObject());					
		}
		request.setAttribute("content", getInputJspPath());
	}
	
	protected void save() {
		actionSave();
		list();
	}
	
	protected void delete() {
		actionDelete();
		list();	
	}
	
	protected void index() {
		indexAll();
		list();
	}
	
	protected long getUserId(){
		if (user != null){
			return user.getId();
		}
		return 0l;
	}
	
	protected boolean isAdmin() {
		if (user != null && user.getSecurityRule() != null && !user.getSecurityRule().isEmpty() && user.getSecurityRule().equalsIgnoreCase("admin")) {
			return true;
		}
		return false;
	}

	
	protected String layoutPath = "/restrict/default/layout.jsp";
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	protected T id;
	
	protected String action;
	
	protected abstract E getObject();
	
	protected abstract List<E> getList();
	
	protected abstract void actionSave();
	
	protected abstract void actionDelete();
	
	protected abstract String getListJspPath();
	
	protected abstract String getInputJspPath();
	
	protected abstract T parse(String id);
	
	protected abstract T idEmpty();
	
	protected abstract void indexAll();
}