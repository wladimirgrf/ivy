package br.com.ivy.dao;

import br.com.ivy.entity.Target;

public class TargetDAO extends DAO<Target>{
	
	private TargetDAO() {
		super();
	}
	
	@Override
	public Class<Target> getSuperClass() {
		return Target.class;
	}
	
	private static TargetDAO instance;
	
	public static TargetDAO getInstance() {
		if (instance == null) {
			synchronized (TargetDAO.class) {
				if (instance == null) {
					TargetDAO advertiserDAO = new TargetDAO();
					instance = advertiserDAO;	
				}
			}
		}
		return instance;
	}

}
