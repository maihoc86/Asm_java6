package com.asm_java6_pc00725.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.asm_java6_pc00725.contraint.ExistEmailConstraint;
import com.asm_java6_pc00725.contraint.ExistPhoneConstraint;
import com.asm_java6_pc00725.service.AccountsService;

public class PhoneUserValidator implements ConstraintValidator<ExistPhoneConstraint, String> {

	@Autowired
	AccountsService service;

	@Override
	public void initialize(ExistPhoneConstraint email) {
	}

	@Override
	public boolean isValid(String Field, ConstraintValidatorContext cxt) {
		if (service != null) {
			if (Field != null && !Field.isEmpty() && service.findByPhone(Field) != null) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

}