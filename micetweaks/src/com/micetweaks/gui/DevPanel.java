package com.micetweaks.gui;

import com.micetweaks.Assets;
import com.micetweaks.Commands;
import com.micetweaks.Log;
import com.micetweaks.configs.DevicesConfig;
import com.micetweaks.devices.Device;
import com.micetweaks.gui.events.DevPanelSliderEventHandler;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.IOException;

/**
 * Stores com.micetweaks.devices regulators.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DevPanel extends VBox implements EventHandler<Event> {
	private Slider      speedSlider  = new Slider(1, 100, Assets.SPEED_DEFAULT * 10);
	private Slider      decelSlider  = new Slider(1, 100, Assets.DECELERATION_DEFAULT * 10);
	private Label       speedLabel   = new Label("" + Assets.SPEED_DEFAULT);
	private Label       decelLabel   = new Label("" + Assets.DECELERATION_DEFAULT);
	private ProgressBar speedBar     = new ProgressBar(Assets.SPEED_DEFAULT * 10);
	private ProgressBar decelBar     = new ProgressBar(Assets.DECELERATION_DEFAULT * 10);
	private StackPane   speedPane    = new StackPane();
	private StackPane   decelPane    = new StackPane();
	private double      speed        = Assets.SPEED_DEFAULT;
	private double      deceleration = Assets.DECELERATION_DEFAULT;
	private Label                      name;
	private int                        devID;
	private DevPanelSliderEventHandler speedSliderAction;
	private DevPanelSliderEventHandler decelSliderAction;

	public DevPanel(String name, double speed, double deceleration, int id) {
		this.speed = speed;
		this.deceleration = deceleration;
		this.name = new Label(name);
		this.name.setId("devName");
		devID = id;

		speedSlider = new Slider(1, 100, speed * 10);
		decelSlider = new Slider(1, 100, deceleration * 10);
		speedLabel = new Label("" + speed);
		decelLabel = new Label("" + deceleration);
		speedBar = new ProgressBar(speed * 10);
		decelBar = new ProgressBar(deceleration * 10);
		speedSliderAction = new DevPanelSliderEventHandler(speedLabel, speedBar, speed);
		decelSliderAction = new DevPanelSliderEventHandler(decelLabel, decelBar, deceleration);
	}

	public DevPanel(String name, int id) {
		speedSliderAction = new DevPanelSliderEventHandler(speedLabel, speedBar, this.speed);
		decelSliderAction = new DevPanelSliderEventHandler(decelLabel, decelBar, this.deceleration);
		this.name = new Label(name);
		this.name.setId("devName");
		devID = id;
	}

	public void setupComponents() {
		setLayout();
		speedLabel.setId("values");
		speedLabel.setUserData("Speed: ");
		speedLabel.setText("" + speedLabel.getUserData() + speed);
		speedLabel.setMouseTransparent(true);

		decelLabel.setId("values");
		decelLabel.setUserData("Deceleration: ");
		decelLabel.setText("" + decelLabel.getUserData() + deceleration);
		decelLabel.setMouseTransparent(true);

		speedBar.setProgress(speed / 10.0);

		decelBar.setProgress(deceleration / 10.0);

		speedSlider.setValue((int) (this.speed * 10));
		speedSlider.setOnMouseDragged(speedSliderAction);
		speedSlider.setOnMousePressed(speedSliderAction);
		speedSlider.setOnMouseReleased(this);

		speedPane.getChildren().addAll(speedBar, speedSlider);

		decelSlider.setValue((int) (this.deceleration * 10));
		decelSlider.setOnMouseDragged(decelSliderAction);
		decelSlider.setOnMousePressed(decelSliderAction);
		decelSlider.setOnMouseReleased(this);

		decelPane.getChildren().addAll(decelBar, decelSlider);

		getChildren().addAll(name, speedLabel, speedPane, decelLabel, decelPane);
	}

	private void setLayout() {
		setPadding(new Insets(4, 0, 8, 0));
	}

	@Override public void handle(Event event) {
		Device props = DevicesConfig.INSTANCE.getConfig().get(name.getText());
		try {
			Commands.setProp(devID, speedSliderAction.getValue(), decelSliderAction.getValue());
			props.setSpeed(speedSliderAction.getValue());
			props.setDeceleration(decelSliderAction.getValue());
		} catch (IOException e) {
			Log.write("ERROR, cannot use this setting: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error. Cannot use this setting: " + e.getMessage());
		}
	}
}
