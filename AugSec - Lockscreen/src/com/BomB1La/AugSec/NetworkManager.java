package com.BomB1La.AugSec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkManager implements Runnable {

	private static NetworkManager instance = new NetworkManager();

	private String HOST = "localhost";
	private int PORT = 8090;

	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private User selectUser;
	public NetworkManager() {
		// Read Settings from file (HOST, PORT NUMBER)
	}

	public void setup() {
		try {
			socket = new Socket(HOST, PORT);
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		new Thread(this).start();
	}

	@Override
	public void run() {
		String rec = null;
		while (isConnected()) {
			rec = recive();
			if (rec == null) {
				continue;
			}
			handle(rec);
		}
	}

	public void handle(String str) {
		System.out.println("Handling: " + str);
		if (str.startsWith("201")) {
			send("101"); // Lockscreen Connection
		} else if (str.startsWith("150")) { // Trying to connect
			if(Main.settings.getMacAddress().equals(str.substring(3, 20)) && 
				!(Main.users.getUserName(str.substring(20)).equals(null))){
				send("110");
			}else{
				send("ZZZ");
			}
			
		} else if (str.startsWith("401")) { // LOGIN KEY
			if(Main.users.getUser(selectUser.getName()).getKey().equals(str.substring(3))){
				send("101");
			}
			else if(Main.users.getUser(selectUser.getName()).getKey().equals(null)){
				send("YYY");
			}
			else {
				send("ZZZ");
			}
		} else if (str.startsWith("411")) { // CREATE LOGIN KEY
			if(!Main.users.getUser(selectUser.getName()).getKey().equals(null)){
				send("YYY");
			}
			else if(Main.users.getUser(selectUser.getName()).setKey(str.substring(3))){
				send("101");
			}
			else{
				send("ZZZ");
			}
		} else if (str.startsWith("970")) { // remove key from user
			if(Main.users.getUser(selectUser.getName()).setKey(null)){
				send("101");
			}
			else{
				send("ZZZ");
			}
		} else if (str.startsWith("980")) { // return back to locking the screen 
			send("101"); // TODO add the option to return back to the lockscreen after unlock 
		} else if (str.startsWith("990")) { // POWER OFF PC
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("shutdown -t 5");
				send("101");// Sending verification
			} catch (IOException e) {
				send("ZZZ");// Sending ERROR
				return;
			}
			System.exit(0);
		}
	}

	public void disconnect() {
		send("DISCONNECT");
	}

	public void send(String msg) {
		writer.println(msg);
		System.out.println("Sending: " + msg);
	}

	public String recive() {
		try {
			if (!reader.ready()) {
				return null;
			}
			String rec = reader.readLine();
			System.out.println("Recived: " + rec);
			return rec;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isConnected() {
		return (socket != null) && socket.isConnected();
	}

	public static NetworkManager getInstance() {
		return instance;
	}
}
