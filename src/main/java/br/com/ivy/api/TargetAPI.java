package br.com.ivy.api;

import java.net.URL;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;

import br.com.ivy.entity.Target;
import br.com.ivy.implementation.TargetImplementation;
import br.com.ivy.util.WebPage;
import br.com.ivy.util.Whois;


@WebServlet("/api/target")
public class TargetAPI extends API {

	private static final long serialVersionUID = -2198141207687980772L;
	
	private static final long week = 7 * 24 * 60 * 60 * 1000;

	private long id;
	
	private URL host;
	
	private Target target;
	
	private TargetImplementation implementation;
	
	@Override
	public void init() throws ServletException {
		super.init();
		implementation = new TargetImplementation();
	}
	
	@Override
	public void execute() {
		String object = "ERROR";
		
		setParameters();
		
		if(host != null && WebPage.isReachable(host)){
			target = implementation.get(this.host.getHost());
			sincronize(target);
			if(target != null) id = target.getId();
		}
	
		if (id > 0) {
			object = new Gson().toJson(implementation.get(id));
		}else{
			object = new Gson().toJson(implementation.list());
		}
		
		request.setAttribute("object", object);
	}
	
	private void sincronize(Target target){
		if(target == null){
			target = Whois.get(host.getHost());
			target.setLastScan(getCurrentDate());
			target.setSafe(true);
			
			implementation.persist(target);
			
		} else if (getCurrentDate() - target.getLastScan() >= week){
			target.setLastScan(getCurrentDate());
			target.setSafe(true);
			
			implementation.update(target);
		}
	}
	
	public long getCurrentDate(){
		return Calendar.getInstance().getTimeInMillis();
	}
	
	private void setParameters(){
		if (request.getParameter("id") != null) {
			try {
				id = Integer.parseInt(request.getParameter("id"));
			} catch (Exception e) { }
		}
		if(request.getParameter("host") != null){
			host = WebPage.getHost(request.getParameter("host"));
		}
	}
}
