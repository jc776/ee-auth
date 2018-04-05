package com.jc776.eeauth;

import javax.enterprise.inject.Specializes;

import org.picketlink.internal.DefaultIdentity;

@Specializes
public class CustomIdentity extends DefaultIdentity {
	private static final long serialVersionUID = 1L;

	private String msg;
	
	public void postLoginSetup() {
		this.msg = "JUST LOGGED IN";
	}
	
	public void setMsg(final String msg) {
		this.msg = msg;
	}
	
	public String getMsg(){
		return msg;
	}
}
