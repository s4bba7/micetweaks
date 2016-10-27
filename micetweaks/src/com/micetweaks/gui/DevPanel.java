package com.micetweaks.gui;

import com.micetweaks.Commands;
import com.micetweaks.DeviceProps;
import com.micetweaks.resources.Assets;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class DevPanel extends JPanel implements MouseListener {
	private double speed;
	private double deaccel;
	private JSlider      speedSlider;
	private JSlider      deaccelSlider;
	private TitledBorder nameBorder;
	private TitledBorder speedBorder;
	private TitledBorder deaccelBorder;
	private JLabel      speedLabel   = new JLabel();
	private JLabel      deaccelLabel = new JLabel();
	private Border      matteBorder  = new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);
	private EmptyBorder emptyBorder  = new EmptyBorder(16, 0, 0, 0);

	public DevPanel(String name, double speed, double deaccel) {
		if (speed < 0.1) this.speed = 0.1;
		else this.speed = speed;
		if (deaccel < 0.1) this.deaccel = 0.1;
		else this.deaccel = deaccel;
		System.out.println(this.speed);
		speedSlider = new JSlider(1, 50);
		speedSlider.setValue((int) (this.speed * 10));
		speedSlider.addMouseListener(this);
		deaccelSlider = new JSlider(1, 50);
		deaccelSlider.setValue((int) (this.deaccel * 10));
		deaccelSlider.addMouseListener(this);
		nameBorder = BorderFactory.createTitledBorder(name);
		speedBorder = BorderFactory.createTitledBorder("Speed  [" + correctLabelValue(speedSlider.getValue()) + "]");
		deaccelBorder = BorderFactory
				.createTitledBorder("Deacceleration  [" + correctLabelValue(deaccelSlider.getValue()) + "]");
	}

	public void prepare() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		speedSlider.setFocusable(false);
		speedSlider.setBorder(speedBorder);
		speedSlider.addChangeListener(e -> {
			JSlider slider = (JSlider) e.getSource();
			speed = slider.getValue() / 10.0;
			speedBorder.setTitle("Speed  [" + correctLabelValue(slider.getValue()) + "]");
			repaint();
		});
		deaccelSlider.setFocusable(false);
		deaccelSlider.setBorder(deaccelBorder);
		deaccelSlider.addChangeListener(e -> {
			JSlider slider = (JSlider) e.getSource();
			deaccel = slider.getValue() / 10.0;
			deaccelBorder.setTitle("Deacceleration  [" + correctLabelValue(slider.getValue()) + "]");
			repaint();
		});

		nameBorder.setBorder(matteBorder);
		speedBorder.setBorder(emptyBorder);
		deaccelBorder.setBorder(emptyBorder);

		speedLabel.setText("" + speed);
		deaccelLabel.setText("" + deaccel);

		setBorder(nameBorder);

		c.gridx = 0;
		c.gridy = 0;
		add(speedSlider, c);

		c.gridx = 0;
		c.gridy = 1;
		add(deaccelSlider, c);
	}

	private String correctLabelValue(int value) {
		return (value < 10) ? "0." + value : (value / 10) + "." + (value % 10);
	}

	@Override public void mouseClicked(MouseEvent e) {

	}

	@Override public void mousePressed(MouseEvent e) {

	}

	@Override public void mouseReleased(MouseEvent e) {
		DeviceProps props = Assets.DEVICES_LIST.get(nameBorder.getTitle());
		try {
			Commands.setProp(props.getIds(), speed, deaccel);
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

    /*public static void main(String[] args) throws Exception {
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
        BeautyEyeLNFHelper.launchBeautyEyeLNF();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DevPanel p = new DevPanel("Logitech mega super 9000", 1, 2);
        p.prepare();
        f.add(p);
        f.pack();
        f.setVisible(true);
    }*/
}
