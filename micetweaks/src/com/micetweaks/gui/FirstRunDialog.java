package com.micetweaks.gui;

import com.micetweaks.Assets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created on 04/04/17.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class FirstRunDialog extends Stage {
	private final String   greetingsText =
			"How-to:\n" + "1. To hide or show program's window just click on the tray icon.\n"
					+ "2. To add configuration click \"add config\" button.\n"
					+ "3. To adjust mouse/touchpad speed press and hold scrollbar, then slide "
					+ "it to the left to decrease property or to the right to increase it. When you "
					+ "release the button the property will be applied immediately." + "\n";
	private       Label    title         = new Label();
	private       TextArea text          = new TextArea();
	private       Button   okButton      = new Button("OK");
	private Scene scene;
	private BorderPane pane = new BorderPane();

	public FirstRunDialog() {
		setDialogProperties();
		setGreetingsText();
		setEventHandlers();
		setLayout();
		setScene();
	}

	private void setEventHandlers() {
		okButton.setOnMouseClicked(e -> { close(); });
	}

	private void setScene() {
		scene = new Scene(pane);
		scene.getStylesheets().add(Assets.CSS_PATH);
		setScene(scene);
	}

	private void setLayout() {
		pane.setTop(title);
		pane.setCenter(text);
		pane.setBottom(okButton);
		BorderPane.setAlignment(okButton, Pos.CENTER);
		BorderPane.setAlignment(title, Pos.CENTER);
	}

	private void setDialogProperties() {
		setTitle("Micetweaks");
		getIcons().add(Assets.WHITE_FRAME_ICON);
		setAlwaysOnTop(true);
		setResizable(false);
		initStyle(StageStyle.UTILITY);
	}

	private void setGreetingsText() {
		title.setId("firstRunTitle");
		title.setText("Thank you for using this software!");

		text.setId("firstRunText");
		text.setEditable(false);
		text.setText(greetingsText);
	}

}
