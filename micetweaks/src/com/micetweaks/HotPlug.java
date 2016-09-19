package com.micetweaks;

/**
 * Copyright (C) 2014 Klaus Reimer <k@ailis.de>
 */
import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.HotplugCallback;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

import com.micetweaks.Mode.STATE;

/**
 * Event thread reacting on USB device connection.
 * 
 * @author Klaus Reimer <k@ailis.de>. Modded by Łukasz 's4bba7' Gąsiorowski.
 *
 */
public class HotPlug {

	static class EventHandlingThread extends Thread {
		private volatile boolean abort;

		/**
		 * Aborts the event handling thread.
		 */
		public void abort() {
			this.abort = true;
		}

		@Override
		public void run() {
			while (!this.abort) {
				// Let libusb handle pending events. This blocks until events
				// have been handled, a hotplug callback has been deregistered
				// or the specified time of 1 second (Specified in
				// Microseconds) has passed.
				int result = LibUsb.handleEventsTimeout(null, 1000000);
				if (result != LibUsb.SUCCESS)
					throw new LibUsbException("Unable to handle events", result);
			}
		}
	}

	/**
	 * The hotplug callback handler
	 */
	static class Callback implements HotplugCallback {
		@Override
		public int processEvent(Context context, Device device, int event, Object userData) {
			DeviceDescriptor descriptor = new DeviceDescriptor();
			int result = LibUsb.getDeviceDescriptor(device, descriptor);
			if (result != LibUsb.SUCCESS)
				throw new LibUsbException("Unable to read device descriptor", result);

			// Device connected.
			if (event == LibUsb.HOTPLUG_EVENT_DEVICE_ARRIVED) {
				String vendorID = String.format("%04x", descriptor.idVendor());

				if (Mode.getState() == STATE.NORMAL) Event.launchHotplugConfig(vendorID);
				else if (Mode.getState() == STATE.SETUP) Event.launchSetupProcess(vendorID);
			}
			return 0;
		}
	}
}
