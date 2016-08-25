package br.com.ivy.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
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
	
	@Override
	public String[] getSearchFields() {
		return new String[] {"user"};
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
	
	public User get(String email, String hash){
		Query query = ManagerFactory.getCurrentEntityManager().createQuery("from User u where u.email=:email and u.hash=:hash");
		query.setParameter("email", email);
		query.setParameter("hash", hash);
		List<?> result = query.getResultList();
		User us = null;
		if (result != null && result.size() > 0 && result.get(0) instanceof User) {
			us = (User)result.get(0);
		}
		return us;
	}
	
	public String hash(String password) {
		String salt = password + "pentest@";
        String hash = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(salt.getBytes(), 0, salt.length());
            hash = new BigInteger(1, md5.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
	}
}