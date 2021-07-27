package com.asm_java6_pc00725.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asm_java6_pc00725.entity.Accounts;
import com.asm_java6_pc00725.entity.Authorities;


public interface AuthoritiesDAO extends JpaRepository<Authorities, Integer> {
	
	
	@Query("SELECT DISTINCT a FROM Authorities a WHERE a.account IN ?1")
	List<Authorities> authoritiesOf(List<Accounts> accounts);
	
	@Query("SELECT DISTINCT ar.account FROM Authorities ar WHERE ar.role.id IN ('DIRE', 'STAF') and ar.account.username != ?1"  )
	// lấy ra những account có vai trò DIRE và STAFF
	List<Accounts> findAdmin(String username);
}
