package com.BomB1La.AugSec;

public class Main {

	public static Handler handler = Handler.getInstance();
	public static SettingsManager settings = SettingsManager.getInstance();
	public static NetworkManager network = NetworkManager.getInstance();

	public static void main(String[] args) {
		handler.setup(new Gui());
	}
}
