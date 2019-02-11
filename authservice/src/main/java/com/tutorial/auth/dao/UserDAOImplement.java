package com.tutorial.auth.dao;


import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tutorial.auth.model.User;

@Repository
@Transactional
public class UserDAOImplement extends AbstractDAOImplement<User, Long> implements UserDAO{

}
