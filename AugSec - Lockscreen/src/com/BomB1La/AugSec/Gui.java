package com.BomB1La.AugSec;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Gui extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;

	public Gui() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int dWidth = gd.getDisplayMode().getWidth(), dHeight = gd.getDisplayMode().getHeight();

		JLabel qrcode = new JLabel(new ImageIcon(Main.settings.generateQrCode(Main.settings.getMacAddress(), 256, 256)));

		setTitle("AugSec 2017");
		setSize(dWidth, dHeight);

		qrcode.setBounds((dWidth / 2) - (289 / 2) + 5, (dHeight / 2) - (289 / 2) + 109, 289, 289);

		qrcode.addMouseListener(this);

		setContentPane(new JLabel(new ImageIcon(Main.class.getResource("/background.jpg"))));

		add(qrcode);

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
	public void mouseClicked(MouseEvent arg0) {
		Main.handler.setLock(!Main.handler.getLock());
		dispose();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
