package br.com.ivy.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.ivy.entity.Target;
import br.com.ivy.entity.Url;
import br.com.ivy.util.ManagerFactory;

public class UrlDAO extends DAO<Url>{
	
	private UrlDAO() {
		super();
	}
	
	@Override
	public Class<Url> getSuperClass() {
		return Url.class;
	}
	
	private static UrlDAO instance;
	
	public static UrlDAO getInstance() {
		if (instance == null) {
			synchronized (UrlDAO.class) {
				if (instance == null) {
					instance = new UrlDAO();	
				}
			}
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<Url> list(Target target) {
		if (target != null) {
			Query query = ManagerFactory.getCurrentEntityManager().createQuery("from Url u where u.target=:target");
			query.setParameter("target", target);			
			return query.getResultList();
		}
		return null;
	}

}
