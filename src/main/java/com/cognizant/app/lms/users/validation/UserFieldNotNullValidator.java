package com.cognizant.app.lms.users.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;
import com.cognizant.app.lms.users.annotation.UserFieldNotNull;

@Component
public class UserFieldNotNullValidator implements ConstraintValidator<UserFieldNotNull	, String>{
	
	@Override
	public boolean isValid(String fieldValue, ConstraintValidatorContext context) {

		if(null == fieldValue || fieldValue == "")
			return false;
		return true;
	}
}
