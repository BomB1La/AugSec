package com.BomB1La.AugSec;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

	private static UserManager instance = new UserManager();
	private List<User> users = new ArrayList<User>();

	public void load() {
		// TODO:
		// Load all the files from the OS folder
	}

	public List<User> getUsers() {
		return users;
	}

	public User getUser(String name) {
		for (User u : users) {
			if (!u.getName().equalsIgnoreCase(name)) {
				continue;
			}
			return u;
		}
		return null;
	}

	public void save(User user) {
		// TODO:
		// Writing the user file and making it hidden
	}

	public static UserManager getInstance() {
		return instance;
	}
}
