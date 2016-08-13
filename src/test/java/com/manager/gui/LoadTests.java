package com.manager.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
import javax.swing.JOptionPane;

import org.junit.Assert;
import org.junit.Test;

import com.manager.utils.DefineUtils;

public class LoadTests {

	private String mailAdress = DefineUtils.mailAdress;
	private String mailPassword = DefineUtils.mailPassword;

	private String filePath100 = "C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems\\Potwierdzenia100";
	private String filePath500 = "C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems\\Potwierdzenia500";
	private String filePath1000 = "C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems\\Potwierdzenia1000";
	private String zipPath = "C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems"
			+ "\\" + "EWUS_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".zip";

	private boolean test100;
	private boolean test500;
	private boolean test1000;

	@Test
	public void test100DataLoad() {

		test100 = true;
		test500 = false;
		test1000 = false;

		boolean result = false;

		byte[] buffer = new byte[1024];

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipPath);
			ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

			String pathInitBrowseFiles = "";

			if (test100) {
				pathInitBrowseFiles = filePath100;
			} else if (test500) {
				pathInitBrowseFiles = filePath500;
			} else if (test1000) {
				pathInitBrowseFiles = filePath1000;
			}

			File folder = new File(pathInitBrowseFiles);
			for (File file : folder.listFiles()) {
				if (file.isFile() && file.getName().endsWith(".xml")) {
					String fileName = file.getName();

					zipOutputStream.putNextEntry(new ZipEntry(fileName));
					FileInputStream in = new FileInputStream(pathInitBrowseFiles + "\\" + fileName);

					int len;

					while ((len = in.read(buffer)) > 0) {
						zipOutputStream.write(buffer, 0, len);
					}

					in.close();
				}
			}

			zipOutputStream.closeEntry();
			zipOutputStream.close();

			String filePath = "C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems\\testFile.zip";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailAdress, mailPassword);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailAdress));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailAdress));
			message.setSubject("topic");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("message text");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filePath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(source.getName());
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			JOptionPane.showMessageDialog(null, "Wiadomość została wysłana", "EWUŚ MANAGER",
					JOptionPane.INFORMATION_MESSAGE);
			result = true;
		} catch (MessagingException | IOException ex) {
			JOptionPane.showMessageDialog(null, "Logowanie nie powiodło się", "EWUŚ MANAGER",
					JOptionPane.ERROR_MESSAGE);
		}

		Assert.assertTrue(result);
	}

	@Test
	public void test500DataLoad() {

		test100 = false;
		test500 = true;
		test1000 = false;
		
		boolean result = false;

		byte[] buffer = new byte[1024];

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipPath);
			ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

			String pathInitBrowseFiles = "";

			if (test100) {
				pathInitBrowseFiles = filePath100;
			} else if (test500) {
				pathInitBrowseFiles = filePath500;
			} else if (test1000) {
				pathInitBrowseFiles = filePath1000;
			}

			File folder = new File(pathInitBrowseFiles);
			for (File file : folder.listFiles()) {
				if (file.isFile() && file.getName().endsWith(".xml")) {
					String fileName = file.getName();

					zipOutputStream.putNextEntry(new ZipEntry(fileName));
					FileInputStream in = new FileInputStream(pathInitBrowseFiles + "\\" + fileName);

					int len;

					while ((len = in.read(buffer)) > 0) {
						zipOutputStream.write(buffer, 0, len);
					}

					in.close();
				}
			}

			zipOutputStream.closeEntry();
			zipOutputStream.close();

			String filePath = "C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems\\testFile.zip";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailAdress, mailPassword);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailAdress));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailAdress));
			message.setSubject("topic");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("message text");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filePath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(source.getName());
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			JOptionPane.showMessageDialog(null, "Wiadomość została wysłana", "EWUŚ MANAGER",
					JOptionPane.INFORMATION_MESSAGE);
			result = true;
		} catch (MessagingException | IOException ex) {
			JOptionPane.showMessageDialog(null, "Logowanie nie powiodło się", "EWUŚ MANAGER",
					JOptionPane.ERROR_MESSAGE);
		}

		Assert.assertTrue(result);
	}

	@Test
	public void test1000DataLoad() {

		test100 = false;
		test500 = false;
		test1000 = true;
		
		boolean result = false;

		byte[] buffer = new byte[1024];

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipPath);
			ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

			String pathInitBrowseFiles = "";

			if (test100) {
				pathInitBrowseFiles = filePath100;
			} else if (test500) {
				pathInitBrowseFiles = filePath500;
			} else if (test1000) {
				pathInitBrowseFiles = filePath1000;
			}

			File folder = new File(pathInitBrowseFiles);
			for (File file : folder.listFiles()) {
				if (file.isFile() && file.getName().endsWith(".xml")) {
					String fileName = file.getName();

					zipOutputStream.putNextEntry(new ZipEntry(fileName));
					FileInputStream in = new FileInputStream(pathInitBrowseFiles + "\\" + fileName);

					int len;

					while ((len = in.read(buffer)) > 0) {
						zipOutputStream.write(buffer, 0, len);
					}

					in.close();
				}
			}

			zipOutputStream.closeEntry();
			zipOutputStream.close();

			String filePath = "C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems\\testFile.zip";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailAdress, mailPassword);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailAdress));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailAdress));
			message.setSubject("topic");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("message text");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filePath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(source.getName());
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			JOptionPane.showMessageDialog(null, "Wiadomość została wysłana", "EWUŚ MANAGER",
					JOptionPane.INFORMATION_MESSAGE);
			result = true;
		} catch (MessagingException | IOException ex) {
			JOptionPane.showMessageDialog(null, "Logowanie nie powiodło się", "EWUŚ MANAGER",
					JOptionPane.ERROR_MESSAGE);
		}

		Assert.assertTrue(result);
	}
	
}
