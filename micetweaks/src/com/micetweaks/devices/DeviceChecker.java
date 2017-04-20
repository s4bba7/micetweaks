package com.micetweaks.devices;

import com.micetweaks.Commands;

import java.util.List;

/**
 * Created on 20/04/17.
 * <p>
 * Checks device's intended use.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DeviceChecker {

/*	public static void main(String[] args) {
		boolean b = DeviceChecker.isMouse(16);
		System.out.println(b);
	}*/

	public static boolean isMouse(int id) {
		List<String> deviceProps = Commands.getDeviceProps(id);
		return deviceProps.stream().anyMatch(s -> s.toLowerCase().contains("accel speed"));
	}
}
