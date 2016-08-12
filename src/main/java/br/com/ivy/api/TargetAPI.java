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
	
	@Override
	public void init() throws ServletException {
		super.init();
		implementation = new TargetImplementation();
	}
	
	@Override
	protected void clear(){
		host 	= null;
		safe 	= true;
		tags 	= "";
		url 	= "";
		action 	= "";
		query 	= "";
		order 	= "";
		orderBy = "";
		id 		 = 0l;
		page 	 = 0;
		pagesize = 0;
		
		gson = new Gson();
	}
	
	@Override
	protected void execute() {
		String object = null;

		setParameters();
		
		if(action != null){
			if (action.equals("evaluate") && host != null && WebPage.isReachable(host))  { 
				evaluate();
				
			}else if (action.equals("search") && query != null && !query.isEmpty()) {	
				object = gson.toJson(implementation.search(query, page, pagesize).getResult());
				
			} else if (action.equals("save") && host != null && WebPage.isReachable(host))  { 
				save(); 
			}
		}
		
		if(id == null){
			object = null;
		}else if(object == null){
			object = gson.toJson(( id > 0 ? implementation.get(id) : implementation.list(page, pagesize, orderBy, order)));
		}
		
		request.setAttribute("object", object);
	}
	
	private void save(){
		target = implementation.get(this.host.getHost());
		
		if(target == null){
			target = Whois.get(host.getHost());
			
			if(url != null && !url.isEmpty()){
				target.setUrl(url);
			}
			if(tags != null && !tags.isEmpty()){
				target.setTags(tags);
			}
			if(safe != null){
				target.setSafe(safe);
			}
			
			target.setLastScan(getCurrentDate());
			
			implementation.persist(target);
			
		}else if((getCurrentDate() - target.getLastScan()) >= week){
			if(url != null && !url.isEmpty()){
				target.setUrl(url);
			}
			if(tags != null && !tags.isEmpty()){
				target.setTags(tags);
			}
			if(safe != null){
				target.setSafe(safe);
			}
			
			target.setLastScan(getCurrentDate());
			
			implementation.update(target);
		}
		
		if(target != null) id = target.getId();
	}
	
	private long getCurrentDate(){
		return Calendar.getInstance().getTimeInMillis();
	}
	
	private void evaluate(){
		target = implementation.get(this.host.getHost());
		
		id = null;
		
		if(target != null && (getCurrentDate() - target.getLastScan()) < week){
			id = target.getId();
		}
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
				case "host": 	orderBy = "host"; 	 break;
				case "country": orderBy = "country"; break;
				case "owner": 	orderBy = "owner"; 	 break;
				case "email": 	orderBy = "email"; 	 break;
				case "changed": orderBy = "changed"; break;
				case "safe": 	orderBy = "safe"; 	 break;
			}
		}
		if (request.getParameter("order") != null) {
			if(request.getParameter("order").toLowerCase() == "asc") order = "asc";
		}
		
		page 	 = (page > 0 ? page : 1); 
		pagesize = (pagesize > 0 ? pagesize : 5);
		
		order 	= (order != "" ? order : "desc");
		orderBy = (orderBy != "" ? orderBy : "lastScan");
	}
	
	
	//Action Properties
	
	private Gson gson;

	private Long id;
	
	private int page;
	
	private int pagesize;
	
	private String url;
	
	private String tags;
	
	private String query;
	
	private String action;
	
	private URL host;
	
	private Boolean safe;
	
	private Target target;
	
	private String order;
	
	private String orderBy;
	
	private TargetImplementation implementation;
	
	private static final long week = 7 * 24 * 60 * 60 * 1000;
}
