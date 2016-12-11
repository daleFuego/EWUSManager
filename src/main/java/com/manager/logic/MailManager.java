package com.manager.logic;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.manager.dao.DBData;
import com.manager.gui.PasswordPanel;
import com.manager.gui.panel.mail.EncryptionPanel;
import com.manager.utils.DefineUtils;

public class MailManager {

	private String receiver;
	private String sender;
	private String topic;
	private JDialog waitDialog;
	private PasswordPanel passwordPanel;
	public MailManager(String sender, String receiver, String topic) {

		this.receiver = receiver;
		this.sender = sender;
		this.topic = "EWUŚ : " + topic;

		waitDialog = new JDialog();
		waitDialog.setLocationRelativeTo(null);
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(progressBar, BorderLayout.CENTER);
		panel.add(new JLabel("Trwa wysyłanie wiadomości..."), BorderLayout.PAGE_START);
		waitDialog.add(panel);
		waitDialog.pack();

	}

	public void sendMail(String filePath, boolean encryptionEnabled) {

		passwordPanel = new PasswordPanel();
		passwordPanel.btnConfirm.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				waitDialog.setVisible(true);
				passwordPanel.setVisible(false);

				Thread thread = new Thread(new Runnable() {

					public void run() {
						System.out.println("Sendig with attachemment " + filePath);
						if (sendMail(passwordPanel.passwordField.getText(), filePath, encryptionEnabled)) {
							DefineUtils.MAIL_PASSWORD = passwordPanel.passwordField.getText();
							passwordPanel.dispose();
						}
					}
				});

				thread.start();
			}
		});
	}

	private boolean sendMail(final String password, String filePath, boolean encryptionEnabled) {
		if (encryptionEnabled) {
			EncryptionPanel encryptionPanel = new EncryptionPanel(true, filePath, "");
			JFrame encryptionSettings = new JFrame(DefineUtils.APP_TITLE);
			encryptionSettings.getContentPane().add(encryptionPanel);
			encryptionSettings.setSize(549, 136);
			encryptionSettings.setVisible(true);
			encryptionPanel.getButtonEncryptOptions().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						sendMail(password, filePath + "_encrypted");
						encryptionSettings.dispose();
					} catch (MessagingException ex) {
						waitDialog.dispose();
						JOptionPane.showMessageDialog(null, "Logowanie nie powiodło się", DefineUtils.APP_TITLE,
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			});

		} else {
			try {
				sendMail(password, filePath);
			} catch (MessagingException ex) {
				waitDialog.dispose();
				JOptionPane.showMessageDialog(null, "Logowanie nie powiodło się", DefineUtils.APP_TITLE,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

		}

		return true;

	}

	private void sendMail(String password, String filePath) throws MessagingException {
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(sender));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
		message.setSubject(topic);
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText("");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		messageBodyPart = new MimeBodyPart();

		DataSource source = new FileDataSource(filePath);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(source.getName());
		multipart.addBodyPart(messageBodyPart);
		message.setContent(multipart);

		Transport.send(message);
		waitDialog.dispose();

		JOptionPane.showMessageDialog(null, "Wiadomość została wysłana", DefineUtils.APP_TITLE,
				JOptionPane.INFORMATION_MESSAGE);

		DBData.getInstance().update(DefineUtils.DB_TABLE_PATHS, DefineUtils.DB_pathsendmailreceiver, receiver);
		DBData.getInstance().update(DefineUtils.DB_TABLE_PATHS, DefineUtils.DB_pathsendmailfile, filePath);

	}
}
