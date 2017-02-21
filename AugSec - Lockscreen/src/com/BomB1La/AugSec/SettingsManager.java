package com.BomB1La.AugSec;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.NetworkInterface;
import java.util.Enumeration;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class SettingsManager {

	private static SettingsManager instance = new SettingsManager();

	public SettingsManager() {

	}

	public String getMacAddress() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface network = networkInterfaces.nextElement();
				byte[] mac = network.getHardwareAddress();
				if (mac != null) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < mac.length; i++) {
						sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
					}
					return sb.toString();
				}
			}
		} catch (Exception e) {

		}
		return null;
	}

	public String getOS() { // OS stands for Operating System
		return System.getProperty("os.name");
	}

	public Image generateQrCode(String content, int width, int height) {
		QRCodeWriter writer = new QRCodeWriter();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		try {
			BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					image.setRGB(i, j, bitMatrix.get(i, j) ? Color.black.getRGB() : Color.white.getRGB());
				}
			}

			return ((Image) image);
		} catch (Exception e) {

		}
		return ((Image) image);
	}

	public static SettingsManager getInstance() {
		return instance;
	}
}
