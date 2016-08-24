package br.com.ivy.implementation;

import java.util.List;

import br.com.ivy.dao.WhoisScopeDAO;
import br.com.ivy.entity.WhoisScope;

public class WhoisScopeImplementation {
	
	private WhoisScopeDAO dao;
	
	public WhoisScopeImplementation(){ 
		dao = WhoisScopeDAO.getInstance(); 
	}
	
	public WhoisScope get(String id) {
		return dao.get(id);
    }
	
	public List<WhoisScope> list() {
		return dao.list();
    }

	public void persist(WhoisScope scope) {
		dao.insert(scope);
    }

	public void update(WhoisScope scope) {
    	dao.update(scope);
    }

	public void remove(WhoisScope scope) {
    	dao.delete(scope);
    }
	
	public void indexAll(){
		dao.indexAll();
	}
}
