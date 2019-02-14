package com.tutorial.auth.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.tutorial.auth.model.Group;
import com.tutorial.auth.model.User;

@Repository
@Transactional
public class UserDAOImplement extends AbstractDAOImplement<User, Long> implements UserDAO{

	@PersistenceContext
	private EntityManager em;
	
	public User loadByUsername(String username){
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		
		Predicate predicate = builder.equal(root.get("username"), username);
		criteria.where(predicate);
		TypedQuery<User> query = em.createQuery(criteria);
		List<User> results = query.getResultList();
		if(! results.isEmpty()){
			User user = results.get(0);
			Hibernate.initialize(user.getGroups());
			for (Group group : user.getGroups()){
				Hibernate.initialize(group.getRoles());
			}
			return user;
		}
		
		return null;
	}
}
