package com.micetweaks.gui.events;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

import java.text.DecimalFormat;

/**
 * Created on 09/11/16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DevPanelSliderEventHandler implements EventHandler<MouseEvent> {
	private DecimalFormat format = new DecimalFormat("#0.0");
	private Label       label;
	private double      value;
	private ProgressBar bar;

	public DevPanelSliderEventHandler(Label label, ProgressBar bar, double value) {
		this.label = label;
		this.bar = bar;
		this.value = value;
	}

	@Override public void handle(MouseEvent e) {
		Slider slider = (Slider) e.getSource();
		value = slider.getValue() / 10.0;
		label.setText("" + label.getUserData() + format.format(value));
		bar.setProgress(value / 10.0);
	}

	public double getValue() {
		return Math.round(value * 10.0) / 10.0;
	}
}
