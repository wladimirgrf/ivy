package br.com.ivy.restrict;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import br.com.ivy.dao.TargetDAO;
import br.com.ivy.entity.Target;

@WebServlet("/restrict/target")
public class TargetAction extends DefaultAction<Target, Long>{

	private static final long serialVersionUID = -6570296534962862169L;
	
	private TargetDAO dao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = TargetDAO.getInstance();
	}
	
	@Override
	protected Target getObject() {
		return dao.get(id);
	}

	@Override
	protected List<Target> getList() {
		return dao.list();
	}

	@Override
	protected void actionSave() {
		
		Target target = (getObject() != null ? getObject() : new Target());
		
		if (request.getParameter("tags") != null){
			target.setTags(request.getParameter("tags"));
		}
		
		if (getObject() != null) {
			dao.merge(target);
		}
	}

	@Override
	protected void actionDelete() {
		if (id > 0) dao.remove(getObject());	
	}
	
	@Override
	protected String getListJspPath() {
		return "/restrict/target/list.jsp";
	}

	@Override
	protected String getInputJspPath() {
		return "/restrict/target/input.jsp";
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
