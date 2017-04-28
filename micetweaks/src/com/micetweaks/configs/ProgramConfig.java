package com.micetweaks.configs;

import com.micetweaks.Assets;
import com.micetweaks.Log;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Keeps configuration of the program.
 *
 * @author Łukasz 's4bba7' Gąsiorowski.
 */
public class ProgramConfig {
	public final static ProgramConfig       INSTANCE = new ProgramConfig();
	private final       String              path     = Assets.getProgramsPath() + "/config";
	private             Map<String, String> config   = new HashMap<>();

	private ProgramConfig() { }

	/**
	 * Loads program's configuration file. If file doesn't exist, it'll be created.
	 *
	 * @return map with program's parameters.
	 */
	public synchronized Map<String, String> load() {
		if (Files.exists(Paths.get(path))) {
			try {
				try (ObjectInput in = new ObjectInputStream(new FileInputStream(path))) {
					config = (HashMap<String, String>) in.readObject();
				} catch (IOException e1) {
					e1.printStackTrace();
					Files.delete(Paths.get(path));
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.write("ERROR, cannot load program's configuration file: " + e.getMessage());
			}
		}
		return config;
	}

	public synchronized void save() {
		try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream(path))) {
			out.writeObject(config);
		} catch (IOException e) {
			Log.write("ERROR, cannot save program's configuration file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Adds program's property.
	 *
	 * @param key   program's property name.
	 * @param value key's data.
	 */
	public synchronized void add(String key, String value) {
		config.put(key.toString(), value.toString());
	}

	/**
	 * Removes program's property.
	 *
	 * @param key program's property name.
	 */
	public synchronized void remove(String key) {
		config.remove(key);
	}
}
