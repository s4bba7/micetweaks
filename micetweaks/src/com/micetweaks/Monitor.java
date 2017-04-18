package com.micetweaks;

import com.micetweaks.configs.DevicesConfig;
import com.micetweaks.devices.Device;
import com.micetweaks.devices.HotPlug;
import com.micetweaks.gui.DevFrame;
import com.micetweaks.gui.DevPanel;
import com.micetweaks.gui.DevPanelModifier;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created on 08/11/16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class Monitor implements Runnable {
	private DevFrame            frame;
	private VBox                mainPanel;
	private DevPanelModifier    panelModifier;
	private Map<String, Device> devicesConfigMap;

	public Monitor(Stage frame) {
		this.frame = (DevFrame) frame;
		mainPanel = this.frame.getPanel();
		panelModifier = new DevPanelModifier(mainPanel);
		devicesConfigMap = DevicesConfig.INSTANCE.load();
	}

	@Override public void run() {
		// Look for the already connected devices.
		HotPlug.detectUsbDevices(true);

		Process p = null;
		try {
			// Register the proccess which will print out hotplugging the com.micetweaks.devices.
			p = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "udevadm monitor --udev" });

			try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
				updateDeviceList();

				String s;
				while ((s = in.readLine()) != null) {
					if (s.contains("mouse")) {
						// Wait for system callback.
						Thread.sleep(1000);
						if (s.contains("add")) HotPlug.detectUsbDevices(false);
						else HotPlug.detectUsbDevices(true);

						updateDeviceList();
					}
				}
			}
		} catch (Exception e) {
			Log.write(e.getMessage());
			JOptionPane.showMessageDialog(null, "Cannot start the application. See the log:\n" + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Updates devices list of the programs frame.
	 */
	private void updateDeviceList() {
		Platform.runLater(() -> {
			panelModifier.clear();

			devicesConfigMap.entrySet().forEach(e -> {
				DevPanel p = new DevPanel(e.getKey(), e.getValue().getSpeed(), e.getValue().getDeceleration());
				p.setupComponents();
				p.setProps(e.getKey());
				panelModifier.add(p);
			});
			frame.sizeToScene();
		});
	}
}
