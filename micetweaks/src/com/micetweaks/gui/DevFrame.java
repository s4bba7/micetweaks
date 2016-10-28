package com.micetweaks.gui;

import com.micetweaks.resources.Assets;

import javax.swing.*;
import java.awt.*;

/**
 * Application main frame.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DevFrame extends JFrame {
	private JPanel devices = new JPanel();

	public DevFrame(String title) {
		super(title);
	}

	public void prepare() {
		setLayout(new BorderLayout());
		devices.setLayout(new GridLayout(0, 1));
	}

	/**
	 * Add devices to program's frame, repaint and resize the component.
	 */
	public void paint() {
		devices.removeAll();
		Assets.DEVICES_LIST.entrySet().stream().forEach(e -> {
			DevPanel p = new DevPanel(e.getKey(), e.getValue().getSpeed(), e.getValue().getDeceleration());
			p.prepare();
			devices.add(p);
		});
		add(devices, BorderLayout.CENTER);

		SwingUtilities.invokeLater(() -> {
			pack();
		});
	}

}
