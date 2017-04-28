package com.micetweaks.gui;

import com.micetweaks.Assets;
import com.micetweaks.Log;
import javafx.geometry.Insets;
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
	private final VBox panel = new VBox();

	private BorderPane                   pane;
	private Scene                        scene;
	private boolean                      firstRun;
	private Tray                         tray;
	private javafx.scene.control.MenuBar menuBar;

	/**
	 * @param firstRun needed for AWT hack - if frame is started in minimized mode it adds +2 to clickCounter.
	 * @throws AWTException
	 */
	public DevFrame(boolean firstRun) {
		this.firstRun = firstRun;
		setFrameProperties();
		setSystemTray();
		setLayout();
		setScene();
	}

	private void setLayout() {
		panel.setPadding(new Insets(8, 2, 2, 2));
		menuBar = new FrameMenu(tray.getTrayIcon(), this);
		pane = new BorderPane();
		pane.setTop(menuBar);
		pane.setCenter(panel);
	}

	private void setFrameProperties() {
		setTitle(Assets.TITLE);
		setResizable(false);
		getIcons().add(Assets.WHITE_FRAME_ICON);
	}

	private void setScene() {
		scene = new Scene(pane);
		scene.getStylesheets().add(Assets.CSS_PATH);
		setScene(scene);
	}

	private void setSystemTray() {
		try {
			tray = new Tray(this);
			tray.setup(firstRun);
		} catch (AWTException e) {
			e.printStackTrace();
			Log.write(e.getMessage());
		}
	}

	public VBox getPanel() {
		return panel;
	}
}
