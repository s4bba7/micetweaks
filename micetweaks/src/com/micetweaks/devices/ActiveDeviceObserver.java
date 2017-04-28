package com.micetweaks.devices;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 20/04/17.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class ActiveDeviceObserver implements DeviceObserver {
	private Map<String, Integer> devices = new HashMap<>();

	@Override public void addDevice(String deviceName, int id) {
		devices.put(deviceName, id);
	}

	@Override public void removeDevices() { devices.clear(); }

	@Override public Map<String, Integer> getActiveDevices() {
		return devices;
	}
}
