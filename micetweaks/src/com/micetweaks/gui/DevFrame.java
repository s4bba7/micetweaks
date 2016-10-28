package com.micetweaks.gui;

import com.micetweaks.Config;
import com.micetweaks.HotPlug;
import com.micetweaks.resources.Assets;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by s4bba7 on 12.10.16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DevFrame extends JFrame {
	private JPanel  devices = new JPanel();

	public DevFrame(String title) {
		super(title);
	}

	public void prepare() {
		setLayout(new BorderLayout());
		devices.setLayout(new GridLayout(0, 1));
	}

	public void paint() {
		devices.removeAll();
		Assets.DEVICES_LIST.entrySet().stream().forEach(e -> {
			DevPanel p = new DevPanel(e.getKey(), e.getValue().getSpeed(), e.getValue().getDeceleration());
			p.prepare();
			devices.add(p);
		});
		add(devices, BorderLayout.CENTER);

		SwingUtilities.invokeLater(() -> {
			repaint();
			pack();
		});
	}

}
