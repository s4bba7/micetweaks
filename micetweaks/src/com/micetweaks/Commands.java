package com.micetweaks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Linux commands for USB devices.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
@SuppressWarnings("JavaDoc") public class Commands {

/*	public static void main(String[] args) {
		List<String> l = Commands.getDeviceProps(14);
		l.stream().forEach(p -> {System.out.println(p);});
	}*/

	public static List<String> getDeviceProps(int id) {
		List<String> props = new ArrayList<>();
		try {
			Process p = Runtime.getRuntime().exec(new String[] { "xinput", "list-props", "" + id });
			try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
				String buff = "";
				while ((buff = in.readLine()) != null) { props.add(buff); }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	/**
	 * Sets device properties by calling "xinput set-prop" command.
	 *
	 * @param devsID       device's ID set.
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
	 * Sets device properties by calling "xinput set-prop" command.
	 *
	 * @param id           device's ID taken from "xinput" command.
	 * @param speed        0.3 to 1.2 recomended.
	 * @param deceleration 1.0 to 1.8 recommended.
	 * @throws IOException
	 */
	public static void setProp(int id, double speed, double deceleration) throws IOException {
		String[] command = { "xinput", "set-prop", "" + id, "Coordinate Transformation Matrix", "" + speed, "0", "0",
				"0", "" + speed, "0", "0", "0", "" + deceleration };
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
