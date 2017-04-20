package com.micetweaks.devices;

/**
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public interface Device {
	String getName();

	void setName(String name);

	double getSpeed();

	void setSpeed(double speed);

	double getDeceleration();

	void setDeceleration(double deceleration);
}
