package com.micetweaks.gui;

import com.micetweaks.Config;
import com.micetweaks.Log;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created on 02/11/16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class SaveButton extends JButton implements ActionListener {
	private String title = "Save config";

	public SaveButton() {
		setText(title);
		setFocusable(false);
		setForeground(Color.WHITE);
		setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
		addActionListener(this);
	}

	@Override public void actionPerformed(ActionEvent e) {
		try {
			Config.save();
		} catch (IOException f) {
			Log.write(f.getMessage());
			JOptionPane.showMessageDialog(null, "Cannot save the config file. See the log file.");
		}
	}
}
