package br.com.ivy.dao;

import br.com.ivy.entity.WhoisScope;

public class WhoisScopeDAO extends DAO<WhoisScope>{
	
	private WhoisScopeDAO() {
		super();
	}
	
	@Override
	public Class<WhoisScope> getSuperClass() {
		return WhoisScope.class;
	}
	
	@Override
	public String[] getSearchFields() {
		return new String[] {"id", "owner", "person", "email", "country"};
	}
	
	private static WhoisScopeDAO instance;
	
	public static WhoisScopeDAO getInstance() {
		if (instance == null) {
			synchronized (WhoisScopeDAO.class) {
				if (instance == null) {
					instance = new WhoisScopeDAO();	
				}
			}
		}
		return instance;
	}
}
