package com.asm_java6_pc00725.interceptor;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConfigErrorURIController implements ErrorController {

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @RequestMapping("/error")
	 * 
	 * @Override public String getErrorPath() { // TODO Auto-generated method stub
	 * return "redirect:/product/list"; }
	 */
}
