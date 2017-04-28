package com.micetweaks.devices;

import com.micetweaks.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Invoked events on connecting/disconnecting devices.
 * <p>
 * author Łukasz 's4bba7' Gąsiorowski
 */
public class HotPlug {
	/**
	 * Detects plugged in pointer devices. If device is a pointer it'll be added to DeviceObserver map <device name,
	 * device ID>
	 */
	public static void detectUSBPointerDevices(DeviceObserver deviceObserver) {
		String buff;
		Process p = xinputExecute();

		if (p != null) try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
			while ((buff = in.readLine()) != null) {
				String[] dev = buff.split("\t");

				if (dev[2].contains("slave  pointer")) {
					String devName = dev[0].substring(6).trim();

					int id = Integer.parseInt(dev[1].substring(3));
					if (DeviceChecker.isMouse(id)) { deviceObserver.addDevice(devName, id); }
					// When there's no more pointers just break.
				} else if (dev[2].contains("keyboard")) break;
			}
		} catch (IOException e) {
			Log.write(e.getMessage());
			e.printStackTrace();
		}
	}

	private static Process xinputExecute() {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("xinput");
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			Log.write(e.getMessage());
			e.printStackTrace();
		}
		return p;
	}

}
