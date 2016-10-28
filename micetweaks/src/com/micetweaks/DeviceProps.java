package com.micetweaks;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created on 11.10.16.
 * Stores device variables.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DeviceProps implements Serializable {
	private HashSet<Integer> ids = new HashSet<>();
	private double speed;
	private double deceleration;

	public DeviceProps(int id) {
		this.ids.add(id);
	}

	public HashSet<Integer> getIds() {
		return ids;
	}

	public boolean addId(int id) {
		return ids.add(id);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getDeceleration() {
		return deceleration;
	}

	public void setDeceleration(double deceleration) {
		this.deceleration = deceleration;
	}

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		ids.stream().forEach(e -> sb.append(e + " "));
		return sb.toString();
	}
}
