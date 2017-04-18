package com.micetweaks.devices;

import com.micetweaks.Log;
import com.micetweaks.configs.DevicesConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Invoked events on connecting/disconnecting devices.
 * <p>
 * author Łukasz 's4bba7' Gąsiorowski
 */
public class HotPlug {
	/**
	 * Detects plugged in devices and adds or removes them from Assets.DEVICES_LIST.
	 *
	 * @param removeFlag com.micetweaks.devices might be plugged off so mark it TRUE to additionally remove that com.micetweaks.devices from list.
	 */
	public static void detectUsbDevices(boolean removeFlag) {
		String buff;
		Process p = xinputExecute();
		Set<String> devNameBuff = new HashSet<>();

		if (p != null) try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
			while ((buff = in.readLine()) != null) {
				String[] dev = buff.split("\t");

				if (dev[2].contains("slave  pointer")) {
					String devName = dev[0].substring(6).trim();

					if (!devName.equalsIgnoreCase("Virtual core XTEST pointer")) {
						int id = Integer.parseInt(dev[1].substring(3));
						devNameBuff.add(devName);

						// If device exist in hashmap just add another devID into it.
						if (DevicesConfig.INSTANCE.getConfig().containsKey(devName)) {
							Device devID = DevicesConfig.INSTANCE.getConfig().get(devName);
							devID.addId(id);
						} else {
							Device devID = new DeviceProps(id);
							DevicesConfig.INSTANCE.getConfig().put(devName, devID);
						}
					}
					// When there's no more pointers just break.
				} else if (dev[2].contains("keyboard")) break;
			}

			// Check if any com.micetweaks.devices is plugged off. If so remove it from list.
			if (removeFlag) {
				for (Iterator<Map.Entry<String, Device>> i = DevicesConfig.INSTANCE.getConfig().entrySet().iterator(); i
						.hasNext(); ) {
					String devName = i.next().getKey();
					if (!devNameBuff.contains(devName)) i.remove();
				}
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
