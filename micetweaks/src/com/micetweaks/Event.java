package com.micetweaks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import com.micetweaks.resources.Assets;

/**
 * Events invoked when USB device is plugged in and at application startup.
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Event {
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Launched at application startup.
	 */
	public static void launchStartupConfig() {
		Iterator<String> i = Assets.STARTUP_LIST.iterator();
		while (i.hasNext()) {
			String dev = i.next();
			String[] opt = dev.split("\\|");
			try {
				// Wait for xinput to react.
				Thread.sleep(500);
				Dev.setProp(opt[0], opt[1], opt[2]);
				System.out.println(opt[0] + "'s config launched.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Launched when USB device is plugged in.
	 * 
	 * @param vendorID
	 *            device's vendor ID number.
	 */
	public static void launchHotplugConfig(String vendorID) {
		Iterator<String> i = Assets.HOTPLUG_LIST.iterator();
		while (i.hasNext()) {
			String dev = i.next();
			if (dev.contains(vendorID)) {
				String[] opt = dev.split("\\|");
				try {
					// Wait for xinput to react.
					Thread.sleep(500);
					Dev.setProp(Dev.getDevsID(Dev.getDevName(opt[0])), opt[1], opt[2]);
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
	}

	/**
	 * Launched in setup mode.
	 * 
	 * @param vendorID
	 *            device's vendor ID number.
	 */
	public static void launchSetupProcess(String vendorID) {
		try {
			String devName = Dev.getDevName(vendorID);
			if (devName != null) {
				System.out.print("Found \"" + devName + "\"! Vendor ID is " + vendorID
						+ ". Would you like to add it to config? (y|N): ");
				String question = in.readLine();
				if (question.equalsIgnoreCase("y") || question.equalsIgnoreCase("yes")) {
					double speed = 0;
					double deacceleration = 0;

					try {
						System.out.print("\nSet pointer speed (default 0.7): ");
						speed = Double.parseDouble(in.readLine());
					} catch (Exception e) {
						speed = 0.7;
					}
					try {
						System.out.print("\nSet pointer deacceleration (default 1.4): ");
						deacceleration = Double.parseDouble(in.readLine());
					} catch (Exception e) {
						deacceleration = 1.4;
					}

					Config.save(vendorID, "" + speed, "" + deacceleration, devName);
					System.out.println("\nConfig added.");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
