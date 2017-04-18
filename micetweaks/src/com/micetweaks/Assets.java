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
	public static final String      TITLE    = "Micetweaks";
	public static final String      CSS_PATH = Main.class.getResource("css/main.css").toExternalForm();
	public static       InputStream ICON     = Main.class.getResourceAsStream("res/icon.png");
	public static  Image  TRAY_ICON;
	// Parent path to this application.
	private static String PROGRAMS_PATH;

	static {
		try {
			TRAY_ICON = ImageIO.read(Main.class.getResource("res/icon.png"));
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
