package com.micetweaks.gui;

import com.micetweaks.Commands;
import com.micetweaks.DeviceProps;
import com.micetweaks.resources.Assets;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class DevPanel extends JPanel implements MouseListener {
	private double       speed;
	private double       deceleration;
	private JSlider      speedSlider;
	private JSlider      decelSlider;
	private TitledBorder nameBorder;
	private TitledBorder speedBorder;
	private TitledBorder decelBorder;
	private JLabel      speedLabel  = new JLabel();
	private JLabel      decelLabel  = new JLabel();
	private Border      matteBorder = new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);
	private EmptyBorder emptyBorder = new EmptyBorder(16, 0, 0, 0);

	public DevPanel(String name, double speed, double deceleration) {
		if (speed < 0.1) this.speed = 0.1;
		else this.speed = speed;
		if (deceleration < 0.1) this.deceleration = 0.1;
		else this.deceleration = deceleration;

		speedSlider = new JSlider(1, 50);
		decelSlider = new JSlider(1, 50);
		nameBorder = BorderFactory.createTitledBorder(name);
		speedBorder = BorderFactory.createTitledBorder("Speed  [" + speed + "]");
		decelBorder = BorderFactory.createTitledBorder("Deceleration  [" + deceleration + "]");
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
	}

	@Override public void mouseClicked(MouseEvent e) {

	}

	@Override public void mousePressed(MouseEvent e) {

	}

	@Override public void mouseReleased(MouseEvent e) {
		DeviceProps props = Assets.DEVICES_LIST.get(nameBorder.getTitle());
		try {
			Commands.setProp(props.getIds(), speed, deceleration);
			props.setSpeed(speed);
			props.setDeceleration(deceleration);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	@Override public void mouseEntered(MouseEvent e) {

	}

	@Override public void mouseExited(MouseEvent e) {

	}
}
