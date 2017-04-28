package com.micetweaks.gui.device.components;

import com.micetweaks.gui.Theme;
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
public class BlackIconsThemeItem extends MenuItem implements EventHandler<ActionEvent> {
	private final String text = "Theme: black icons";
	private TrayIcon trayIcon;
	private Stage    stage;

	public BlackIconsThemeItem(TrayIcon trayIcon, Stage stage) {
		setText(text);
		this.trayIcon = trayIcon;
		this.stage = stage;
		setOnAction(this);
	}

	@Override public void handle(ActionEvent event) {
		Theme.setBlackIcons(trayIcon);
		Theme.setBlackIcons(stage);
	}
}
