package com.micetweaks.resources;

import java.io.File;
import java.util.HashSet;

/**
 * Stores global variables.
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Assets {
	/** Parent path to this application. */
	private static String				PATH;
	private static File					HOTPLUG_CONF;
	private static File					STARTUP_CONF;
	public final static File			DEVICES_BUS_PATH	= new File("/proc/bus/input/devices");
	public final static HashSet<String>	HOTPLUG_LIST		= new HashSet<>();
	public final static HashSet<String>	STARTUP_LIST		= new HashSet<>();

	static {
		PATH = Assets.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		PATH = new File(PATH).getParent();
		HOTPLUG_CONF = new File(PATH + "/hotplug.conf");
		STARTUP_CONF = new File(PATH + "/startup.conf");

	}

	/**
	 * @return configuration file "hotplug.conf"
	 */
	public static File getHOTPLUG_CONF() {
		return HOTPLUG_CONF;
	}

	/**
	 * @return configuration file "startup.conf"
	 */
	public static File getSTARTUP_CONF() {
		return STARTUP_CONF;
	}
}
