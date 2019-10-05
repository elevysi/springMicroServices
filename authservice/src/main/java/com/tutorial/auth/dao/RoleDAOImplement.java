package com.tutorial.auth.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tutorial.auth.model.Role;

@Repository
@Transactional
public class RoleDAOImplement extends AbstractDAOImplement<Role, Long> implements RoleDAO{

}
