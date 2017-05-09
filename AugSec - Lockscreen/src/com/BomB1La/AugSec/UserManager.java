package com.BomB1La.AugSec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

	private static UserManager instance = new UserManager();
	private List<User> users = new ArrayList<User>();

	public void load() {
		// TODO:
		// Load all the files from the OS folder
		File f = new File(Main.settings.getUsersFolder());
		if (!f.exists()) {
			f.mkdir();
		}
		for (String str : f.list()) {
			File file = new File(Main.settings.getUsersFolder() + "\\" + str);
			if (!file.exists()) {
				continue;
			}
			if (file.isDirectory()) {
				continue;
			}
			load(file);
		}
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
		String path = Main.settings.getUsersFolder() + "\\" + (Main.settings.getOS().toLowerCase().contains("windows") ? "" : ".");
		File f = new File(path + user.getName());

		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (Main.settings.getOS().toLowerCase().contains("windows")) {
			Process p = null;
			try {
				p = Runtime.getRuntime().exec("attrib +h " + f.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (p != null) {
				try {
					p.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (!f.canWrite()) {
			return;
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(f));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bw == null) {
			return;
		}

		/**
		 * Lines: 1. Name 2. Key
		 */

		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load(File file) {
		// TODO:
		// Load user into list
		if (!file.exists()) {
			return;
		}
		if (!file.isHidden()) {
			return;
		}
		if (!file.canRead()) {
			return;
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (br == null) {
			return;
		}

		List<String> lines = new ArrayList<String>();
		String str = "";

		try {
			while ((str = br.readLine()) != null) {
				lines.add(str);
				// System.out.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (lines.size() == 0) {
			return;
		}
		/**
		 * Lines: 1. Name 2. Key
		 */
	}

	public static UserManager getInstance() {
		return instance;
	}
}
