package com.BomB1La.AugSec;

import java.io.BufferedReader;
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
			// Communicate
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
