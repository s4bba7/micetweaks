package com.micetweaks.gui;

import javafx.scene.layout.VBox;

/**
 * Created on 16/04/17.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DevPanelModifier {
	private VBox mainPanel;

	public DevPanelModifier(VBox mainPanel) {
		this.mainPanel = mainPanel;
	}

	public void add(VBox devicePanel) {
		mainPanel.getChildren().add(devicePanel);
	}

	public void clear() {
		mainPanel.getChildren().clear();
	}
}
