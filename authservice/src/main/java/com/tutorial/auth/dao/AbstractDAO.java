package com.tutorial.auth.dao;

import java.util.List;

public interface AbstractDAO<E, K> {
	
	public E findByID(K key);
	public E save(E entity);
	public E edit(E entity);
	public void delete(E entity);
	public List<E> findAll();
}
