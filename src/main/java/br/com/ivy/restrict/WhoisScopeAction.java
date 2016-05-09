package br.com.ivy.restrict;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import br.com.ivy.entity.WhoisScope;
import br.com.ivy.implementation.WhoisScopeImplementation;

@WebServlet("/restrict/scope")
public class WhoisScopeAction extends DefaultAction<WhoisScope, String>{

	private static final long serialVersionUID = 6584217161651670606L;
	
	private WhoisScopeImplementation implementation;
	
	@Override
	public void init() throws ServletException {
		super.init();
		implementation = new WhoisScopeImplementation();
	}
	
	@Override
	protected WhoisScope getObject() {
		return implementation.get(id);
	}

	@Override
	protected List<WhoisScope> getList() {
		return implementation.list();
	}

	@Override
	protected void actionSave() {
		
		WhoisScope scope = getObject() != null ? getObject() : new WhoisScope();
		
		if (request.getParameter("id") != null){
			scope.setId(request.getParameter("id"));
		}
		if (request.getParameter("owner") != null){
			scope.setOwner(request.getParameter("owner"));
		}
		if (request.getParameter("person") != null){
			scope.setPerson(request.getParameter("person"));
		}
		if (request.getParameter("email") != null){
			scope.setEmail(request.getParameter("email"));
		}
		if (request.getParameter("country") != null){
			scope.setCountry(request.getParameter("country"));
		}
		if (request.getParameter("changed") != null){
			scope.setChanged(request.getParameter("changed"));
		}
		
		if (getObject() != null) {
			implementation.update(scope);
		} else {
			implementation.persist(scope);
		}
	}

	@Override
	protected void actionDelete() {
		if (!id.equals("")) implementation.remove(getObject());	
	}
	
	@Override
	protected String getListJspPath() {
		return "/restrict/scope/list.jsp";
	}

	@Override
	protected String getInputJspPath() {
		return "/restrict/scope/input.jsp";
	}
	
	@Override
	protected String parse(String id) {
		return id;
	}

	@Override
	protected String idEmpty() {
		return "";
	}
}
