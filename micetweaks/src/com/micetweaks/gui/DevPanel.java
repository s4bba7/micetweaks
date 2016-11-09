package com.micetweaks.gui;

import com.micetweaks.Assets;
import com.micetweaks.Commands;
import com.micetweaks.DeviceProps;
import com.micetweaks.Log;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Stores device regulators.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
class DevPanel extends VBox implements EventHandler<Event> {
	private final Slider speedSlider = new Slider(1, 50, 11);
	private final Slider decelSlider = new Slider(1, 50, 20);
	private final Label  speedLabel  = new Label();
	private final Label  decelLabel  = new Label();
	private double speed;
	private double deceleration;
	private Label  name;
	private DecimalFormat format = new DecimalFormat("#0.0");

	public DevPanel(String name, double speed, double deceleration) {
		// Default values.
		if (speed < 0.1) this.speed = 1.1;
		else this.speed = speed;
		if (deceleration < 0.1) this.deceleration = 2.0;
		else this.deceleration = deceleration;

		this.name = new Label(name);
	}

	public void prepare() {
		setPadding(new Insets(4, 0, 8, 0));
		final ProgressBar speedBar = new ProgressBar(speed / 5.0);
		StackPane speedPane = new StackPane();

		speedSlider.setValue((int) (this.speed * 10));
		speedSlider.setOnMouseReleased(this);
		speedSlider.setOnMouseDragged(e -> {
			Slider slider = (Slider) e.getSource();
			speed = slider.getValue() / 10.0;
			speedLabel.setText(format.format(speed));
			speedBar.setProgress(speed / 5.0);
		});
		speedPane.getChildren().addAll(speedBar, speedSlider);

		final ProgressBar decelBar = new ProgressBar(deceleration / 5.0);
		StackPane decelPane = new StackPane();
		decelSlider.setValue((int) (this.deceleration * 10));
		decelSlider.setOnMouseReleased(this);
		decelSlider.setOnMouseDragged(e -> {
			Slider slider = (Slider) e.getSource();
			deceleration = slider.getValue() / 10.0;
			decelLabel.setText(format.format(deceleration));
			decelBar.setProgress(deceleration / 5.0);
		});
		decelPane.getChildren().addAll(decelBar, decelSlider);

		speedLabel.setText("" + speed);
		decelLabel.setText("" + deceleration);

		HBox speedBox = new HBox(speedPane, speedLabel);
		HBox decelBox = new HBox(decelPane, decelLabel);

		getChildren().addAll(name, speedBox, decelBox);

		// When the device is connected accept the config.
		setProps(name.getText());
	}

	/**
	 * Set speed and deceleration for the desired device.
	 *
	 * @param name device's name.
	 */
	private void setProps(String name) {
		DeviceProps props = Assets.DEVICES_LIST.get(name);
		try {
			Commands.setProp(props.getIds(), speed, deceleration);
			props.setSpeed(speed);
			props.setDeceleration(deceleration);
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
