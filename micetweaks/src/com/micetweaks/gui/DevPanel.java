package com.micetweaks.gui;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class DevPanel extends JPanel {
    private double speed;
    private double deaccel;
    private JSlider speedSlider;
    private JSlider deaccelSlider;
    private TitledBorder nameBorder;
    private TitledBorder speedBorder;
    private TitledBorder deaccelBorder;
    private JLabel speedLabel = new JLabel();
    private JLabel deaccelLabel = new JLabel();
    private JButton save = new JButton("S");
    private JButton load = new JButton("L");
    private JButton hide = new JButton("X");
    private Border matteBorder = new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);
    private EmptyBorder emptyBorder = new EmptyBorder(16, 0, 0, 0);
    private Insets buttonMargin = new Insets(2, 4, 2, 4);

    public DevPanel(String name, double speed, double deaccel) {
        this.speed = speed;
        this.deaccel = deaccel;
        // TODO pamiętaj by docelowo podzielić wartości sliderów!
        speedSlider = new JSlider(0, 50, (int) speed * 10);
        deaccelSlider = new JSlider(0, 50, (int) deaccel * 10);
        nameBorder = BorderFactory.createTitledBorder(name);
        speedBorder = BorderFactory.createTitledBorder("Speed  [" + correctValue(speedSlider.getValue()) + "]");
        deaccelBorder = BorderFactory.createTitledBorder("Deacceleration  [" + correctValue(speedSlider.getValue()) + "]");
    }

    public void prepare() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        speedSlider.setFocusable(false);
        speedSlider.setBorder(speedBorder);
        speedSlider.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            speedBorder.setTitle("Speed  [" + correctValue(slider.getValue()) + "]");
            repaint();
        });
        deaccelSlider.setFocusable(false);
        deaccelSlider.setBorder(deaccelBorder);
        deaccelSlider.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            deaccelBorder.setTitle("Deacceleration  [" + correctValue(slider.getValue()) + "]");
            repaint();
        });
        save.setFocusable(false);
        save.setMargin(buttonMargin);
        load.setFocusable(false);
        load.setMargin(buttonMargin);
        hide.setFocusable(false);
        hide.setMargin(buttonMargin);
        hide.setBackground(Color.RED);
        hide.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));

        nameBorder.setBorder(matteBorder);
        speedBorder.setBorder(emptyBorder);
        deaccelBorder.setBorder(emptyBorder);

        speedLabel.setText("" + speed);
        deaccelLabel.setText("" + deaccel);

        setBorder(nameBorder);

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(3, 1));
        controlsPanel.add(save);
        controlsPanel.add(load);
        controlsPanel.add(hide);
        c.gridx = 0;
        c.gridy = 0;
        add(speedSlider, c);

        c.gridx = 0;
        c.gridy = 1;
        add(deaccelSlider, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 2;
        add(controlsPanel, c);
    }

    private String correctValue(int value) {
        return (value < 10) ? "0." + value : (value / 10) + "." + (value % 10);
    }

    public static void main(String[] args) throws Exception {
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
        BeautyEyeLNFHelper.launchBeautyEyeLNF();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DevPanel p = new DevPanel("Logitech mega super 9000", 1, 2);
        p.prepare();
        f.add(p);
        f.pack();
        f.setVisible(true);
    }
}
