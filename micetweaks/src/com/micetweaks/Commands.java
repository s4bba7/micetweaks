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
	 * @param devsID       device's ID set.
	 * @param speed        0.3 to 1.2 recomended.
	 * @param deceleration 1.0 to 1.8 recommended.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void setProp(HashSet<? extends Number> devsID, double speed, double deceleration)
			throws IOException, InterruptedException {
		Iterator<? extends Number> i = devsID.iterator();
		while (i.hasNext()) {
			String[] command = { "xinput", "set-prop", "" + i.next(), "Coordinate Transformation Matrix", "" + speed,
					"0", "0", "0", "" + speed, "0", "0", "0", "" + deceleration };
			Runtime.getRuntime().exec(command);
		}
	}

	/**
	 * Sets devices properties by calling "xinput set-prop" command.
	 *
	 * @param devName      device name taken from "xinput" command.
	 * @param speed        0.3 to 1.2 recomended.
	 * @param deceleration 1.0 to 1.8 recommended.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void setProp(String devName, String speed, String deceleration)
			throws IOException, InterruptedException {
		String[] command = { "xinput", "set-prop", devName, "Coordinate Transformation Matrix", speed, "0", "0", "0",
				speed, "0", "0", "0", deceleration };
		Runtime.getRuntime().exec(command);
	}

	public static void checkSystemDependency() throws IOException, InterruptedException {
		Process p = Runtime.getRuntime().exec("xinput");
		p = Runtime.getRuntime().exec(new String[] { "udevadm", "--help" });
	}
}
