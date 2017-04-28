package com.micetweaks.gui;

import com.micetweaks.gui.device.components.BlackIconsThemeItem;
import com.micetweaks.gui.device.components.SaveMenuItem;
import com.micetweaks.gui.device.components.WhiteIconsThemeItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created on 28/04/17.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class FrameMenu extends MenuBar {
	private Menu optionsMenu = new Menu("Options");

	private Menu themeMenuItem = new Menu("Theme");
	private MenuItem blackIconsThemeItem;
	private MenuItem whiteIconsThemeItem;
	private MenuItem saveConfigItem = new SaveMenuItem();

	public FrameMenu(TrayIcon trayIcon, Stage stage) {
		blackIconsThemeItem = new BlackIconsThemeItem(trayIcon, stage);
		whiteIconsThemeItem = new WhiteIconsThemeItem(trayIcon, stage);
		createMenubarOptions();
		createOptionsItems();
	}

	private void createMenubarOptions() {
		getMenus().addAll(optionsMenu);
	}

	private void createOptionsItems() {
		themeMenuItem.getItems().addAll(whiteIconsThemeItem, blackIconsThemeItem);
		optionsMenu.getItems().addAll(saveConfigItem, themeMenuItem);
	}
}
