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
	
	private int page;
	
	private int pagesize;
	
	private String url;
	
	private String tags;
	
	private String query;
	
	private String action;
	
	private URL host;
	
	private Boolean safe;
	
	private Target target;
	
	private TargetImplementation implementation;
	
	@Override
	public void init() throws ServletException {
		super.init();
		implementation = new TargetImplementation();
	}
	
	@Override
	public void execute() {
		String object = null;
		
		setParameters();
		
		if(action != null){
			if (action.equals("search") && query != null && !query.isEmpty()) {			
				page 	 = (page > 0 ? page : 1); 
				pagesize = (pagesize > 0 ? pagesize : 5);
				
				object = new Gson().toJson(implementation.search(query, page, pagesize).getResult());
				
			} else if (action.equals("save") && host != null && WebPage.isReachable(host))  { 
				save(); 
			}
		}
		
		if(object == null){
			object = new Gson().toJson(( id > 0 ? implementation.get(id) : implementation.list()));
		}
		
		request.setAttribute("object", object);
	}
	
	private void save(){
		target = implementation.get(this.host.getHost());
		
		boolean isNew = false;
		
		if(target == null){
			isNew = true;
			target = Whois.get(host.getHost());
		}	
		
		if(url != null){
			target.setUrl(url);
		}
		if(tags != null){
			target.setTags(tags);
		}

		if(safe != null){
			target.setSafe(safe);
		}

		if(isNew){
			target.setLastScan(getCurrentDate());
			
			implementation.persist(target);
			
		} else if((getCurrentDate() - target.getLastScan()) >= week) {
			target.setLastScan(getCurrentDate());
			
			implementation.update(target);
		}
		
		if(target != null) id = target.getId();
	}
	
	private void setParameters(){
		if(request.getParameter("tags") != null){
			tags = request.getParameter("tags");
		}
		if(request.getParameter("url") != null){
			url = request.getParameter("url");
		}
		if(request.getParameter("action") != null){
			action = request.getParameter("action");
		}
		if(request.getParameter("query") != null){
			query = request.getParameter("query");
		}
		if(request.getParameter("host") != null){
			host = WebPage.getHost(request.getParameter("host"));
		}
		if(request.getParameter("safe") != null){
			safe = Boolean.parseBoolean(request.getParameter("safe"));
		}
		if (request.getParameter("id") != null) {
			try {
				id = Integer.parseInt(request.getParameter("id"));
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
	}
	
	public long getCurrentDate(){
		return Calendar.getInstance().getTimeInMillis();
	}
}
