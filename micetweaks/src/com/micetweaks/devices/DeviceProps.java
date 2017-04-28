package com.micetweaks.devices;

import java.io.Serializable;

/**
 * Stores device variables.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DeviceProps implements Serializable, Device {
	private String  name;
	private double  speedValue;
	private boolean isAccelerationActive;

	/**
	 * @param name device's name.
	 */
	public DeviceProps(String name) {
		this.name = name;
	}

	@Override public String getName() { return name; }

	@Override public void setName(String name) { this.name = name; }

	@Override public double getSpeedValue() { return speedValue; }

	@Override public void setSpeedValue(double speedValue) { this.speedValue = speedValue; }

	@Override public boolean isAccelerationActive() { return isAccelerationActive; }

	@Override public void setAccelerationActive(boolean state) { isAccelerationActive = state; }
}
