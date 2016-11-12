package com.manager.gui.panel.mail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.manager.utils.DefineUtils;
import com.manager.utils.EmailSession;

@SuppressWarnings("serial")
public class LoginPanel extends JFrame {
	private JTextField textFieldLogin;
	private JPasswordField passwordField;
	private static LoginPanel loginPanel;
	private JButton buttonLogin;

	public LoginPanel() {
		getContentPane().setLayout(null);
		setVisible(true);
		setTitle(DefineUtils.APP_TITLE);
		setSize(495, 110);
		setResizable(false);

		JPanel panel = new JPanel();
		panel.setBounds(3, 4, 482, 73);
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Logowanie do konta pocztowego GMAIL", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		getContentPane().add(panel);

		JLabel labelLogin = new JLabel("Login");
		labelLogin.setFont(DefineUtils.FONT);
		labelLogin.setBounds(10, 18, 54, 14);
		panel.add(labelLogin);

		JLabel labelPassword = new JLabel("Hasło");
		labelPassword.setBounds(10, 46, 46, 14);
		labelPassword.setFont(DefineUtils.FONT);
		panel.add(labelPassword);

		textFieldLogin = new JTextField();
		textFieldLogin.setColumns(10);
		textFieldLogin.setFont(DefineUtils.FONT);
		textFieldLogin.setBounds(66, 15, 288, 20);
		panel.add(textFieldLogin);

		passwordField = new JPasswordField();
		passwordField.setBounds(66, 43, 288, 20);
		passwordField.setFont(DefineUtils.FONT);
		panel.add(passwordField);

		buttonLogin = new JButton("Zaloguj");
		buttonLogin.setFont(DefineUtils.FONT);
		buttonLogin.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				try {
					EmailSession.getInstance().getStore().connect(textFieldLogin.getText(), passwordField.getText());
					loginPanel.dispose();

					new MailClientFrame();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Logowanie nie powiodło się", DefineUtils.APP_TITLE,
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		});
		buttonLogin.setBounds(364, 43, 109, 20);
		panel.add(buttonLogin);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
		}
	}

	public static void main(String[] args) {
		loginPanel = new LoginPanel();
	}

}
