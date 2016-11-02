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
	private final JPanel     panel      = new JPanel();
	private       SaveButton saveButton = new SaveButton();

	public DevFrame() {
		super(Assets.TITLE);
	}

	public void prepare() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//setResizable(false);
		setIconImage(Assets.ICON);
		panel.setLayout(new GridLayout(0, 1));
		add(panel, BorderLayout.CENTER);
		add(saveButton, BorderLayout.NORTH);
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
