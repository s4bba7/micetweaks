package com.micetweaks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Stores global variables.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class Assets {
	public static final String TITLE = "Micetweaks";
	private static final File                         HOTPLUG_CONF;
	public static        Image                        ICON;
	// Stores all connected devices.
	public static        HashMap<String, DeviceProps> DEVICES_LIST;
	// Parent path to this application.
	private static       String                       PATH;

	static {
		try {
			ICON = ImageIO.read(Main.class.getResource("res/icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PATH = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		PATH = new File(PATH).getParent();
		HOTPLUG_CONF = new File(PATH + "/hotplug.conf");
	}

	/**
	 * @return configuration file "hotplug.conf"
	 */
	public static File getHOTPLUG_CONF() {
		return HOTPLUG_CONF;
	}

	public static String getAppPath() {
		return PATH;
	}

}
