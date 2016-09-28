package com.micetweaks.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;

public class DevPanel extends JPanel {
	private String	name;
	private double	speed;
	private double	deaccel;
	private JSlider	speedSlider;
	private JSlider	deaccelSlider;
	private Border	nameBorder;
	private Border	speedBorder;
	private Border	deaccelBorder;
	private JLabel speedLabel = new JLabel();
	private JLabel deaccelLabel = new JLabel();
	private JButton save = new JButton("Save");
	private JButton load = new JButton("Load");
	private JButton hide = new JButton("Hide");

	public DevPanel(String name, double speed, double deaccel) {
		this.name = name;
		this.speed = speed;
		this.deaccel = deaccel;
		// TODO pamiętaj by docelowo podzielić wartości sliderów!
		speedSlider = new JSlider(0, 50, (int) speed * 10);
		deaccelSlider = new JSlider(0, 50, (int) deaccel * 10);
		nameBorder = BorderFactory.createTitledBorder(name);
		speedBorder = BorderFactory.createTitledBorder("Speed");
		deaccelBorder = BorderFactory.createTitledBorder("Deacceleration");
		speedLabel.setText("" + speed);
		deaccelLabel.setText("" + deaccel);
	}

	public void prepare() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		setBorder(nameBorder);
		speedSlider.setBorder(speedBorder);
		deaccelSlider.setBorder(deaccelBorder);
		
		c.gridx = 0;
		c.gridy = 0;
		add(save, c);
		c.gridx = 1;
		add(load, c);
		c.gridx = 2;
		add(hide, c);
		
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		add(speedSlider, c);
		c.gridx = 1;
		add(speedLabel, c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(deaccelSlider, c);
		c.gridx = 1;
		add(deaccelLabel, c);
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DevPanel p = new DevPanel("test", 1, 2);
		p.prepare();
		f.add(p);
		f.pack();
		f.setVisible(true);
	}

}
