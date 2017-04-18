package com.micetweaks.devices;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Stores com.micetweaks.devices variables.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DeviceProps implements Serializable, Device {
	private final HashSet<Integer> ids = new HashSet<>();
	private double speed;
	private double deceleration;

	/**
	 * @param id com.micetweaks.devices's identification number taken from "xinput" command.
	 */
	public DeviceProps(int id) {
		this.ids.add(id);
	}

	@Override public HashSet<Integer> getIds() {
		return ids;
	}

	@Override public boolean addId(int id) {
		return ids.add(id);
	}

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

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		ids.forEach(e -> sb.append(e).append(" "));
		return sb.toString();
	}
}
