package com.BomB1La.AugSec;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Gui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnLock = new JButton(new ImageIcon(Main.class.getResource("/computer-secure.png")));
	private JLabel lblStatus = new JLabel("Status");

	public Gui() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int dWidth = gd.getDisplayMode().getWidth(), dHeight = gd.getDisplayMode().getHeight();

		ImageIcon qr_icon = new ImageIcon(Main.settings.generateQrCode(Main.settings.getMacAddress(), 256, 256));
		// ImageIcon lock_icon = new ImageIcon("res/computer-secure.png");

		setTitle("AugSec 2017");
		setSize(dWidth, dHeight);

		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				JLabel qrcode = new JLabel(qr_icon);
				qrcode.setBounds(x == 0 ? 0 : dWidth - 256, y == 0 ? 0 : dHeight - 256, 256, 256);
				add(qrcode);
			}
		}

		btnLock.setBounds((dWidth / 2) - (256 / 2), (dHeight / 2) - (256 / 2), 256, 256);
		lblStatus.setBounds(dWidth / 2, dHeight / 2, 220, 50);

		btnLock.addActionListener(this);

		add(btnLock);
		add(lblStatus);

		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
		setUndecorated(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(0);
		// pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Main.handler.setLock(!Main.handler.getLock());
		dispose();
	}

	public void setStatus(String status) {
		this.lblStatus.setText(status);
	}
}
