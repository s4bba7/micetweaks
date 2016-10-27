package com.micetweaks;

import com.micetweaks.gui.DevFrame;
import com.micetweaks.resources.Assets;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Łukasz 's4bba7' Gąsiorowski.
 */
public class Main {
	private static DevFrame frame;

	public static void main(String[] args) throws Exception {
		// Load the config.
		Config.load();

		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				// Init theme.
				try {
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
					BeautyEyeLNFHelper.launchBeautyEyeLNF();
				} catch (Exception e) {
					e.printStackTrace();
				}

				// Init frame.
				frame = new DevFrame(Assets.TITLE);
				frame.prepare();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setBounds(100, 100, 0, 0);
				frame.setResizable(true);
				frame.setVisible(true);
				frame.pack();
			}
		});

		HotPlug hotplug = new HotPlug();
		hotplug.detectUsbDevices(false);
		SwingUtilities.invokeAndWait(() -> frame.paintComponents());

		Process p = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "udevadm monitor --udev" });
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String s;
		while ((s = in.readLine()) != null) {
			if (s.contains("mouse")) {
				// Wait for system callback.
				Thread.sleep(1000);
				if (s.contains("add")) hotplug.detectUsbDevices(false);
				else hotplug.detectUsbDevices(true);

				SwingUtilities.invokeAndWait(() -> frame.paintComponents());
			}
		}
	}

}
