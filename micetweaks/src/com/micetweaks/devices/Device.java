package com.micetweaks.devices;

/**
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public interface Device {
	String getName();

	void setName(String name);

	double getSpeedValue();

	void setSpeedValue(double speedValue);

	boolean isAccelerationActive();

	void setAccelerationActive(boolean state);
}
