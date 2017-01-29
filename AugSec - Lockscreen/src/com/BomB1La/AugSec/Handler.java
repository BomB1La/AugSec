package com.BomB1La.AugSec;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Handler implements Runnable {

	private static Handler instance = new Handler();

	private Gui gui = null;
	private boolean locked = true;
	private Robot robot;
	private Runtime rt;

	private Handler() {
		this.rt = Runtime.getRuntime();
	}

	public void setup(Gui gui) {
		this.gui = gui;
		try {
			this.robot = new Robot();
			if (!robot.isAutoWaitForIdle()) {
				robot.setAutoWaitForIdle(true);
			}
			robot.setAutoDelay(0);
		} catch (AWTException e) {
			e.printStackTrace();
			return;
		}
		new Thread(this).start();
	}

	public void run() {
		try {
			kill("explorer.exe");
			while (locked) {
				sleep(30L);
				focus();
				handleMouseAndKeyboard();
				kill("taskmgr.exe");
				focus();
				handleMouseAndKeyboard();
			}
			Runtime.getRuntime().exec("explorer.exe");
		} catch (Exception e) {

		}
	}

	private void handleMouseAndKeyboard() {
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		// robot.mouseMove(0, 0); // Re Enable this line only after the Network Manager will work
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {

		}
	}

	private void kill(String task) {
		try {
			if (Main.settings.getOS().toLowerCase().contains("windows")) {
				rt.exec("taskkill /F /IM " + task);
			} else {
				rt.exec("kill -9 " + task);
			}
		} catch (Exception e) {
			this.gui.setStatus(e.getMessage());
		}
	}

	private void focus() {
		this.gui.setFocusable(true);
		this.gui.requestFocus();
	}

	public void setLock(boolean lock) {
		this.locked = lock;
	}

	public boolean getLock() {
		return this.locked;
	}

	public static Handler getInstance() {
		return instance;
	}
}
