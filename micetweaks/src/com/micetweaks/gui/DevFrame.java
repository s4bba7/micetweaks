package com.micetweaks.gui;

import com.micetweaks.Assets;
import com.micetweaks.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Application's main frame.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DevFrame extends JDialog {
	private final JPanel     panel      = new JPanel();
	private       SaveButton saveButton = new SaveButton();

	public DevFrame() {
		setTitle(Assets.TITLE);
	}

	/**
	 * @param isVisible needed for AWT hack - if frame is started in minimized mode it adds +2 to clickCounter.
	 */
	public void setup(boolean isVisible) {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setResizable(false);
		setIconImage(Assets.ICON);
		panel.setLayout(new GridLayout(0, 1));
		add(panel, BorderLayout.CENTER);
		add(saveButton, BorderLayout.NORTH);

		// Set system tray.
		try {
			new Tray(this).setup(isVisible);
		} catch (AWTException e) {
			Log.write(e.getMessage());
			e.printStackTrace();
		}

		// Exit app when close button is pressed on JDialog.
		addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		});
	}

	/**
	 * Add devices to frame.
	 */
	public void paint() {
		panel.removeAll();

		Assets.DEVICES_LIST.entrySet().forEach(e -> {
			DevPanel p = new DevPanel(e.getKey(), e.getValue().getSpeed(), e.getValue().getDeceleration());
			p.prepare();
			panel.add(p);
		});
		repaint();
	}
}
