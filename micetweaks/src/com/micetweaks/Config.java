package com.micetweaks;

import java.io.*;
import java.util.HashMap;

/**
 * Keeps configuration of the program.
 *
 * @author Łukasz 's4bba7' Gąsiorowski.
 */
public class Config {
	/**
	 * Loads configuration file.
	 *
	 * @return hashmap object if file exists. Otherwise returns empty hashmap.
	 */
	public static HashMap<String, DeviceProps> load() {
		HashMap<String, DeviceProps> map;
		try {
			if (Assets.getHOTPLUG_CONF().exists()) {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(Assets.getHOTPLUG_CONF()));
				map = (HashMap<String, DeviceProps>) in.readObject();
				in.close();
			} else {
				map = new HashMap<>();
				Assets.getHOTPLUG_CONF().createNewFile();
			}
		} catch (Exception e) {
			Assets.getHOTPLUG_CONF().delete();
			map = new HashMap<>();
		}
		return map;
	}

	/**
	 * Saves devices configuration.
	 */
	public static void save() throws IOException {
		HashMap<String, DeviceProps> conf = load();
		// Merge old config with new one.
		conf.putAll(Assets.DEVICES_LIST);
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Assets.getHOTPLUG_CONF()));
		out.writeObject(conf);
		out.close();
	}

}
