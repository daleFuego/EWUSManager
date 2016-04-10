package com.manager.panel;

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
import javax.swing.JFrame;
import javax.swing.JPopupMenu;

import com.manager.dao.DBData;
import com.manager.utils.DefineUtils;

public class MailManager {

	private String receiver;
	private String filePath;

	public MailManager(JFrame frame, String sender, String receiver, String filePath) {
		this.receiver = receiver;
		this.filePath = filePath;
	}

	public void sendMail() {

		String to = receiver;

		String from = "mailmail@mailmail.cba.pl";

		final String username = from;
		final String password = "password";

		String host = "mail.cba.pl";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");



		try {
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("EWUS Manager");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filePath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filePath);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			JPopupMenu jPopupMenu = new JPopupMenu("Sent message successfully");
			jPopupMenu.setVisible(true);
			DBData.getInstance().updatePath(DefineUtils.DB_pathsendmailreceiver, receiver);
			DBData.getInstance().updatePath(DefineUtils.DB_pathsendmailfile, filePath);
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
