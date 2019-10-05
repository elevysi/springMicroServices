package com.tutorial.basic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.basic.dao.AbstractDAO;


@Service
public abstract class AbstractServiceImplement<E, K> implements AbstractService<E, K>{
	
	@Autowired
	private AbstractDAO<E, K> abstractDAO;
	
	public E findByID(K key){
		return abstractDAO.findByID(key);
	}
	public E save(E entity){
		return abstractDAO.save(entity);
	}
	public E edit(E entity){
		return abstractDAO.edit(entity);
	}
	public void delete(E entity){
		abstractDAO.delete(entity);
	}
	
	public List<E> findAll(){
		return abstractDAO.findAll();
	}

}
