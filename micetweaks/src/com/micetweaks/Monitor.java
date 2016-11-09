package com.micetweaks;

import com.micetweaks.gui.DevFrame;
import javafx.application.Platform;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created on 08/11/16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class Monitor implements Runnable {
	private DevFrame frame;

	public Monitor(Stage frame) {
		this.frame = (DevFrame) frame;
	}

	@Override public void run() {
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

					Platform.runLater(() -> frame.paint());
				}
			}
			in.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot start the application. See the log:\n" + e.getMessage());
			Log.write(e.getMessage());
			System.exit(1);
		}
	}
}
