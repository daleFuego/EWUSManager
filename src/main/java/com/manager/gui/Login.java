package com.manager.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.manager.dao.DBData;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private JPanel contentPane;
	private JPanel panelLogin;

	private JTextField textFieldUserName;
	private JPasswordField textFieldPassword;

	private JLabel lblPassword;
	private JLabel lblLogin;

	public static JButton btnLogin;
	private static Login frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {			
					frame = new Login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		initialize();

	}

	private void initialize() {

		// LABEL
		lblPassword = new JLabel("Hasło");
		lblPassword.setBounds(10, 50, 46, 14);
		lblLogin = new JLabel("Login");
		lblLogin.setBounds(10, 22, 46, 14);

		// TEXTFIELD
		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(57, 19, 123, 20);
		textFieldUserName.setColumns(10);
		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(57, 47, 123, 20);
		textFieldPassword.setColumns(10);
		try {
			textFieldUserName.setText(DBData.getInstance().getInitLoginData().get(0));
			textFieldPassword.setText(DBData.getInstance().getInitLoginData().get(1));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TEXTAREA

		// BUTTON
		btnLogin = new JButton("Zaloguj się");
		btnLogin.setBounds(61, 72, 123, 23);

		// TABLE

		// PANEL
		panelLogin = new JPanel();
		panelLogin.setLayout(null);
		panelLogin.setBorder(
				new TitledBorder(null, "Logowanie do programu", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLogin.setBounds(4, 8, 190, 106);
		panelLogin.add(btnLogin);
		panelLogin.add(lblLogin);
		panelLogin.add(lblPassword);
		panelLogin.add(textFieldUserName);
		panelLogin.add(textFieldPassword);

		// PANE
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.add(panelLogin);

		// ACTION
		btnLogin.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});

		btnLogin.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					if (DBData.verifyLoginData(textFieldUserName.getText(), textFieldPassword.getText())) {
						setVisible(false);
						new Manager();

					} else {
						JOptionPane.showMessageDialog(frame, "Verification failed, check username or password", "Error",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					System.exit(0);
				}
			}
		});

		// FRAME
		// One - user change
		setVisible(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 214, 162);
		setContentPane(contentPane);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		btnLogin.requestFocus();
		
		// One - user change
		btnLogin.doClick();
	}
}
