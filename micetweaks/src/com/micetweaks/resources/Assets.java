package com.micetweaks.resources;

import com.micetweaks.DeviceProps;
import com.micetweaks.Main;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Stores global variables.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class Assets {
	public static final String                       TITLE              = "Micetweaks";
	// Stores remembered devices.
	public final static HashSet<String>              SAVED_DEVICES_LIST = new HashSet<>();
	// Stores all connected devices.
	public static       HashMap<String, DeviceProps> DEVICES_LIST       = new HashMap<>();
	// Parent path to this application.
	private static String PATH;
	private static File   HOTPLUG_CONF;

	static {
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

}
