package com.asm_java6_pc00725.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.asm_java6_pc00725.contraint.ExistUsernameConstraint;
import com.asm_java6_pc00725.service.AccountsService;

public class UsernameUserValidator implements ConstraintValidator<ExistUsernameConstraint, String> {

	@Autowired
	AccountsService service;

	@Override
	public void initialize(ExistUsernameConstraint contactNumber) {
	}

	@Override
	public boolean isValid(String Field, ConstraintValidatorContext cxt) {
		if (service != null) {
			if (Field != null && !Field.isEmpty() && service.findById(Field) != null) {
				return false;
			} else {
				return true;
			}
		}else {
			return true;
		}
	}

}