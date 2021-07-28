package com.asm_java6_pc00725.contraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.asm_java6_pc00725.validator.EmailUserValidator;
import com.asm_java6_pc00725.validator.PhoneUserValidator;

@Documented
@Constraint(validatedBy = PhoneUserValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistPhoneConstraint {
	String message() default "Số điện thoại đã tồn tại";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}