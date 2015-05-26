package br.com.ivy.view;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;

import br.com.ivy.entity.Target;
import br.com.ivy.implementation.TargetImplementation;
import br.com.ivy.implementation.UrlImplementation;

@WebServlet("/target")
public class TargetAction extends ViewAction<Target>{

	private static final long serialVersionUID = -5610094628063728512L;
	
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
	protected void subList(){
		Gson gson = new Gson();
		request.setAttribute("sublist", gson.toJson(new UrlImplementation().list(implementation.get(id))));
	}

}
