package com.jc776.eeauth;

public class SecurityUser {
	public SecurityUser(String name, String password, String data) {
		this.name = name;
		this.password = password;
		this.data = data;
	}
	
	public String name;
	public String password;
	public String data;
}
