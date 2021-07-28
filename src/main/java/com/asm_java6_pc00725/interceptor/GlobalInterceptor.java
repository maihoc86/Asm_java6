package com.asm_java6_pc00725.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.asm_java6_pc00725.service.CategoryService;

@Component
public class GlobalInterceptor implements HandlerInterceptor {
	@Autowired
	CategoryService service;

	@Autowired
	HttpServletRequest request;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		request.setAttribute("cates", service.findAll());
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getQueryString() != null) {
			String uri = request.getRequestURI() + "?" + request.getQueryString();
			System.out.print(uri);
			request.setAttribute("uri", uri);
		}
		return true;
	}

}
