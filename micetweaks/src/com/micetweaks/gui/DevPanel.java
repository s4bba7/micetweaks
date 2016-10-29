package com.micetweaks.gui;

import com.micetweaks.Assets;
import com.micetweaks.Commands;
import com.micetweaks.DeviceProps;
import com.micetweaks.Log;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Stores device regulators.
 *
 * @author Łukasz 's4bba7' Gąsiorowski
 */
public class DevPanel extends JPanel implements MouseListener {
	private double       speed;
	private double       deceleration;
	private TitledBorder nameBorder;
	private TitledBorder speedBorder;
	private TitledBorder decelBorder;
	private JSlider     speedSlider = new JSlider(1, 50);
	private JSlider     decelSlider = new JSlider(1, 50);
	private JLabel      speedLabel  = new JLabel();
	private JLabel      decelLabel  = new JLabel();
	private Border      matteBorder = new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);
	private EmptyBorder emptyBorder = new EmptyBorder(16, 0, 0, 0);

	public DevPanel(String name, double speed, double deceleration) {
		// Default values.
		if (speed < 0.1) this.speed = 1.1;
		else this.speed = speed;
		if (deceleration < 0.1) this.deceleration = 2.0;
		else this.deceleration = deceleration;

		nameBorder = BorderFactory.createTitledBorder(name);
		speedBorder = BorderFactory.createTitledBorder("Speed  [" + this.speed + "]");
		decelBorder = BorderFactory.createTitledBorder("Deceleration  [" + this.deceleration + "]");
	}

	public void prepare() {
		setLayout(new GridLayout(0, 1));

		speedSlider.setValue((int) (this.speed * 10));
		speedSlider.addMouseListener(this);
		speedSlider.setFocusable(false);
		speedSlider.setBorder(speedBorder);
		speedSlider.addChangeListener(e -> {
			JSlider slider = (JSlider) e.getSource();
			speed = slider.getValue() / 10.0;
			speedBorder.setTitle("Speed  [" + speed + "]");
			repaint();
		});
		decelSlider.setValue((int) (this.deceleration * 10));
		decelSlider.addMouseListener(this);
		decelSlider.setFocusable(false);
		decelSlider.setBorder(decelBorder);
		decelSlider.addChangeListener(e -> {
			JSlider slider = (JSlider) e.getSource();
			deceleration = slider.getValue() / 10.0;
			decelBorder.setTitle("Deceleration  [" + deceleration + "]");
			repaint();
		});

		nameBorder.setBorder(matteBorder);
		speedBorder.setBorder(emptyBorder);
		decelBorder.setBorder(emptyBorder);

		speedLabel.setText("" + speed);
		decelLabel.setText("" + deceleration);

		setBorder(nameBorder);

		add(speedSlider);
		add(decelSlider);

		// When the device is connected accept the config.
		setProps(nameBorder.getTitle());
	}

	@Override public void mouseClicked(MouseEvent e) {
	}

	@Override public void mousePressed(MouseEvent e) {
	}

	@Override public void mouseReleased(MouseEvent e) {
		setProps(nameBorder.getTitle());
	}

	@Override public void mouseEntered(MouseEvent e) {
	}

	@Override public void mouseExited(MouseEvent e) {
	}

	/**
	 * Set speed and deceleration for the desired device.
	 *
	 * @param name device's name.
	 */
	private void setProps(String name) {
		DeviceProps props = Assets.DEVICES_LIST.get(name);
		try {
			Commands.setProp(props.getIds(), speed, deceleration);
			props.setSpeed(speed);
			props.setDeceleration(deceleration);
		} catch (IOException | InterruptedException e1) {
			Log.write(e1.getMessage());
			JOptionPane.showMessageDialog(null, "Error. Cannot use this setting:\n" + e1.getMessage());
			e1.printStackTrace();
		}
	}
}
