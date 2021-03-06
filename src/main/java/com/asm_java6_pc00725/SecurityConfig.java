package com.asm_java6_pc00725;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import com.asm_java6_pc00725.entity.Accounts;
import com.asm_java6_pc00725.service.AccountsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	AccountsService accountsService;
	@Autowired
	HttpServletRequest request;
	@Autowired
	BCryptPasswordEncoder pEncoder;

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Cung cấp nguồn dữ liệu đăng nhập
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				Accounts accounts = accountsService.findById(username);
				String password = accounts.getPassword(); // lấy ra password
				String[] roles = accounts.getAuthorities().stream().map(au -> au.getRole().getId())
						.collect(Collectors.toList()).toArray(new String[0]); // lấy ra các Id của role thu thập lại
																				// thành 1
				return User.withUsername(username).password(pEncoder.encode(password)).roles(roles).build();
			} catch (Exception e) {
				throw new UsernameNotFoundException(username + e);
			}
		});
	}

	// Phân quyền sử dụng
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable();
		http.authorizeRequests().antMatchers("/order/**", "/my-account/**").authenticated()
				.antMatchers("/assets/admin/**").hasAnyRole("STAF", "DIRE", "Ad").antMatchers("/rest/authorities")
				.hasAnyRole("DIRE", "Ad", "STAF").anyRequest().permitAll();

		http.formLogin().loginPage("/security/login/form") // form dang nhap
				.loginProcessingUrl("/security/login") // action
				.defaultSuccessUrl("/security/login/success", false).failureUrl("/security/login/error") // đăng nhập //
																											// sai hoặc
																											// // lỗi
				.usernameParameter("username").passwordParameter("password"); // default [username] [password]
		http.oauth2Login().loginPage("/security/login/form").defaultSuccessUrl("/oauth2/login/success", true)
				.failureUrl("/security/login/error").authorizationEndpoint().baseUri("/oauth2/authorization")
				.authorizationRequestRepository(getRepository()).and().tokenEndpoint()
				.accessTokenResponseClient(getToken());
		http.rememberMe().tokenValiditySeconds(86400); // default [remember-me]

		http.exceptionHandling().accessDeniedPage("/security/unauthoried"); // truy xuất không hợp lệ, k đúng quyền

		http.logout().logoutUrl("/security/logoff").logoutSuccessUrl("/security/logoff/success");
	}

	// Cho phép truy xuất REST API từ bên ngoài (domain khác)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> getRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getToken() {
		return new DefaultAuthorizationCodeTokenResponseClient();
	}
}
