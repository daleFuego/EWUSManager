package com.manager.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class PasswordPanel extends JFrame {

	private JPanel contentPane;
	public JPasswordField passwordField;
	public JButton btnConfirm;

	public PasswordPanel() {
		setResizable(false);
		setTitle(DefineUtils.APP_TITLE);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 473, 63);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblEnterPassword = new JLabel("Podaj hasło do swojego konta mailowego:");
		lblEnterPassword.setFont(DefineUtils.FONT);
		lblEnterPassword.setBounds(6, 5, 239, 14);
		contentPane.add(lblEnterPassword);

		passwordField = new JPasswordField(DefineUtils.MAIL_PASSWORD);
		passwordField.setBounds(253, 2, 103, 20);
		passwordField.setFont(DefineUtils.FONT);
		contentPane.add(passwordField);

		btnConfirm = new JButton("Potwierdź");
		btnConfirm.setFont(DefineUtils.FONT);
		btnConfirm.setBounds(362, 1, 89, 23);

		passwordField.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnConfirm.doClick();
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}

			public void keyTyped(KeyEvent e) {				
			}

			public void keyReleased(KeyEvent e) {
			}
		});
		contentPane.add(btnConfirm);
	}
}
