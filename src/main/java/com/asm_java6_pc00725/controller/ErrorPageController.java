//
//package com.asm_java6_pc00725.controller;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@Controller
//public class ErrorPageController implements ErrorController {
//
//	@Autowired
//	HttpServletRequest request;
//
//	@Override
//
//	@RequestMapping(value = "/error", method = RequestMethod.GET)
//	public String getErrorPath() {
//		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//		Integer statusCode = Integer.valueOf(status.toString());
//		System.out.println(statusCode);
//		if (statusCode == HttpStatus.NOT_FOUND.value()) {
//			return "security/404";
//		} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//			return "security/500";
//		}
//		return "error";
//	}
//
//	@RequestMapping(value = "/error", method = RequestMethod.POST)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(BindException.class)
//	public String handleBindException(BindException e) {
//		// Trả về message của lỗi đầu tiên
//		System.out.println("Vào check API nè");
//		String errorMessage = "Request không hợp lệ";
//		if (e.getBindingResult().hasErrors())
//			e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
//		return errorMessage;
//	}
//}
