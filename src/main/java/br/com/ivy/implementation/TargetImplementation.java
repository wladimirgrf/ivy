package br.com.ivy.implementation;

import java.util.Collection;

import br.com.ivy.dao.TargetDAO;
import br.com.ivy.entity.Target;


public class TargetImplementation {
	
	private TargetDAO dao;
	
	TargetImplementation(){ 
		dao = TargetDAO.getInstance(); 
	}
	
	public Target get(Long id) {
		return dao.get(id);
    }
	
	public Collection<Target> list() {
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
    
	public Target get(String domain) {
		return dao.find(String.format("from Target where domain=%s",domain)).get(0);
    }
}
