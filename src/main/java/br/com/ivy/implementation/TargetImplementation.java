package br.com.ivy.implementation;


import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.com.ivy.dao.TargetDAO;
import br.com.ivy.entity.Target;


public class TargetImplementation {
	
	private TargetDAO dao;
	
	public TargetImplementation(){ 
		dao = TargetDAO.getInstance();
	}
	
	public Target get(Long id) {
		return dao.get(id);
    }
	
	public Collection<Target> list() {
		return dao.list();
    }

	public void persist(Map<String,String> map) {
		Target target = new Target();
		
		target.setHost(map.get("host"));
		target.setOwner(map.get("owner"));
		target.setCountry(map.get("country"));
		target.setChanged(map.get("changed"));
		target.setPerson(map.get("person"));
		target.setEmail(map.get("email"));
		
		Calendar date =  Calendar.getInstance();
		target.setLastScan(date.getTimeInMillis());
		
		dao.insert(target);
    }

	public void update(Target target) {
    	dao.update(target);
    }

	public void remove(Target target) {
    	dao.delete(target);
    }
    
	public Target get(String host) {
		List<Target> targets = dao.find(String.format("from Target where host='%s'",host));
		try{  return targets.get(0);  }	catch(Exception e){  return null;  }
    }
}
