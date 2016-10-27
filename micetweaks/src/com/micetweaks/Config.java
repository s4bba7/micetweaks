package com.micetweaks;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

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
			Assets.DEVICES_LIST.clear();
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

	/**
	 * Applys config file by invoking "xinput set-props" command for every device in this config.
	 */
	public static void apply() {
		Assets.DEVICES_LIST.entrySet().stream().forEach(a -> {
			DeviceProps props = a.getValue();
			try {
				Commands.setProp(props.getIds(), props.getSpeed(), props.getDeceleration());
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		});
	}
}
