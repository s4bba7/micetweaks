package com.micetweaks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Stores global variables.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class Assets {
	public static final String TITLE = "Micetweaks";
	private static final File HOTPLUG_CONF;
	private static final File APP_CONF;
	public static final String      CSS_PATH = Main.class.getResource("css/main.css").toExternalForm();
	public static       InputStream ICON     = Main.class.getResourceAsStream("res/icon.png");
	public static  Image                        TRAY_ICON;
	// Stores all connected devices.
	public static  HashMap<String, DeviceProps> DEVICES_LIST;
	// Parent path to this application.
	private static String                       PATH;

	static {
		try {
			TRAY_ICON = ImageIO.read(Main.class.getResource("res/icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PATH = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		PATH = new File(PATH).getParent();
		HOTPLUG_CONF = new File(PATH + "/hotplug.conf");
		APP_CONF = new File(PATH + "/micetweaks.conf");
	}

	/**
	 * @return device list file "hotplug.conf"
	 */
	static File getHotplugConf() {
		return HOTPLUG_CONF;
	}

	/**
	 * @return program configuration file "micetweaks.conf"
	 */
	static File getAppConf() {
		return APP_CONF;
	}

	static String getAppPath() {
		return PATH;
	}

}
