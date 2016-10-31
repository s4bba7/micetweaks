package com.micetweaks;

import com.micetweaks.gui.DevFrame;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Łukasz 's4bba7' Gąsiorowski.
 */
public class Main {
	private static DevFrame frame;

	public static void main(String[] args) {
		// Save config at program shutdown.
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				Config.save();
			} catch (IOException e) {
				Log.write(e.getMessage());
				JOptionPane.showMessageDialog(null, "Cannot save the config file. See the log file.");
			}
		}));

		// Check host system for package dependency.
		try {
			Commands.checkSystemDependency();
		} catch (IOException | InterruptedException e) {
			Log.write(e.getMessage());
			JOptionPane.showMessageDialog(null,
					"Missing package dependencies. You need to install 'udevadm' and " + "'xinput' packages.");
			System.exit(1);
		}

		// Init theme.
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			Log.write(e.getMessage());
			e.printStackTrace();
		}

		// Load the config.
		Assets.DEVICES_LIST = Config.load();

		// Look for the already connected devices.
		HotPlug.detectUsbDevices(true);
		SwingUtilities.invokeLater(() -> {
			// Init frame.
			frame = new DevFrame(Assets.TITLE);
			frame.prepare();
			frame.paint();
			frame.pack();
			frame.setVisible(true);
		});

		// Start the usb hotplug monitor.
		try {
			Process p = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "udevadm monitor --udev" });
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s;
			while ((s = in.readLine()) != null) {
				if (s.contains("mouse")) {
					// Wait for system callback.
					Thread.sleep(1000);
					if (s.contains("add")) HotPlug.detectUsbDevices(true);
					else HotPlug.detectUsbDevices(true);

					SwingUtilities.invokeLater(() -> {
						frame.paint();
						frame.repaint();
						frame.pack();
					});
				}
			}
		} catch (Exception e) {
			Log.write(e.getMessage());
			JOptionPane.showMessageDialog(null, "Cannot start the application. See the log:\n" + e.getMessage());
			System.exit(1);
		}
	}
}
