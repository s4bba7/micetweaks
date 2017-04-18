package com.micetweaks.devices;

import java.util.HashSet;

/**
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public interface Device {
	HashSet<Integer> getIds();

	boolean addId(int id);

	double getSpeed();

	void setSpeed(double speed);

	double getDeceleration();

	void setDeceleration(double deceleration);
}
