package com.micetweaks.properties;

import com.micetweaks.Assets;
import com.micetweaks.Log;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Keeps configuration of the program.
 *
 * @author Łukasz 's4bba7' Gąsiorowski.
 */
public class ProgramProperties {
	public final static ProgramProperties INSTANCE = new ProgramProperties();
	private final       String            path     = Assets.getProgramsPath() + "/config";
	private             Properties        config   = new Properties();

	private ProgramProperties() { }

	public Properties getConfig() { return config; }

	/**
	 * Loads program's configuration file. If file doesn't exist, it'll be created.
	 *
	 * @return map with program's parameters.
	 */
	public synchronized Properties load() {
		if (Files.exists(Paths.get(path))) {
			try {
				try (ObjectInput in = new ObjectInputStream(new FileInputStream(path))) {
					config = (Properties) in.readObject();
				} catch (IOException e1) {
					e1.printStackTrace();
					Files.delete(Paths.get(path));
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.write("ERROR, cannot load program's configuration file: " + e.getMessage());
			}
		} else {
			createDefaultProperties();
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

	private void createDefaultProperties() {
		config.put("first run", "true");
		config.put("theme", "white");
	}
}
