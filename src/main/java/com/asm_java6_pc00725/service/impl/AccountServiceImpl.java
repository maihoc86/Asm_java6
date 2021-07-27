package com.asm_java6_pc00725.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.asm_java6_pc00725.dao.AccountDAO;
import com.asm_java6_pc00725.entity.Accounts;
import com.asm_java6_pc00725.service.AccountsService;

@Service
public class AccountServiceImpl implements AccountsService {
	@Autowired
	AccountDAO dao;

	@Override
	public Accounts findById(String id) {
		if (id != null) {
			Optional<Accounts> findAccounts = dao.findById(id);
			if (findAccounts.isEmpty()) {
				return null;
			} else {
				System.out.println(findAccounts);
				Accounts accounts = findAccounts.get();
				System.out.println(accounts);
				return accounts;
			}
		} else {
			return null;
		}

	}

	@Override
	public List<Accounts> findAll() {
		return dao.findAll();
	}

	@Autowired
	BCryptPasswordEncoder pEncoder;

	@Override
	public void loginFormOAuth2(OAuth2AuthenticationToken oAuth2AccessToken) {
		String email = oAuth2AccessToken.getPrincipal().getAttribute("email");
		String password = Long.toHexString(System.currentTimeMillis()); // mk ngẫu nhiên
		UserDetails user = User.withUsername(email).password(pEncoder.encode(password)).roles("GUEST").build();
		// lấy email làm username , lấy password random = password
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		// tạo đối tượng Authentication từ UserDetail

		SecurityContextHolder.getContext().setAuthentication(authentication); // thay đổi thông tin đăng nhập trên hệ
																				// thống
	}

	@Override
	public Accounts update(Accounts account) {
		return dao.saveAndFlush(account);
	}

	@Override
	public Accounts save(Accounts account) {
		return dao.saveAndFlush(account);
	}

	@Override
	public void delete(String username) {
		dao.updateStatus(username);
	}

	@Override
	public Accounts findEmailExist(String email, String username) {
		// TODO Auto-generated method stub
		return dao.findExistEmail(email, username);
	}

	@Override
	public Accounts findByEmail(String email) {
		// TODO Auto-generated method stub
		return dao.findByEmail(email);
	}

	@Override
	public Accounts getByResetPasswordToken(String token) {
		// TODO Auto-generated method stub
		return dao.findByReset_password_token(token);
	}

	@Override
	public void updateResetPasswordToken(String token, String email) {
		Accounts accounts = dao.findByEmail(email);
		if (accounts != null) {
			accounts.setReset_password_token(token);
			System.out.println(accounts.getPassword());
			dao.save(accounts);
		} else {
			throw new RuntimeException("Could not find any customer with the email " + email);
		}
	}

	@Override
	public void updatePassword(Accounts accounts, String newPassword) {
		accounts.setPassword(newPassword);
		accounts.setReset_password_token(null);
		dao.save(accounts);
	}

}
