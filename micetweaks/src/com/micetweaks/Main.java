package com.micetweaks;

import com.micetweaks.gui.DevFrame;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Łukasz 's4bba7' Gąsiorowski.
 */
class Main {
	private static DevFrame frame;
	private static boolean isVisible = true; // Shows window at first run.

	public static void main(String[] args) {
		// Check host system for package dependency.
		try {
			Commands.checkSystemDependency();
		} catch (IOException e) {
			Log.write(e.getMessage());
			JOptionPane.showMessageDialog(null,
					"Missing package dependencies. You need to install 'udevadm' and " + "'xinput' packages.");
			System.exit(1);
		}

		// SHUTDOWN HOOK.
		// Save configuration at program exit.
		Runtime.getRuntime().addShutdownHook(new Thread(() -> Config.saveAppConfig(frame)));

		// Init theme.
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			Log.write(e.getMessage());
			e.printStackTrace();
		}

		// Load program configuration if file exist.
		if (Assets.getAppConf().exists()) {
			Properties prop = Config.loadAppConfig();
			isVisible = Boolean.parseBoolean(prop.getProperty("minimized"));
		}

		// Load devices configuration.
		Assets.DEVICES_LIST = Config.loadDeviceConfig();

		// Look for the already connected devices.
		HotPlug.detectUsbDevices(true);
		SwingUtilities.invokeLater(() -> {
			// Init frame.
			frame = new DevFrame();
			frame.setup(isVisible);
			frame.paint();
			frame.pack();
			frame.setVisible(isVisible);
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
					if (s.contains("add")) HotPlug.detectUsbDevices(false);
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
