package com.micetweaks;

import com.micetweaks.gui.DevFrame;
import com.micetweaks.gui.DevPanel;
import com.micetweaks.gui.DevPanelModifier;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
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
	private DevFrame         frame;
	private VBox             mainPanel;
	private DevPanelModifier panelModifier;

	public Monitor(Stage frame) {
		this.frame = (DevFrame) frame;
		mainPanel = this.frame.getPanel();
		panelModifier = new DevPanelModifier(mainPanel);
	}

	@Override public void run() {
		Process p = null;
		try {
			// Register the process which will print out hotplugged devices.
			p = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "udevadm monitor --udev" });

			try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
				Platform.runLater(() -> updateDeviceList());
				String buff;
				while ((buff = in.readLine()) != null) {
					if (buff.contains("mouse")) {
						// Wait for system callback.
						Thread.sleep(1000);
						if (buff.contains("add")) HotPlug.detectUsbDevices(false);
						else HotPlug.detectUsbDevices(true);

						Platform.runLater(() -> updateDeviceList());
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Cannot start the application. See the log:\n" + e.getMessage());
			Log.write(e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Updates devices list of the programs frame.
	 */
	private void updateDeviceList() {
		Platform.runLater(() -> {
			panelModifier.clear();

			Assets.DEVICES_LIST.entrySet().forEach(e -> {
				System.out.println(e.getKey() + ":" + e.getValue().getSpeed());
				DevPanel p = new DevPanel(e.getKey(), e.getValue().getSpeed(), e.getValue().getDeceleration());
				p.setupComponents();
				p.setProps(e.getKey());
				panelModifier.add(p);
			});
			frame.sizeToScene();
		});
	}
}
