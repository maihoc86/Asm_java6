package com.asm_java6_pc00725.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asm_java6_pc00725.service.AccountsService;

@Controller
public class SecurityController {
	@Autowired
	AccountsService service;

	@Autowired
	HttpServletRequest request;

	@RequestMapping("/security/login/*")
	public String get(Model model) {
		if (request.getRemoteUser() != null)
			return "redirect:/product/list";
		model.addAttribute("message", "Vui lòng đăng nhập");
		return "security/login";
	}

	@RequestMapping("/security/login/success")
	public String success(Model model) {
		model.addAttribute("message", "Đăng nhập thành công");
		return "redirect:/home/index";
	}

	@RequestMapping("/security/login/error")
	public String error(Model model) {
		model.addAttribute("message", "Sai thông tin đăng nhập");
		return "security/login";
	}

	@RequestMapping("/security/logoff/success")
	public String logoff(Model model) {
		if (request.getRemoteUser() != null)
			return "redirect:/product/list";
		model.addAttribute("message", "Đăng xuất thành công");
		return "security/login";
	}

	@RequestMapping("/security/unauthoried")
	public String denied(Model model) {
		return "security/403";
	}

	@RequestMapping("/oauth2/login/success")
	public String success(OAuth2AuthenticationToken oAuth2AccessToken) {
		service.loginFormOAuth2(oAuth2AccessToken);
		return "forward:/security/login/success";
	}

	@RequestMapping("/oauth2/login/error")
	public String errorOAuth2(Model model) {
		model.addAttribute("message", "Đăng nhập thất bại");
		return "forward:/security/login/form";
	}
}
