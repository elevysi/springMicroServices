package com.tutorial.basic.dao;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public abstract class AbstractDAOImplement<E, K> implements AbstractDAO<E, K>{
	
	@PersistenceContext
	private EntityManager em;
	private Class<E> type;
	
	
	public AbstractDAOImplement(){
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}
	
	public E findByID(K key){
		return em.find(type, key);
	}
	public E save(E entity){
		em.persist(entity);
		em.flush();
		return entity;
	}
	public E edit(E entity){
		em.merge(entity);
		em.flush();
		return entity;
	}
	public void delete(E entity){
		if(entity != null){
			em.merge(entity);
			em.remove(entity);
		}
	}
	
	public List<E> findAll(){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<E> criteria = builder.createQuery(type);
		Root<E> root = criteria.from(type);
		criteria.select(root);
		
		TypedQuery<E> query = em.createQuery(criteria);
		
		return query.getResultList();
	}

}
