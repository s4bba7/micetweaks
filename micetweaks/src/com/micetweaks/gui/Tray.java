package com.micetweaks.gui;

import com.micetweaks.Assets;
import com.micetweaks.properties.ProgramProperties;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Properties;

/**
 * Created on 07/11/16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
class Tray {
	private Stage frame;
	private int   clickCounter;
	private TrayIcon   trayIcon          = new TrayIcon(Assets.WHITE_TRAY_ICON);
	private Properties programProperties = ProgramProperties.INSTANCE.getConfig();

	Tray(Stage frame) {
		this.frame = frame;
	}

	/**
	 * @param firstRun needed for AWT hack - if frame is started in minimized mode it adds +2 to clickCounter.
	 * @throws AWTException
	 */
	void setup(boolean firstRun) throws AWTException {
		if (SystemTray.isSupported()) {
			if (!firstRun) clickCounter += 2;
			SystemTray tray = SystemTray.getSystemTray();

			if (programProperties.getProperty("theme").equalsIgnoreCase("white"))
				trayIcon.setImage(Assets.WHITE_TRAY_ICON);
			else trayIcon.setImage(Assets.BLACK_TRAY_ICON);

			trayIcon.setImageAutoSize(true);
			trayIcon.setToolTip(Assets.TITLE);
			tray.add(trayIcon);
			trayhack();
		}
	}

	public TrayIcon getTrayIcon() {
		return trayIcon;
	}

	private void trayhack() {
		// Hack to snoop on global mouse events:
		Toolkit.getDefaultToolkit().addAWTEventListener(e -> {
			if (e.getSource().toString().contains("TrayIcon@")) {
				clickCounter++;
				if (clickCounter % 4 == 0) Platform.runLater(() -> {
					frame.setAlwaysOnTop(true);
					frame.sizeToScene();
					frame.centerOnScreen();
					frame.show();
				});
				else if ((clickCounter + 2) % 4 == 0) Platform.runLater(() -> frame.hide());
			}
		}, MouseEvent.MOUSE_EVENT_MASK);
	}

}
