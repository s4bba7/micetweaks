package com.micetweaks.gui.device.components;

import com.micetweaks.configs.DevicesConfig;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

/**
 * Created on 02/11/16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class SaveMenuItem extends MenuItem implements EventHandler<ActionEvent> {
	private String text = "Save config";

	public SaveMenuItem() {
		setText(text);
		setOnAction(this);
	}

	@Override public void handle(ActionEvent event) {
		DevicesConfig.INSTANCE.save();
	}
}
