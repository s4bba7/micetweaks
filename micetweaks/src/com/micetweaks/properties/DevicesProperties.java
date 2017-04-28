package com.micetweaks.properties;

import com.micetweaks.Assets;
import com.micetweaks.Log;
import com.micetweaks.devices.Device;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 18/04/17.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DevicesProperties {
	public final static DevicesProperties   INSTANCE = new DevicesProperties();
	private final       String              path     = Assets.getProgramsPath() + "/devices";
	private             Map<String, Device> config   = new HashMap<>();

	private DevicesProperties() { }

	public void updateConfig(Device device) {
		config.put(device.getName(), device);
	}

	/**
	 * Loads configuration file. If file does not exist, it'll be created.
	 *
	 * @return hashmap object with devices configuration if file exists. Otherwise returns empty hashmap.
	 */
	public synchronized Map<String, Device> load() {
		try {
			if (Files.exists(Paths.get(path))) {
				try (ObjectInput in = new ObjectInputStream(new FileInputStream(path))) {
					config = (Map<String, Device>) in.readObject();
				} catch (Exception e) {
					e.printStackTrace();
					if (e.getMessage() == null) {
						Log.write("Devices configuration file is empty - loading ignored" + ".");
					} else Log.write("ERROR, cannot load devices configuration file: " + e.getMessage());
					Files.delete(Paths.get(path));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.write("ERROR, cannot create new devices configuration file: " + e.getMessage());
		}
		return config;
	}

	public synchronized void save() {
		try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream(path))) {
			out.writeObject(config);
		} catch (IOException e) {
			e.printStackTrace();
			Log.write("ERROR, cannot save device's configuration file: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Cannot save device's configuration file. See the log file:\n" + e
					.getMessage());
		}
	}
}
