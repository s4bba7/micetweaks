package com.micetweaks.gui.device.components;

import com.micetweaks.gui.Theme;
import com.micetweaks.properties.ProgramProperties;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created on 28/04/17.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class IconsThemeItem extends MenuItem implements EventHandler<ActionEvent> {
	private String   option;
	private TrayIcon trayIcon;
	private Stage    stage;

	public IconsThemeItem(String text, TrayIcon trayIcon, Stage stage) {
		option = text;
		setText("Theme: " + text + " icons");
		this.trayIcon = trayIcon;
		this.stage = stage;
		setOnAction(this);
	}

	@Override public void handle(ActionEvent event) {
		switch (option.toLowerCase()) {
			case "white":
				Theme.setWhiteIcons(trayIcon);
				Theme.setWhiteIcons(stage);
				break;
			case "black":
				Theme.setBlackIcons(trayIcon);
				Theme.setBlackIcons(stage);
				break;
		}
		ProgramProperties.INSTANCE.add("theme", option.toLowerCase());
	}
}
