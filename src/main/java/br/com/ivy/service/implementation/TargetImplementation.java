package br.com.ivy.service.implementation;

import java.util.Collection;

import br.com.ivy.dao.TargetDAO;
import br.com.ivy.entity.Target;


public class TargetImplementation {
	
	private TargetDAO dao;
	
	protected Target get(Long id) {
		return dao.get(id);
    }
	
	protected Collection<Target> list() {
		return dao.list();
    }

	protected void persist(Target t) {
		dao.insert(t);
    }

    protected void update(Target t) {
    	dao.update(t);
    }

    protected void remove(Target t) {
    	dao.delete(t);
    }


}
