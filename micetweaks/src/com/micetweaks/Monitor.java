package com.micetweaks;

import com.micetweaks.devices.*;
import com.micetweaks.gui.DevFrame;
import com.micetweaks.gui.DevPanel;
import com.micetweaks.gui.DevPanelModifier;
import com.micetweaks.properties.DevicesProperties;
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
	private DeviceObserver deviceObserver = new ActiveDeviceObserver();

	public Monitor(Stage frame) {
		this.frame = (DevFrame) frame;
		mainPanel = this.frame.getPanel();
		panelModifier = new DevPanelModifier(mainPanel);
		devicesConfigMap = DevicesProperties.INSTANCE.load();
	}

	@Override public void run() {
		// Look for the already connected devices.
		HotPlug.detectUSBPointerDevices(deviceObserver);
		updateDeviceList();

		Process p = null;
		try {
			// Register the process which will print out hotplugged devices.
			p = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "udevadm monitor --udev" });

			try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
				String s;
				while ((s = in.readLine()) != null) {
					if (s.contains("mouse")) {
						deviceObserver.removeDevices();
						// Wait for system callback.
						Thread.sleep(1000);
						HotPlug.detectUSBPointerDevices(deviceObserver);
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
		deviceObserver.getActiveDevices().entrySet().stream().forEach(e -> {
			Device device = new DeviceProps(e.getKey());
			int deviceID = e.getValue();

			if (devicesConfigMap.get(e.getKey()) != null) {
				double speed = devicesConfigMap.get(e.getKey()).getSpeedValue();
				device.setSpeedValue(speed);
				boolean acceleration = devicesConfigMap.get(e.getKey()).isAccelerationActive();
				device.setAccelerationActive(acceleration);
			} else { DevicesProperties.INSTANCE.updateConfig(device); }

			final DevPanel devPanel = new DevPanel(device, deviceID);
			devPanel.setupComponents();
			devPanel.handle(null);

			Platform.runLater(() -> {
				panelModifier.clear();
				panelModifier.add(devPanel);
				frame.sizeToScene();
			});
		});
	}
}
