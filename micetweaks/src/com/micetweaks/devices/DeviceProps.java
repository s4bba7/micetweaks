package com.micetweaks.devices;

import java.io.Serializable;

/**
 * Stores device variables.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DeviceProps implements Serializable, Device {
	private String name;
	private double speed;
	private double deceleration;

	/**
	 * @param name device's name.
	 */
	public DeviceProps(String name) {
		this.name = name;
	}

	@Override public String getName() { return name; }

	@Override public void setName(String name) { this.name = name; }

	@Override public double getSpeed() {
		return speed;
	}

	@Override public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override public double getDeceleration() {
		return deceleration;
	}

	@Override public void setDeceleration(double deceleration) {
		this.deceleration = deceleration;
	}

}
