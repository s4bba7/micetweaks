package com.micetweaks.gui.events;

import com.micetweaks.Commands;
import com.micetweaks.configs.DevicesConfig;
import com.micetweaks.devices.Device;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

import javax.swing.*;
import java.io.IOException;

/**
 * Created on 27/04/17.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class AccelCheckBoxEventHandler implements EventHandler<ActionEvent> {
	private int    deviceID;
	private Device device;

	public AccelCheckBoxEventHandler(Device device, int deviceID) {
		this.device = device;
		this.deviceID = deviceID;
	}

	@Override public void handle(ActionEvent event) {
		CheckBox checkBoxEvent = (CheckBox) event.getSource();
		try {
			Commands.setAccelerationState(deviceID, checkBoxEvent.isSelected());
			device.setAccelerationActive(checkBoxEvent.isSelected());
			DevicesConfig.INSTANCE.updateConfig(device);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Acceleration is not handled by this device.");
		}
	}
}
