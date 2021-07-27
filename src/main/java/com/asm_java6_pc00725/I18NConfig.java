package com.asm_java6_pc00725;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class I18NConfig implements WebMvcConfigurer {

	@Bean("messageSource")
	public MessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
		ms.setDefaultEncoding("utf-8");
		ms.setBasenames("classpath:i18n/layout", "classpath:i18n/about", "classpath:i18n/shop",
				"classpath:i18n/contact", "classpath:i18n/changepass", "classpath:i18n/update_profile",
				"classpath:i18n/orderhistory", "classpath:i18n/login", "classpath:i18n/signup", "classpath:i18n/cart",
				"classpath:i18n/chitietdonhang", "classpath:i18n/chitietsanpham", "classpath:i18n/thanhtoan",
				"classpath:i18n/admintaikhoan");
		return ms;
	}

	@Bean("localeResolver")
	public LocaleResolver getLocaleResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(new Locale("vi"));
		cookieLocaleResolver.setCookiePath("/");
		cookieLocaleResolver.setCookieMaxAge(10 * 24 * 60 * 60);
		return cookieLocaleResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		registry.addInterceptor(lci).addPathPatterns("/**").excludePathPatterns("/images/**");
	}

}
