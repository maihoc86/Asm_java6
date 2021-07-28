package com.asm_java6_pc00725.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.asm_java6_pc00725.entity.Accounts;

public interface AccountDAO extends JpaRepository<Accounts, String> {

	@Query(value = "SELECT * FROM Accounts  WHERE Email = :email and Username not like :username", nativeQuery = true)
	Accounts findExistEmail(String email, String username);

	@Transactional
	@Modifying
	@Query("Update Accounts c set c.status = false where c.username = ?1")
	void updateStatus(String username);

	@Query("SELECT o FROM Accounts o where o.email = ?1")
	Accounts findByEmail(String email);

	@Query("SELECT o  FROM Accounts  o WHERE o.reset_password_token = ?1")
	Accounts findByReset_password_token(String token);

	Accounts findByPhone(String field);

}
