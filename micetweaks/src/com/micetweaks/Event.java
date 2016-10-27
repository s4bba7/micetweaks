package com.micetweaks;

import java.io.IOException;
import java.util.Iterator;

import com.micetweaks.resources.Assets;

/**
 * Events invoked when USB device is plugged in.
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Event {
	/**
	 * Launched when USB device is plugged in.
	 * 
	 * @param vendorID
	 *            device's vendor ID number.
	 *
	 * @return DeviceProps info which contains device name, speed and deacceleration.
	 */
	public static String[] launchHotplugConfig(String vendorID) {
		// Stores device name, speed and deacceleration
		String[] devinfo = new String[3];

	/*	Iterator<String> i = Assets.SAVED_DEVICES_LIST.iterator();
		while (i.hasNext()) {
			String dev = i.next();
			if (dev.contains(vendorID)) {
				String[] opt = dev.split("\\|");
				try {
					// Wait for xinput to react.
					Thread.sleep(500);
					devinfo[0] = Commands.getDevName(opt[0]);
					devinfo[1] = opt[1]; // speed
					devinfo[2] = opt[2]; // deacceleration
					Commands.setProp(Commands.getDevsID(devinfo[0]), opt[1], opt[2]);
					System.out.println(opt[3] + "'s config launched.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// If device isn't on the list detect the new device
		if (devinfo[0] == null) {
			try {
				String devName = Commands.getDevName(vendorID);
				if (devName != null) {
					double speed = 0.8;
					double deacceleration = 1.4;
					devinfo[0] = devName;
					devinfo[1] = "" + speed;
					devinfo[2] = "" + deacceleration;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		return devinfo;
	}
}
