package com.micetweaks;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Stores program's log.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class Log {
	private static BufferedWriter out;

	static {
		try {
			out = new BufferedWriter(new FileWriter(Assets.getProgramsPath() + "/log.txt"));
			// Close stream at the program's exit.
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR, cannot write to logfile, but program will continue: " + e
					.getMessage());
			e.printStackTrace();
		}
	}

	public static void write(String s) {
		try {
			out.write(s);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
