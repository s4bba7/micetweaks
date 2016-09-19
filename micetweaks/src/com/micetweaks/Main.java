package com.micetweaks;

import org.usb4java.HotplugCallbackHandle;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

import com.micetweaks.HotPlug.Callback;
import com.micetweaks.HotPlug.EventHandlingThread;
import com.micetweaks.Mode.STATE;

/**
 * @author Klaus Reimer <k@ailis.de>. Modded by Łukasz 's4bba7' Gąsiorowski.
 */
public class Main {

	public static void main(String[] args) throws Exception {

		// Check application mode
		if (args.length > 0 && args[0].equalsIgnoreCase(STATE.SETUP.toString())) {
			Mode.setState(STATE.SETUP);
		} else Mode.setState(STATE.NORMAL);

		// Load the config
		Config.load();

		System.out.print(Mode.getState().toString().toUpperCase() + " MODE.");
		if (Mode.getState().toString().equalsIgnoreCase(STATE.NORMAL.toString())) {
			System.out.println(" Hit enter to exit the application.");
			// Launch startup config. Needed for touchpads etc.
			Event.launchStartupConfig();
		} else System.out.println();

		// Initialize the libusb context
		int result = LibUsb.init(null);
		if (result != LibUsb.SUCCESS) { throw new LibUsbException("Unable to initialize libusb",
				result); }

		// Check if hotplug is available
		if (!LibUsb.hasCapability(LibUsb.CAP_HAS_HOTPLUG)) {
			System.err.println("libusb doesn't support hotplug on this system");
			System.exit(1);
		}

		// Start the event handling thread
		EventHandlingThread thread = new EventHandlingThread();
		thread.start();

		// Register the hotplug callback
		HotplugCallbackHandle callbackHandle = new HotplugCallbackHandle();
		result = LibUsb.hotplugRegisterCallback(null,
				LibUsb.HOTPLUG_EVENT_DEVICE_ARRIVED | LibUsb.HOTPLUG_EVENT_DEVICE_LEFT,
				LibUsb.HOTPLUG_ENUMERATE, LibUsb.HOTPLUG_MATCH_ANY, LibUsb.HOTPLUG_MATCH_ANY,
				LibUsb.HOTPLUG_MATCH_ANY, new Callback(), null, callbackHandle);
		if (result != LibUsb.SUCCESS) { throw new LibUsbException(
				"Unable to register hotplug callback", result); }

		if (Mode.getState().toString().equalsIgnoreCase(STATE.SETUP.toString())) {
			System.out.println(
					"Setup is finished. You may now restart the app without any arguments.");
		} else {
			// Exit the application.
			System.in.read();
			System.out.println("BYE!");
		}

		// Unregister the hotplug callback and stop the event handling thread
		thread.abort();
		LibUsb.hotplugDeregisterCallback(null, callbackHandle);
		thread.join();

		// Deinitialize the libusb context
		LibUsb.exit(null);
	}

}
