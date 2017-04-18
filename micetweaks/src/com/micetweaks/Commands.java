package com.micetweaks;

import java.io.IOException;
import java.util.HashSet;

/**
 * Linux commands for USB devices.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
@SuppressWarnings("JavaDoc") public class Commands {
	/**
	 * Sets devices properties by calling "xinput set-prop" command.
	 *
	 * @param devsID       com.micetweaks.devices's ID set.
	 * @param speed        0.3 to 1.2 recomended.
	 * @param deceleration 1.0 to 1.8 recommended.
	 * @throws IOException
	 */
	public static void setProp(HashSet<? extends Number> devsID, double speed, double deceleration) throws IOException {
		for (Number aDevsID : devsID) {
			String[] command = { "xinput", "set-prop", "" + aDevsID, "Coordinate Transformation Matrix", "" + speed,
					"0", "0", "0", "" + speed, "0", "0", "0", "" + deceleration };
			Runtime.getRuntime().exec(command);
		}
	}

	/**
	 * Sets devices properties by calling "xinput set-prop" command.
	 *
	 * @param devName      com.micetweaks.devices name taken from "xinput" command.
	 * @param speed        0.3 to 1.2 recomended.
	 * @param deceleration 1.0 to 1.8 recommended.
	 * @throws IOException
	 */
	public static void setProp(String devName, String speed, String deceleration) throws IOException {
		String[] command = { "xinput", "set-prop", devName, "Coordinate Transformation Matrix", speed, "0", "0", "0",
				speed, "0", "0", "0", deceleration };
		Runtime.getRuntime().exec(command);
	}

	/**
	 * Checks system is having "xinput" and "udevadm" packages. If not throw exception.
	 *
	 * @throws IOException
	 */
	static void checkSystemDependency() throws IOException {
		Runtime.getRuntime().exec("xinput");
		Runtime.getRuntime().exec(new String[] { "udevadm", "--help" });
	}
}
