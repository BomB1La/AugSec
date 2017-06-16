package com.BomB1La.AugSec;

public class Main {

	public static Handler handler = Handler.getInstance();
	public static SettingsManager settings = SettingsManager.getInstance();
	public static NetworkManager network = NetworkManager.getInstance();
	public static UserManager users = UserManager.getInstance();

	public static void main(String[] args) {
		users.load();
		network.setup();
		handler.setup(new Gui());
		
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				network.disconnect();
			}
		});
		
	}
}