package com.asm_java6_pc00725.exception;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Configuration
public class BadRequestRestAPI {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public String handleBindException(BindException e) {
		// Trả về message của lỗi đầu tiên
		String errorMessage = "Request không hợp lệ";
		if (e.getBindingResult().hasErrors())
			e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		return errorMessage;
	}
}