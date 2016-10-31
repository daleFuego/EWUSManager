package com.manager.logic;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import com.manager.dao.DBData;
import com.manager.gui.PasswordPanel;
import com.manager.utils.DefineUtils;

public class MailManager {

	private String receiver;
	private String sender;
	private String filePath;
	private String topic;
	private JDialog waitDialog;
	private PasswordPanel passwordPanel;

	public MailManager(String sender, String receiver, String filePath, String topic) {

		this.receiver = receiver;
		this.sender = sender;
		this.filePath = filePath;
		this.topic = topic;

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

	public void sendMail() {

		passwordPanel = new PasswordPanel();
		passwordPanel.btnConfirm.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				waitDialog.setVisible(true);
				passwordPanel.setVisible(false);

				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						if (sendMail(passwordPanel.passwordField.getText())) {
							DefineUtils.MAIL_PASSWORD = passwordPanel.passwordField.getText();
							passwordPanel.dispose();
						}
					}
				});

				thread.start();

				try {
					SwingUtilities.invokeAndWait(thread);
				} catch (InvocationTargetException e1) {
				} catch (InterruptedException e1) {
				}
			}
		});
	}

	private boolean sendMail(String password) {
		boolean result = true;

		try {
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
			JOptionPane.showMessageDialog(null, "Wiadomość została wysłana", "EWUŚ MANAGER",
					JOptionPane.INFORMATION_MESSAGE);
			DBData.getInstance().updatePath(DefineUtils.DB_pathsendmailreceiver, receiver);
			DBData.getInstance().updatePath(DefineUtils.DB_pathsendmailfile, filePath);
		} catch (MessagingException e) {
			waitDialog.dispose();
			JOptionPane.showMessageDialog(null, "Logowanie nie powiodło się", "EWUŚ MANAGER",
					JOptionPane.ERROR_MESSAGE);
			result = false;
		}

		return result;
	}

}
