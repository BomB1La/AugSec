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
		while (isConnected()) {
			String rec = recive();
			if (rec == null) {
				continue;
			}
			handle(rec);
		}
	}

	public void handle(String str) {
		if (str.startsWith("401")) { // LOGIN KEY

		} else if (str.startsWith("411")) { // CREATE LOGIN KEY

		} else if (str.startsWith("130")) { // REQUEST TO START A STREAM (TeamViewer)

		} else if (str.startsWith("140")) { // REQUEST TO END A STREAM (TeamViewer)

		} else if (str.startsWith("150")) { // Trying to connect

		} else if (str.startsWith("990")) { // POWER OFF PC
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("shutdown -s -t 5");
				// Sending verfication
			} catch (IOException e) {
				// Sending ERROR
				return;
			}
			System.exit(0);
		}
	}

	public void send(String msg) {
		writer.println(msg);
	}

	public String recive() {
		try {
			if (!reader.ready()) {
				return null;
			}
			return reader.readLine();
		} catch (Exception e) {

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
