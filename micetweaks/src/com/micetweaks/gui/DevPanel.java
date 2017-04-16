package com.micetweaks.gui;

import com.micetweaks.Assets;
import com.micetweaks.Commands;
import com.micetweaks.DeviceProps;
import com.micetweaks.Log;
import com.micetweaks.gui.events.MouseAction;
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
 * Stores device regulators.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DevPanel extends VBox implements EventHandler<Event> {
	private final Slider      speedSlider = new Slider(1, 100, 26);
	private final Slider      decelSlider = new Slider(1, 100, 52);
	private final Label       speedLabel  = new Label();
	private final Label       decelLabel  = new Label();
	private final ProgressBar speedBar    = new ProgressBar();
	private final ProgressBar decelBar    = new ProgressBar();
	private       StackPane   speedPane   = new StackPane();
	private       StackPane   decelPane   = new StackPane();
	private double      speed;
	private double      deceleration;
	private Label       name;
	private MouseAction speedSliderAction;
	private MouseAction decelSliderAction;

	public DevPanel(String name, double speed, double deceleration) {
		setSlidersDefaultValues(speed, deceleration);
		speedSliderAction = new MouseAction(speedLabel, speedBar, this.speed);
		decelSliderAction = new MouseAction(decelLabel, decelBar, this.deceleration);
		this.name = new Label(name);
		this.name.setId("devName");
	}

	private void setSlidersDefaultValues(double speed, double deceleration) {
		if (speed < 0.1) this.speed = 2.6;
		else this.speed = speed;
		if (deceleration < 0.1) this.deceleration = 5.2;
		else this.deceleration = deceleration;
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

	/**
	 * Set speed and deceleration for the desired device.
	 *
	 * @param name device's name.
	 */
	public void setProps(String name) {
		DeviceProps props = Assets.DEVICES_LIST.get(name);
		try {
			Commands.setProp(props.getIds(), speedSliderAction.getValue(), decelSliderAction.getValue());
			props.setSpeed(speedSliderAction.getValue());
			props.setDeceleration(decelSliderAction.getValue());
		} catch (IOException e) {
			Log.write(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error. Cannot use this setting:\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override public void handle(Event event) {
		setProps(name.getText());
	}
}
