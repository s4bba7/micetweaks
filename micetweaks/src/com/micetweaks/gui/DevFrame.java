package com.micetweaks.gui;

import com.micetweaks.Assets;
import com.micetweaks.Log;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

	/**
	 * @param isVisible needed for AWT hack - if frame is started in minimized mode it adds +2 to clickCounter.
	 * @throws AWTException
	 */
	public DevFrame(boolean isVisible) {
		setTitle(Assets.TITLE);
		setResizable(false);
		getIcons().add(new Image(Assets.ICON));
		panel.setPadding(new Insets(8, 2, 2, 2));
		pane = new BorderPane();
		pane.setAlignment(saveButton, Pos.CENTER);
		pane.setTop(saveButton);
		pane.setCenter(panel);
		scene = new Scene(pane);
		scene.getStylesheets().add(Assets.CSS_PATH);
		setScene(scene);
		paint();

		// Set system tray.
		try {
			new Tray(this).setup(isVisible);
		} catch (AWTException e) {
			e.printStackTrace();
			Log.write(e.getMessage());
		}
	}

	/**
	 * Add devices to frame.
	 */
	public void paint() {
		Platform.runLater(() -> {
			panel.getChildren().clear();

			Assets.DEVICES_LIST.entrySet().forEach(e -> {
				DevPanel p = new DevPanel(e.getKey(), e.getValue().getSpeed(), e.getValue().getDeceleration());
				p.prepare();
				panel.getChildren().add(p);
			});
			sizeToScene();
		});
	}
}
