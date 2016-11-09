package com.micetweaks;

import com.micetweaks.gui.DevFrame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Łukasz 's4bba7' Gąsiorowski.
 */
public class Main extends Application {
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

		// Load program configuration if file exist.
		if (Assets.getAppConf().exists()) {
			Properties prop = Config.loadAppConfig();
			isVisible = Boolean.parseBoolean(prop.getProperty("isVisible"));
		}

		// Prevent exiting JavaFX when last window is closed. Needed when hiding window from system tray.
		Platform.setImplicitExit(false);

		// Load devices configuration.
		Assets.DEVICES_LIST = Config.loadDeviceConfig();

		// Look for the already connected devices.
		HotPlug.detectUsbDevices(true);

		launch(args);
	}

	@Override public void start(Stage frame) throws Exception {
		// Init frame.
		frame = new DevFrame(isVisible);
		if (isVisible) {
			frame.setAlwaysOnTop(true);
			frame.sizeToScene();
			frame.show();
		}
		Stage finalFrame = frame;
		frame.setOnCloseRequest(e -> {
			// Exit program configuration when window is closed.
			System.exit(0);
		});

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			// Save program configuration at program exit.
			Config.saveAppConfig(finalFrame);
		}));

		// Start the usb hotplug monitor.
		new Thread(new Monitor(frame)).start();
	}

}
