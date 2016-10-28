package com.micetweaks;

import com.micetweaks.resources.Assets;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created on 28/10/16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class Log {
	private static BufferedWriter out;

	static {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));

		try {
			out = new BufferedWriter(new FileWriter(Assets.getAppPath() + "/log.txt"));
		} catch (IOException e) {
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
