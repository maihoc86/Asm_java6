package com.asm_java6_pc00725.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asm_java6_pc00725.dao.AccountDAO;
import com.asm_java6_pc00725.dao.AuthoritiesDAO;
import com.asm_java6_pc00725.entity.Accounts;
import com.asm_java6_pc00725.entity.Authorities;
import com.asm_java6_pc00725.service.AuthoritesService;

@Service
public class AuthoritiesServiceImpl implements AuthoritesService {
	@Autowired
	AccountDAO dAccountDAO;
	@Autowired
	AuthoritiesDAO dao;
	@Autowired
	HttpServletRequest request;

	public List<Authorities> findAuthoritiesOfAdministrators() {
		List<Accounts> accounts = dao.findAdmin(request.getRemoteUser());
		System.out.println(dao.authoritiesOf(accounts));
		return dao.authoritiesOf(accounts);
	}

	@Override
	public List<Authorities> findAll() {
		return dao.findAll();
	}

	@Override
	public Authorities save(Authorities authorities) {
		return dao.save(authorities);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteById(id);

	}

	@Override
	public List<Accounts> getAdmin(String username) {
		// TODO Auto-generated method stub
		return dao.findAdmin(username);
	}

}
