package com.micetweaks.gui;

import com.micetweaks.Assets;
import com.micetweaks.Log;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Application's main frame.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DevFrame extends Stage {
	private final VBox       panel      = new VBox();
	private       SaveButton saveButton = new SaveButton();
	private BorderPane pane;
	private Scene      scene;
	private boolean    firstRun;

	/**
	 * @param firstRun needed for AWT hack - if frame is started in minimized mode it adds +2 to clickCounter.
	 * @throws AWTException
	 */
	public DevFrame(boolean firstRun) {
		this.firstRun = firstRun;
		setFrameProperties();
		setLayout();
		setScene();
		setSystemTray();
	}

	private void setLayout() {
		panel.setPadding(new Insets(8, 2, 2, 2));
		pane = new BorderPane();
		pane.setAlignment(saveButton, Pos.CENTER);
		pane.setTop(saveButton);
		pane.setCenter(panel);
	}

	private void setFrameProperties() {
		setTitle(Assets.TITLE);
		setResizable(false);
		getIcons().add(new javafx.scene.image.Image(Assets.ICON));
	}

	private void setScene() {
		scene = new Scene(pane);
		scene.getStylesheets().add(Assets.CSS_PATH);
		setScene(scene);
	}

	private void setSystemTray() {
		try {
			new Tray(this).setup(firstRun);
		} catch (AWTException e) {
			e.printStackTrace();
			Log.write(e.getMessage());
		}
	}

	public VBox getPanel() {
		return panel;
	}
}
