package br.com.ivy.dao;

import br.com.ivy.entity.Url;

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

}
