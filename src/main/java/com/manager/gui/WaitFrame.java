package com.manager.gui;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class WaitFrame extends JFrame {

	private JFrame f;

	public WaitFrame() {
		Icon icon = new ImageIcon("giphy.gif");
		JLabel label = new JLabel(icon);

		f = new JFrame("Animation");
		f.getContentPane().add(label);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);
	}

	public void setVisible(boolean isVisible) {
		f.setVisible(isVisible);
	}

	public static void main(String[] args) {
		new WaitFrame();

	}

}
