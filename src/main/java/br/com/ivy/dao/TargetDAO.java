package br.com.ivy.dao;

import java.util.List;

import br.com.ivy.entity.Target;

public class TargetDAO extends DAO<Target>{
	
	private TargetDAO() {
		super();
	}
	
	@Override
	public Class<Target> getSuperClass() {
		return Target.class;
	}
	
	@Override
	public String[] getSearchFields() {
		return new String[] {"host", "country", "tags"};
	}
	
	private static TargetDAO instance;
	
	public static TargetDAO getInstance() {
		if (instance == null) {
			synchronized (TargetDAO.class) {
				if (instance == null) {
					instance = new TargetDAO();	
				}
			}
		}
		return instance;
	}
	
	public Target getByHost(String host) {
		List<Target> targets = find(String.format("from Target where host='%s'",host));
		try{ return targets.get(0); } catch(Exception e){ return null; }
	}
}
