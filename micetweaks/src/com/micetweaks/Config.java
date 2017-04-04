package com.micetweaks;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Properties;

/**
 * Keeps configuration of the program.
 *
 * @author Łukasz 's4bba7' Gąsiorowski.
 */
public class Config {
	private static Properties conf = new Properties();

	/**
	 * Loads configuration file.
	 *
	 * @return hashmap object if file exists. Otherwise returns empty hashmap.
	 */
	@SuppressWarnings({ "unchecked",
			"ResultOfMethodCallIgnored" }) static HashMap<String, DeviceProps> loadDeviceConfig() {
		HashMap<String, DeviceProps> map;
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(Assets.getHotplugConf()))) {
			if (Assets.getHotplugConf().exists()) {
				map = (HashMap<String, DeviceProps>) in.readObject();
			} else {
				map = new HashMap<>();
				Assets.getHotplugConf().createNewFile();
			}
		} catch (Exception e) {
			Assets.getHotplugConf().delete();
			map = new HashMap<>();
		}
		return map;
	}

	/**
	 * Saves devices configuration.
	 */
	public static void saveDeviceConfig() {
		HashMap<String, DeviceProps> conf = loadDeviceConfig();
		// Merge the old config with the new one.
		conf.putAll(Assets.DEVICES_LIST);
		// Clear devices IDs (IDs could change when devices are reconnected, so map may store many unneeded IDs).
		conf.entrySet().forEach(p -> p.getValue().getIds().clear());

		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Assets.getHotplugConf()))) {
			out.writeObject(conf);
			out.close();
		} catch (IOException e) {
			Log.write(e.getMessage());
			JOptionPane.showMessageDialog(null,
					"Cannot saveDeviceConfig the config file. See the log file.\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Saves program's configuration.
	 *
	 * @param option program's value.
	 */
	static void saveAppConfig(Object option) {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(Assets.getAppConf()))) {
			conf.put("firstRun", option.toString());
			conf.store(out, null);
		} catch (IOException e) {
			Log.write("Cannot saveDeviceConfig app config.\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Loads program's configuration.
	 *
	 * @return list with program's parameters.
	 */
	@SuppressWarnings({ "unchecked", "ResultOfMethodCallIgnored" }) static Properties loadAppConfig() {
		if (Assets.getAppConf().exists()) {
			try (BufferedReader in = new BufferedReader(new FileReader(Assets.getAppConf()))) {
				conf.load(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else try {
			Assets.getAppConf().createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conf;
	}
}
