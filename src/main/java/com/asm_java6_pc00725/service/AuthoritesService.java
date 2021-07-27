package com.asm_java6_pc00725.service;

import java.util.List;

import com.asm_java6_pc00725.entity.Accounts;
import com.asm_java6_pc00725.entity.Authorities;

public interface AuthoritesService {

	public List<Authorities> findAuthoritiesOfAdministrators();

	List<Authorities> findAll();

	Authorities save(Authorities authorities);

	void delete(Integer id);
	List<Accounts> getAdmin(String username);

}
