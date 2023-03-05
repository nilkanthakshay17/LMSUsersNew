package com.cognizant.app.lms.users.model;

import com.cognizant.app.lms.users.annotation.UserEmail;
import com.cognizant.app.lms.users.annotation.UserPassword;

public class UserUpdateRequestModel {

	private String userName;
	
	@UserEmail
	private String userEmail;
	
	@UserPassword
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
