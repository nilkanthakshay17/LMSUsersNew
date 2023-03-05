package com.cognizant.app.lms.users.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.core.annotation.Order;

import com.cognizant.app.lms.users.validation.UserEmailValidator;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER })
@Constraint(validatedBy = {UserEmailValidator.class})
@Order(2)
public @interface UserEmail {

	public String message() default "Email should be valid. (e.g. abc@gmail.com)";
	
	Class<?>[] groups() default{};

	Class<? extends Payload>[] payload() default {}; 
}
