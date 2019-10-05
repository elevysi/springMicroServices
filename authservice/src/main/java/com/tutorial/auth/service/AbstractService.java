package com.tutorial.auth.service;

public interface AbstractService <E, K>{
	
	public E findByID(K key);
	public E save(E entity);
	public E edit(E entity);
	public void delete(E enity);
}
