package com.micetweaks.gui;

import com.micetweaks.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created on 07/11/16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
class Tray {
	private JDialog frame;
	private int     clickCounter;

	Tray(JDialog frame) {
		this.frame = frame;
	}

	/**
	 * @param isVisible needed for AWT hack - if frame is started in minimized mode it adds +2 to clickCounter.
	 * @throws AWTException
	 */
	void setup(boolean isVisible) throws AWTException {
		if (SystemTray.isSupported()) {
			if (!isVisible) clickCounter += 2;
			SystemTray tray = SystemTray.getSystemTray();
			TrayIcon icon = new TrayIcon(Assets.ICON);
			icon.setImageAutoSize(true);
			icon.addMouseListener(new MouseAdapter() {
				@Override public void mousePressed(MouseEvent e) {
					System.out.println("A");
					super.mouseClicked(e);
					if (frame.isShowing()) frame.setVisible(false);
					else frame.setVisible(true);
				}
			});
			icon.setToolTip(Assets.TITLE);
			tray.add(icon);
			trayhack();
		}
	}

	private void trayhack() {
		// Hack to snoop on global mouse events:
		Toolkit.getDefaultToolkit().addAWTEventListener(e -> {
			if (e.getSource().toString().contains("TrayIcon@")) clickCounter++;
			if (clickCounter % 4 == 0) frame.setVisible(true);
			else if ((clickCounter + 2) % 4 == 0) frame.setVisible(false);
		}, MouseEvent.MOUSE_EVENT_MASK);
	}
}
