package br.com.ivy.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;

import br.com.ivy.util.ManagerFactory;
import br.com.ivy.util.ResultHolder;


public abstract class DAO<T> {
	
    final List<String> stopWords = Arrays.asList(
			"a", "an", "and", "are", "as", "at", "be", "but", 
			"by", "for", "if", "in", "into", "is", "it", "no",
			"not", "of", "on", "or", "such", "that", "the", 
			"their", "then", "there", "these", "they", "this", 
			"to", "was", "will", "with"
    );
	
	public abstract Class<T> getSuperClass();
	
	public abstract String[] getSearchFields();
	
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
	
	
	@SuppressWarnings("unchecked")
	public ResultHolder<T> search(String query, int page, int pagesize) {
        long t = System.currentTimeMillis();
        
        EntityManager entityManager = ManagerFactory.getCurrentEntityManager();
    	FullTextEntityManager ft = Search.getFullTextEntityManager(entityManager);
		org.hibernate.search.query.dsl.QueryBuilder qb = ft.getSearchFactory().buildQueryBuilder().forEntity(getSuperClass()).get();		
		String[] matches = query != null ? query.split(" ") : new String[1];
		BooleanJunction<?> junction = qb.bool();
		
		for (String match : matches) {
			if (match.length() > 0 && !stopWords.contains(match.toLowerCase())) {
				junction.must(qb.keyword().onFields(getSearchFields()).matching(match).createQuery());
			}
		}
		org.apache.lucene.search.Query luceneQuery = junction.createQuery();
        FullTextQuery fullTextQuery = ft.createFullTextQuery(luceneQuery, getSuperClass());

        if (page > 0 && pagesize > 0) {
        	fullTextQuery.setMaxResults(pagesize);
        	fullTextQuery.setFirstResult((page - 1) * pagesize);
        }
        fullTextQuery.setHint("org.hibernate.cacheable", true);
        ResultHolder<T> result = new ResultHolder<T>();
        result.setPage(page);
        result.setPageSize(pagesize);
        result.setResult(fullTextQuery.getResultList());
        result.setResultSize(fullTextQuery.getResultSize());
        result.setTimeElapsed((int)(System.currentTimeMillis() - t));
       
        return result;
    }
}
