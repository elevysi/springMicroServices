package com.tutorial.basic.dao;


import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tutorial.basic.model.Post;

@Repository
@Transactional
public class PostDAOImplement extends AbstractDAOImplement<Post, Long> implements PostDAO{

}
