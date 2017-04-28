package com.micetweaks.gui;

import com.micetweaks.Assets;
import com.micetweaks.Commands;
import com.micetweaks.Log;
import com.micetweaks.devices.Device;
import com.micetweaks.gui.events.AccelCheckBoxEventHandler;
import com.micetweaks.gui.events.DevPanelSliderEventHandler;
import com.micetweaks.properties.DevicesProperties;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.IOException;

/**
 * Stores device regulators.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DevPanel extends VBox implements EventHandler<Event> {
	private Slider      speedSlider = new Slider(0.01, 1.00, Assets.SPEED_DEFAULT);
	private Label       speedLabel  = new Label("" + Assets.SPEED_DEFAULT);
	private ProgressBar speedBar    = new ProgressBar(Assets.SPEED_DEFAULT);
	private StackPane   speedPane   = new StackPane();
	private Label                      deviceNameLabel;
	private int                        deviceID;
	private DevPanelSliderEventHandler speedSliderEventHandler;
	private CheckBox accelCheckBox = new CheckBox("Enable acceleration");
	private EventHandler<ActionEvent> accelCheckBoxEventHandler;
	private Device                    device;

	public DevPanel(Device device, int deviceID) {
		if (device.getSpeedValue() <= 0) device.setSpeedValue(Assets.SPEED_DEFAULT);
		this.device = device;
		this.deviceID = deviceID;
	}

	public void setupComponents() {
		setLayout();

		deviceNameLabel = new Label(device.getName());
		deviceNameLabel.setId("devName");
		speedSlider.setValue(device.getSpeedValue());
		speedLabel = new Label("" + device.getSpeedValue());
		speedBar = new ProgressBar(device.getSpeedValue());
		speedSliderEventHandler = new DevPanelSliderEventHandler(speedLabel, speedBar, device.getSpeedValue());
		accelCheckBoxEventHandler = new AccelCheckBoxEventHandler(device, deviceID);

		speedLabel.setId("values");
		speedLabel.setUserData("Speed: ");
		speedLabel.setText("" + speedLabel.getUserData() + device.getSpeedValue());
		speedLabel.setMouseTransparent(true);

		speedBar.setProgress(device.getSpeedValue());

		speedSlider.setValue(device.getSpeedValue());
		speedSlider.setOnMouseDragged(speedSliderEventHandler);
		speedSlider.setOnMousePressed(speedSliderEventHandler);
		speedSlider.setOnMouseReleased(this);

		speedPane.getChildren().addAll(speedBar, speedSlider);

		accelCheckBox.setSelected(device.isAccelerationActive());
		accelCheckBox.setOnAction(accelCheckBoxEventHandler);

		getChildren().addAll(deviceNameLabel, speedLabel, speedPane, accelCheckBox);
	}

	private void setLayout() {
		setPadding(new Insets(4, 0, 8, 0));
	}

	@Override public void handle(Event event) {
		try {
			double newSpeed = speedSliderEventHandler.getValue();
			Commands.setSpeedProperty(deviceID, newSpeed);
			Commands.setAccelerationState(deviceID, accelCheckBox.isSelected());
			device.setSpeedValue(newSpeed);
			device.setAccelerationActive(accelCheckBox.isSelected());
			DevicesProperties.INSTANCE.updateConfig(device);
		} catch (IOException e) {
			Log.write("ERROR, cannot use this setting: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error. Cannot use this setting: " + e.getMessage());
		}
	}
}
