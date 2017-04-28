package com.micetweaks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Stores global variables.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class Assets {
	public static final  String      TITLE         = "Micetweaks";
	public static final  String      CSS_PATH      = Main.class.getResource("css/main.css").toExternalForm();
	public static final  double      SPEED_DEFAULT = 0.500000;
	private static final InputStream BLACK_ICON    = Main.class.getResourceAsStream("res/black-icon.png");
	private static final InputStream WHITE_ICON    = Main.class.getResourceAsStream("res/white-icon.png");
	public static Image BLACK_TRAY_ICON;
	public static Image WHITE_TRAY_ICON;
	public static javafx.scene.image.Image BLACK_FRAME_ICON = new javafx.scene.image.Image(BLACK_ICON);
	public static javafx.scene.image.Image WHITE_FRAME_ICON = new javafx.scene.image.Image(WHITE_ICON);
	// Parent path to this application.
	private static String PROGRAMS_PATH;

	static {
		try {
			BLACK_TRAY_ICON = ImageIO.read(Main.class.getResource("res/black-icon.png"));
			WHITE_TRAY_ICON = ImageIO.read(Main.class.getResource("res/white-icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PROGRAMS_PATH = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		PROGRAMS_PATH = new File(PROGRAMS_PATH).getParent();
	}

	public static String getProgramsPath() {
		return PROGRAMS_PATH;
	}
}
