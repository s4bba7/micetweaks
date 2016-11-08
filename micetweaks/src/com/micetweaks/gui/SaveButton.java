package com.micetweaks.gui;

import com.micetweaks.Config;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created on 02/11/16.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
class SaveButton extends JButton implements ActionListener {
	private String title = "Save config";

	SaveButton() {
		setText(title);
		setFocusable(false);
		setForeground(Color.WHITE);
		setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
		addActionListener(this);
	}

	@Override public void actionPerformed(ActionEvent e) {
		Config.saveDeviceConfig();
	}
}
