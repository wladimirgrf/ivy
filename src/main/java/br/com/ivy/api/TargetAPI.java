package br.com.ivy.api;

import java.net.URL;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;

import br.com.ivy.dao.TargetDAO;
import br.com.ivy.entity.Target;

import br.com.ivy.util.WebPage;
import br.com.ivy.util.Whois;

@WebServlet("/api/target")
public class TargetAPI extends API {

	private static final long serialVersionUID = -2198141207687980772L;
	
	private TargetDAO dao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = TargetDAO.getInstance();
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
		page 	 = 1;
		pagesize = 5;
		
		gson = new Gson();
	}
	
	@Override
	protected void requestParameters(){
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
			
			reachable = WebPage.isReachable(host);
			
			if(!reachable.equals("true")) host = null;
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
		
		if(order   == "") order   = "desc";
		if(orderBy == "") orderBy = "lastScan";
	}
	
	@Override
	protected Object requestObject() {	
		Object result = null;
		
		if(action == null 
		|| action.isEmpty()){
			result = (id > 0 ? dao.get(id) : dao.list(page, pagesize, orderBy, order));
		}else{
			if (action.equals("search")) {	
				if(query != null 
				&& !query.isEmpty()){
					result = dao.search(query, page, pagesize).getResult();
				}
			}else if(host == null){
				if(!reachable.isEmpty()) result = reachable;
				
			}else if(action.equals("supported")) {
				result = (Whois.get(host.getHost()) != null ? true : false);
			
			}else if (action.equals("evaluate"))  { 
				target = dao.getByHost(this.host.getHost());
				
				if(target != null 
				&& (getCurrentDate() - target.getLastScan()) < week){
					result = target;
				}
			} else if (action.equals("save"))  { 
				result = save(); 
			}
		}
		return result;
	}
	
	private String save(){
		target = dao.getByHost(this.host.getHost());
		
		if(target == null){
			target = Whois.get(host.getHost());
			
			if(target == null) return "NOT-SUPORTED";
			
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
			
			dao.persist(target);
			
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
			
			dao.merge(target);
		}
		
		if(target != null) return "SUCCESS";
		
		return "ERROR";
	}
	
	private long getCurrentDate(){
		return Calendar.getInstance().getTimeInMillis();
	}
	
	
	//Action Properties

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
	
	private String reachable;
	
	private static final long week = 7 * 24 * 60 * 60 * 1000;
}
