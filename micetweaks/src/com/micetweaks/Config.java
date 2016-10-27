package com.micetweaks;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.micetweaks.resources.Assets;

/**
 * Keeps configuration of the program.
 *
 * @author Łukasz 's4bba7' Gąsiorowski.
 */
public class Config {
	private static HashSet<String> devlistTemp = new HashSet<>();

	/**
	 * Loads configuration file of the devices into hashset - SAVED_DEVICES_LIST.
	 */
	public static void load() {
		try {
			if (Assets.getHOTPLUG_CONF().exists()) {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(Assets.getHOTPLUG_CONF()));
				Assets.DEVICES_LIST = (HashMap<String, DeviceProps>) in.readObject();
				in.close();
			} else {
				Assets.getHOTPLUG_CONF().createNewFile();
			}
		} catch (Exception e) {
			Assets.getHOTPLUG_CONF().delete();
		}
	}

	/**
	 * Saves devices configuration.
	 */
	public static void save() throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Assets.getHOTPLUG_CONF()));
		out.writeObject(Assets.DEVICES_LIST);
		out.close();
	}
}
