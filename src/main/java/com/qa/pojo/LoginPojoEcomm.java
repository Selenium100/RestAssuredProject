package com.qa.pojo;

import org.apache.juneau.annotation.Beanc;

public class LoginPojoEcomm {
	
	private String userEmail;
	private String userPassword;
	
	
	@Beanc(properties = "userEmail,userPassword")
	public LoginPojoEcomm(String userEmail, String userPassword) {
		this.userEmail=userEmail;
		this.userPassword = userPassword;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
	

}
