package br.com.ivy.api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;

import br.com.ivy.implementation.TargetImplementation;


@WebServlet("/target")
public class Target extends API {

	private static final long serialVersionUID = -2198141207687980772L;

	private long id;
	
	private TargetImplementation implementation;
	
	@Override
	public void init() throws ServletException {
		super.init();

		implementation = new TargetImplementation();
	}
	
	public void execute(){
		if (request.getParameter("id") != null) {
			try {
				id = Integer.parseInt(request.getParameter("id"));
			} catch (Exception e) { }
		}
		
		if(id > 0){
			request.setAttribute("object", new Gson().toJson(implementation.get(id)));
		}else{
			request.setAttribute("object", new Gson().toJson(implementation.list()));
		}
	}
}
