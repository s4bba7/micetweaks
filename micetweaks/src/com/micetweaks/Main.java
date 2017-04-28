package com.micetweaks;

import com.micetweaks.gui.DevFrame;
import com.micetweaks.gui.FirstRunDialog;
import com.micetweaks.properties.ProgramProperties;
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
	private static boolean firstRun = false; // Shows greetings window at first run.
	private static Properties programProperties;

	public static void main(String[] args) {
		// Check host system for package dependency.
		try {
			Commands.checkSystemDependency();
		} catch (IOException e) {
			Log.write(e.getMessage());
			JOptionPane.showMessageDialog(null, "Missing package dependencies. You need to install 'udevadm' and "
					+ "'xinput' packages.");
			System.exit(1);
		}

		programProperties = ProgramProperties.INSTANCE.load();
		if (programProperties != null)
			if (programProperties.getProperty("first run").equalsIgnoreCase("true")) firstRun = true;

		// Prevent exiting JavaFX when last window is closed. Needed when hiding window from system tray.
		Platform.setImplicitExit(false);

		launch(args);
	}

	@Override public void start(Stage frame) throws Exception {
		// Init frame.
		frame = new DevFrame(firstRun);
		frame.setAlwaysOnTop(true);
		frame.sizeToScene();
		if (firstRun) {
			programProperties.put("first run", "false");
			Stage firstRunDialog = new FirstRunDialog();
			firstRunDialog.showAndWait();
			frame.show();
		}

		frame.setOnCloseRequest(e -> {
			// Save program's configuration and exit when window is closed.
			ProgramProperties.INSTANCE.save();
			System.exit(0);
		});

		// Start the usb hotplug monitor.
		new Thread(new Monitor(frame)).start();
	}

}
