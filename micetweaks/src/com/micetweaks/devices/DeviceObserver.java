package com.micetweaks.devices;

import java.util.Map;

/**
 * Created on 20/04/17.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public interface DeviceObserver {
	void addDevice(String deviceName, int id);

	void removeDevices();

	Map<String, Integer> getActiveDevices();
}
