package com.micetweaks.gui;

import com.micetweaks.Assets;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created on 28/04/17.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class Theme {
	public static void setWhiteIcons(Object component) {
		if (component instanceof TrayIcon) {
			TrayIcon icon = (TrayIcon) component;
			icon.setImage(Assets.WHITE_TRAY_ICON);
		} else if (component instanceof Stage) {
			Stage stage = (Stage) component;
			stage.getIcons().clear();
			stage.getIcons().add(Assets.WHITE_FRAME_ICON);
		}
	}

	public static void setBlackIcons(Object component) {
		if (component instanceof TrayIcon) {
			TrayIcon icon = (TrayIcon) component;
			icon.setImage(Assets.BLACK_TRAY_ICON);
		} else if (component instanceof Stage) {
			Stage stage = (Stage) component;
			stage.getIcons().clear();
			stage.getIcons().add(Assets.BLACK_FRAME_ICON);
		}
	}
}
