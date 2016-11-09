package com.micetweaks.gui;

import com.micetweaks.Config;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Created on 02/11/16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
class SaveButton extends Button implements EventHandler<Event> {
	private String title = "Save config";

	SaveButton() {
		setText(title);
		setFocusTraversable(false);
		setOnMouseClicked(this);
	}

	@Override public void handle(Event event) {
		Config.saveDeviceConfig();
	}
}
