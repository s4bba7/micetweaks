package com.micetweaks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Linux commands.
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
	 * @param deviceID         device's ID taken from "xinput" command.
	 * @param deviceSpeedValue device's new speed value.
	 * @throws IOException
	 */
	public static void setSpeedProperty(int deviceID, double deviceSpeedValue) throws IOException {
		// Libinput accepts values between -1 and 1.
		deviceSpeedValue = (deviceSpeedValue * 2) - 1.0;
		String[] command = { "xinput", "set-prop", "" + deviceID, "libinput Accel Speed", "" + deviceSpeedValue };
		Runtime.getRuntime().exec(command);
	}

	public static void setAccelerationState(int deviceID, boolean accelerationState) throws IOException {
		String[] command = { "xinput", "set-prop", "" + deviceID, "libinput Accel Profile Enabled", "0", "1" };
		if (accelerationState) {
			command[4] = "1";
			command[5] = "0";
		}
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
