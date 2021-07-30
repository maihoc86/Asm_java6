package com.asm_java6_pc00725.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asm_java6_pc00725.dao.RoleDAO;
import com.asm_java6_pc00725.entity.Roles;
import com.asm_java6_pc00725.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDAO dao;

	@Override
	public List<Roles> findAll() {
		return dao.findAll();
	}

	@Override
	public Roles findById(String role) {
		// TODO Auto-generated method stub
		return dao.findById(role).get();
	}

}
