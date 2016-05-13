package br.com.ivy.restrict;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import br.com.ivy.entity.Target;
import br.com.ivy.implementation.TargetImplementation;

@WebServlet("/restrict/target")
public class TargetAction extends DefaultAction<Target, Long>{

	private static final long serialVersionUID = -6570296534962862169L;
	
	private TargetImplementation implementation;
	
	@Override
	public void init() throws ServletException {
		super.init();
		implementation = new TargetImplementation();
	}
	
	@Override
	protected Target getObject() {
		return implementation.get(id);
	}

	@Override
	protected List<Target> getList() {
		return implementation.list();
	}

	@Override
	protected void actionSave() {
		
		Target target = getObject() != null ? getObject() : new Target();
		
		if (request.getParameter("tags") != null){
			target.setTags(request.getParameter("tags"));
		}
		
		if (getObject() != null) {
			implementation.update(target);
		}
	}

	@Override
	protected void actionDelete() {
		if (!id.equals("")) implementation.remove(getObject());	
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
}
