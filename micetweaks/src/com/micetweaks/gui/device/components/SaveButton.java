package com.micetweaks.gui.device.components;

import com.micetweaks.configs.DevicesConfig;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Created on 02/11/16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class SaveButton extends Button implements EventHandler<Event> {
	private String title = "Save config";

	public SaveButton() {
		setText(title);
		setFocusTraversable(false);
		setOnMouseClicked(this);
	}

	@Override public void handle(Event event) {
		DevicesConfig.INSTANCE.save();
	}
}
