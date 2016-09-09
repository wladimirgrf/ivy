package br.com.ivy.api;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import br.com.ivy.dao.WhoisScopeDAO;


@WebServlet("/api/scope")
public class WhoisScopeAPI extends API{
	
	private static final long serialVersionUID = 9838477621860306L;
	
	private WhoisScopeDAO dao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = WhoisScopeDAO.getInstance();
	}

	@Override
	protected void clear() {
		order 	 = "";
		orderBy  = "";
		id 		 = 0l;
		page 	 = 1;
		pagesize = 5;
	}
	
	@Override
	protected void requestParameters(){
		if (request.getParameter("id") != null) {
			try {
				id = Long.parseLong(request.getParameter("id"));
			} catch (Exception e) { }
		}
		if (request.getParameter("page") != null) {
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (Exception e) { }
		}
		if (request.getParameter("pagesize") != null) {
			try {
				pagesize = Integer.parseInt(request.getParameter("pagesize"));
			} catch (Exception e) { }
		}
		if (request.getParameter("orderBy") != null) {
			switch (request.getParameter("orderBy").toLowerCase()) {
				case "owner": 	orderBy = "owner"; 	 break;
				case "person":  orderBy = "person";  break;
				case "email": 	orderBy = "email"; 	 break;
				case "country": orderBy = "country"; break;
				case "changed": orderBy = "changed"; break;
			}
		}
		if (request.getParameter("order") != null) {
			if(request.getParameter("order").toLowerCase() == "desc") order = "desc";
		}
		
		if(order   == "") order   = "asc";
		if(orderBy == "") orderBy = "id";
	}
	
	@Override
	protected Object requestObject(){				
		Object result = null;

		result = (id > 0 ? dao.get(id) : dao.list(page, pagesize, orderBy, order));

		return result;
	}
	
	
	//Action Properties

	private Long id;
	
	private int page;
	
	private int pagesize;
	
	private String order;
	
	private String orderBy;
}
