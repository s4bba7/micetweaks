package com.micetweaks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	 * Loads startup and hotplug configuration files of the devices into two
	 * hashsets - STARTUP_LIST and HOTPLUG_LIST.
	 */
	public static void load() throws IOException {
		if (Assets.getSTARTUP_CONF().exists()) {
			BufferedReader in = new BufferedReader(new FileReader(Assets.getSTARTUP_CONF()));
			String s = "";
			while ((s = in.readLine()) != null)
				Assets.STARTUP_LIST.add(s);
			in.close();
		} else {
			Assets.getSTARTUP_CONF().createNewFile();
		}

		if (Assets.getHOTPLUG_CONF().exists()) {
			BufferedReader in = new BufferedReader(new FileReader(Assets.getHOTPLUG_CONF()));
			String s = "";
			while ((s = in.readLine()) != null)
				Assets.HOTPLUG_LIST.add(s);
			in.close();
		} else {
			Assets.getHOTPLUG_CONF().createNewFile();
		}
	}

	/**
	 * Saves hotplug configuration in sequence: Device VendorID, Speed,
	 * Deacceleration, Device name. Data is appended to file.
	 * 
	 * @param args
	 *            Configuration of the device. Required order is: Device
	 *            VendorID, Speed, Deacceleration, Device name.
	 */
	public static void save(String... args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(Assets.getHOTPLUG_CONF()));
		String s = "";
		while ((s = in.readLine()) != null)
			devlistTemp.add(s);
		in.close();

		Iterator<String> i = devlistTemp.iterator();
		if (i.hasNext()) while (i.hasNext()) {
			String[] split = i.next().split("\\|");
			if (split[0].equalsIgnoreCase(args[0])) {
				i.remove();
				devlistTemp.add(args[0] + "|" + args[1] + "|" + args[2] + "|" + args[3]);
				break;
			} else if (!i.hasNext()) {
				devlistTemp.add(args[0] + "|" + args[1] + "|" + args[2] + "|" + args[3]);
				break;
			}
		}
		else devlistTemp.add(args[0] + "|" + args[1] + "|" + args[2] + "|" + args[3]);

		i = devlistTemp.iterator();
		BufferedWriter out = new BufferedWriter(new FileWriter(Assets.getHOTPLUG_CONF()));
		while (i.hasNext())
			out.write(i.next() + "\n");
		out.close();
	}
}
