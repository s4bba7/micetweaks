package com.micetweaks;

/**
 * Keeps mode of the application. Allowed modes are "normal" and "setup". 
 * 
 * @author Łukasz 's4bba7' Gąsiorowski
 *
 */
public class Mode {
	public static enum STATE {
		NORMAL, SETUP
	};

	private static STATE state = STATE.NORMAL;

	public static STATE getState() {
		return state;
	}

	public static void setState(STATE s) {
		switch (s.toString().toLowerCase()) {
			case "setup":
				state = STATE.SETUP;
				break;

			default:
				state = STATE.NORMAL;
				break;
		}
	}
}
