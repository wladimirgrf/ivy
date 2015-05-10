package br.com.ivy.implementation;

import java.util.List;

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
	
	public List<Target> list() {
		return dao.list();
    }

	public void persist(Target target) {		
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
