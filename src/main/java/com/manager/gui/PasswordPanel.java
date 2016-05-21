package com.manager.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PasswordPanel extends JFrame {

	private JPanel contentPane;
	public JPasswordField passwordField;
	public JButton btnConfirm;

	public PasswordPanel() {
		setTitle("EWUŚ MANAGER");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 473, 63);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterPassword = new JLabel("Podaj hasło do swojego konta mailowego:");
		lblEnterPassword.setBounds(6, 5, 208, 14);
		contentPane.add(lblEnterPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(220, 2, 136, 20);
		contentPane.add(passwordField);
		
		btnConfirm = new JButton("Potwierdź");
		btnConfirm.setBounds(362, 1, 89, 23);

		passwordField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnConfirm.doClick();
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}
		});
		contentPane.add(btnConfirm);
	}
}
