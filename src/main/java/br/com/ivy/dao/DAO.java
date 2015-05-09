package br.com.ivy.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ivy.util.ManagerFactory;


public abstract class DAO<T> {
	
	public abstract Class<T> getSuperClass();
	
	private String getClassName() {
		return getSuperClass().getSimpleName().replace(getSuperClass().getPackage().getName() + ".", "");
	}
	
	public T get(Object id) {
		return ManagerFactory.getCurrentEntityManager().find(getSuperClass(), id);		
	}
	
	public void insert(T model) {
		ManagerFactory.getCurrentEntityManager().persist(model);
	}
	
	public void update(T model) {
		ManagerFactory.getCurrentEntityManager().merge(model);
	}
	
	public void delete(T model) {
		ManagerFactory.getCurrentEntityManager().remove(ManagerFactory.getCurrentEntityManager().merge(model));
	}
	
	public List<T> list() {
		return list(0, 0);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> find(String select) {
		EntityManager entityManager = ManagerFactory.getCurrentEntityManager();
		Query query = entityManager.createQuery(select);
        List<T> result = query.getResultList();
        return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> list(int page, int pageSize) {
		EntityManager entityManager = ManagerFactory.getCurrentEntityManager();
		Query query = entityManager.createQuery(String.format("from %s", getClassName()));
        if (page > 0 && pageSize > 0) {
            query.setMaxResults(pageSize);
            query.setFirstResult((page - 1) * pageSize);			
		}
        List<T> result = query.getResultList();
        return result;
	}
}
