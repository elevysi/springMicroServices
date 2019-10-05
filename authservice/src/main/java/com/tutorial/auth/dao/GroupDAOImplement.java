package com.tutorial.auth.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tutorial.auth.model.Group;

@Repository
@Transactional
public class GroupDAOImplement extends AbstractDAOImplement<Group, Long> implements GroupDAO{

}
