package br.com.ivy.implementation;


import java.util.Collection;
import br.com.ivy.dao.UrlDAO;
import br.com.ivy.entity.Url;

public class UrlImplementation {
	
	private UrlDAO dao;
	
	public UrlImplementation(){ 
		dao = UrlDAO.getInstance();
	}
	
	public Url get(Long id) {
		return dao.get(id);
    }
	
	public Collection<Url> list() {
		return dao.list();
    }

	public void persist(Url url) {
		dao.insert(url);
    }

	public void update(Url url) {
    	dao.update(url);
    }

	public void remove(Url url) {
    	dao.delete(url);
    }

}
