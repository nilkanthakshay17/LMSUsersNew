package com.cognizant.app.lms.users.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.cognizant.app.lms.users.annotation.UserEmail;
import com.cognizant.app.lms.users.annotation.UserPassword;

@Component
public class UserEmailValidator implements ConstraintValidator<UserEmail, String> {

	@Override
	public boolean isValid(String emailValue, ConstraintValidatorContext context) {

		if (null == emailValue || "" == emailValue) 
			return false;
		else {
			Pattern emailPattern = Pattern.compile("([a-zA-Z0-9_\\-\\.]+)@([a-z\\.\\-]+)\\.([a-z]{2,8})([a-z]{2,8})?");

			Matcher emailMatcher = emailPattern.matcher(emailValue);

			return emailMatcher.matches();
		}

	}

}
