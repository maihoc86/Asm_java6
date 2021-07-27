package com.asm_java6_pc00725.service;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import com.asm_java6_pc00725.entity.Accounts;

public interface AccountsService {
	Accounts findById(String id);

	List<Accounts> findAll();

	Accounts getByResetPasswordToken(String token);

	void updateResetPasswordToken(String token, String email);

	void updatePassword(Accounts accounts, String newPassword);
	Accounts findByEmail(String email);

	void loginFormOAuth2(OAuth2AuthenticationToken oAuth2AccessToken);

	Accounts update(Accounts account);

	Accounts save(Accounts account);

	void delete(String username);

	Accounts findEmailExist(String email, String username);

}
