package br.com.ivy.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ivy.util.EntityManagerFactoryUtil;


public abstract class DAO<T> {
	
	public abstract Class<T> getSuperClass();
	
	private String getClassName() {
		return getSuperClass().getSimpleName().replace(getSuperClass().getPackage().getName() + ".", "");
	}
	
	public T get(Long id) {
		return EntityManagerFactoryUtil.getCurrentEntityManager().find(getSuperClass(), id);		
	}
	
	public void insert(T model) {
		EntityManagerFactoryUtil.getCurrentEntityManager().persist(model);
	}
	
	public void update(T model) {
		EntityManagerFactoryUtil.getCurrentEntityManager().merge(model);
	}
	
	public void delete(T model) {
		EntityManagerFactoryUtil.getCurrentEntityManager().remove(EntityManagerFactoryUtil.getCurrentEntityManager().merge(model));
	}
	
	public List<T> list() {
		return list(0, 0);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> list(int page, int pageSize) {
		EntityManager entityManager = EntityManagerFactoryUtil.getCurrentEntityManager();
		Query query = entityManager.createQuery("from " + getClassName());
        if (page > 0 && pageSize > 0) {
            query.setMaxResults(pageSize);
            query.setFirstResult((page - 1) * pageSize);			
		}
        List<T> result = query.getResultList();
        return result;
	}

}
