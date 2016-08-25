package br.com.ivy.restrict;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import br.com.ivy.dao.UserDAO;
import br.com.ivy.entity.User;

@WebServlet("/restrict/user")
public class UserAction extends DefaultAction<User, Long>{

	private static final long serialVersionUID = -6746691638585518604L;
	
	private UserDAO dao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = UserDAO.getInstance();
	}
	
	@Override
	protected User getObject() {
		return dao.get(id);
	}

	@Override
	protected List<User> getList() {
		return dao.list();
	}

	@Override
	protected void actionSave() {
		
		User user = (getObject() != null ? getObject() : new User());
		
		if(isAdmin() || getUserId() == user.getId()){
			if (request.getParameter("name") != null){
				user.setName(request.getParameter("name"));
			}
			if (request.getParameter("email") != null){
				user.setEmail(request.getParameter("email"));
			}
			if (request.getParameter("password") != null){
				String hash = dao.hash(request.getParameter("password"));
				user.setHash(hash);
			}
			if(isAdmin()){
				if (request.getParameter("securityRule") != null){
					user.setSecurityRule(request.getParameter("securityRule"));
				}
				
				boolean active = false;
				if (request.getParameter("active") != null && request.getParameter("active").equals("on")) {			
					active = true;
				}
				
				user.setActive(active);
			}
			
			if (getObject() != null) {
				dao.merge(user);
				
			} else if(isAdmin()) {
				dao.persist(user);
			}
		}
	}

	@Override
	protected void actionDelete() {
		if (id > 0 && isAdmin()) dao.remove(getObject());	
	}
	
	@Override
	protected String getListJspPath() {
		return "/restrict/user/list.jsp";
	}

	@Override
	protected String getInputJspPath() {
		return "/restrict/user/input.jsp";
	}
	
	@Override
	protected Long parse(String id) {
		try {
			return Long.parseLong(id);
		} catch (Exception e) { 
			return 0l;
		}
	}

	@Override
	protected Long idEmpty() {
		return 0l;
	}
	
	@Override
	protected void indexAll() {
		dao.indexAll();
	}
}
