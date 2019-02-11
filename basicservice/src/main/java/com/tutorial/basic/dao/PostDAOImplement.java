package com.tutorial.basic.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tutorial.basic.model.Post;

@Repository
@Transactional
public class PostDAOImplement implements PostDAO{
	
	@Autowired
	EntityManager em;
	
	public List<Post> getPosts(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Post> cq = cb.createQuery(Post.class);
		Root<Post> root = cq.from(Post.class);
		cq.select(root);
		TypedQuery<Post> query = em.createQuery(cq);
		return query.getResultList();
	}
	
	public Post getPost(Long id){
		return em.find(Post.class, id);
	}
	
	public Post savePost(Post post){
		em.persist(post);
		em.flush();
		return post;
	}
	
	public Post editPost(Post post){
		em.merge(post);
		em.flush();
		return post;
	}
	
	public void deletePost(Post post){
		if(post != null){
			em.merge(post);
			em.remove(post);
		}
		
	}

}
