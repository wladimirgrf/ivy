package br.com.ivy.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.ivy.entity.User;
import br.com.ivy.util.ManagerFactory;


public class UserDAO extends DAO<User> {

	private UserDAO() {
		super();
	}
	
	@Override
	public Class<User> getSuperClass() {
		return User.class;
	}
	
	private static UserDAO instance;
	
	public static UserDAO getInstance() {
		if (instance == null) {
			synchronized (UserDAO.class) {
				if (instance == null) {
					UserDAO userDAO = new UserDAO();
					instance = userDAO;
				}
			}
		}
		return instance;
	}
	
	public User get(String user, String password){
		Query query = ManagerFactory.getCurrentEntityManager().createQuery("from User u where u.user=:user and u.password=:password");
		query.setParameter("user", user);
		query.setParameter("password", password);
		List<?> result = query.getResultList();
		User us = null;
		if (result != null && result.size() > 0 && result.get(0) instanceof User) {
			us = (User)result.get(0);
		}
		return us;
	}
}