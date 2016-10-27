package com.micetweaks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

import com.micetweaks.resources.Assets;

/**
 * Linux wrapped commands for USB devices.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class Commands {

	/**
	 * Sets devices properties by calling "xinput set-prop" command.
	 *
	 * @param devsID         device's ID set.
	 * @param speed          0.3 to 1.2 recomended.
	 * @param deacceleration 1.0 to 1.8 recommended.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void setProp(HashSet<? extends Number> devsID, double speed,
			double deacceleration) throws IOException, InterruptedException {
		Iterator<? extends Number> i = devsID.iterator();
		while (i.hasNext()) {
			String[] command = { "xinput", "set-prop", "" + i.next(),
					"Coordinate Transformation Matrix", "" + speed, "0", "0", "0", "" + speed, "0", "0", "0",
					"" + deacceleration };
			Runtime.getRuntime().exec(command);
		}
	}

	/**
	 * Sets devices properties by calling "xinput set-prop" command.
	 *
	 * @param devName        device name taken from "xinput" command.
	 * @param speed          0.3 to 1.2 recomended.
	 * @param deacceleration 1.0 to 1.8 recommended.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void setProp(String devName, String speed, String deacceleration)
			throws IOException, InterruptedException {
		String[] command = { "xinput", "set-prop", devName, "Coordinate Transformation Matrix",
				speed, "0", "0", "0", speed, "0", "0", "0", deacceleration };
		Runtime.getRuntime().exec(command);
	}

	/**
	 * Reads /proc/bus/input/devices, looks for device vendorID and returns
	 * device name.
	 *
	 * @param vendorID device vendorID.
	 * @return device name.
	 * @throws IOException
	 *//*
	public static String getDevName(String vendorID) throws IOException {
		String s = null;
		if (vendorID != null) {
			BufferedReader in = new BufferedReader(new FileReader(Assets.DEVICES_BUS_PATH));
			while ((s = in.readLine()) != null) if (s.contains(vendorID)) {
				s = in.readLine();
				in.close();
				int i = s.indexOf('=');
				return s.substring(i + 2, s.length() - 1);
			}
			in.close();
		}
		return s;
	}

	*//**
	 * Looks for device ID by calling "xinput" command. Devices like gaming mice
	 * could have two or more IDs.
	 *
	 * @param devName device name.
	 * @return device ID.
	 * @throws IOException
	 * @throws InterruptedException
	 *//*
	public static HashSet<Integer> getDevsID(String devName)
			throws IOException, InterruptedException {
		HashSet<Integer> devs = new HashSet<>();
		if (devName != null) {
			Process p = Runtime.getRuntime().exec("xinput");
			p.waitFor();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = "";
			int i = 0;
			int j = 0;
			while ((s = in.readLine()) != null) if (s.contains(devName)) {
				i = s.indexOf('=');
				j = s.indexOf('\t', i + 1);
				devs.add(Integer.parseInt(s.substring(i + 1, j)));
			}
			in.close();
		}
		return devs;
	}*/
}
