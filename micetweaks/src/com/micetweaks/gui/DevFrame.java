package com.micetweaks.gui;

import com.micetweaks.Assets;

import javax.swing.*;
import java.awt.*;

/**
 * Application's main frame.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DevFrame extends JFrame {
	private JPanel panel = new JPanel();

	public DevFrame(String title) {
		super(title);
	}

	public void prepare() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setIconImage(Assets.ICON);
		panel.setLayout(new GridLayout(0, 1));
	}

	/**
	 * Add devices to frame.
	 */
	public void paint() {
		panel.removeAll();

		Assets.DEVICES_LIST.entrySet().stream().forEach(e -> {
			DevPanel p = new DevPanel(e.getKey(), e.getValue().getSpeed(), e.getValue().getDeceleration());
			p.prepare();
			panel.add(p);
		});
		add(panel, BorderLayout.CENTER);
	}
}
