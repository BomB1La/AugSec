package com.BomB1La.AugSec;

public class User {

	private String name;
	private String key;
	private boolean admin;

	public User(String name) {
		this.name = name;
		this.key = null;
		this.admin = false;
	}

	public String getName() {
		return name;
	}

	public String getKey() {
		return key;
	}

	public boolean isAdmin() {
		return admin;
	}

	public boolean setKey(String key) {
		try {
			this.key = key;
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}