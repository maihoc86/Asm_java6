package com.asm_java6_pc00725.entity;

import javax.servlet.http.HttpServletRequest;

public class Utility {
	public static String getSiteUrl(HttpServletRequest request) {
		String siteUrl = request.getRequestURL().toString();
		return siteUrl.replace(request.getServletPath(), "");
	}
}
