package com.micetweaks.gui;

import com.micetweaks.Assets;

import java.awt.*;

/**
 * Created on 07/11/16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class Tray {

	public void setup() throws AWTException {
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			TrayIcon icon = new TrayIcon(Assets.ICON);
			icon.setImageAutoSize(true);
			tray.add(icon);
		}
	}
}
