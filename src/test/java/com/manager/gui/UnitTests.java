package com.manager.gui;

import static org.junit.Assert.assertNotNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.SwingWorker;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.manager.dao.DBData;
import com.manager.data.PeselValidator;
import com.manager.utils.DefineUtils;

public class UnitTests {

	private Task task;
	private ProgressDialog progessDialog;

	private String username = "admin";
	private String password = "admin";
	private String mailAdress = DefineUtils.mailAdress;
	private String mailPassword = DefineUtils.mailPassword;
	
	@Before
	public void initialize() {

		task = new Task();

		DBData.verifyLoginData(username, password);
		DBData.getInstance().getInitData();
	}

	@Test
	public void testLoadFiles() {

		// INIT DATA LOAD
		String content = "";
		String BRWOSE_DESCRIPTION = null;
		String SEND_DESRIPTION = null;
		String QUEUE_DESCRIPTION = null;

		try {
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(new FileReader("Init.txt"));
			while ((content = bufferedReader.readLine()) != null) {
				if (content.contains("#BRWOSE_DESCRIPTION")) {
					BRWOSE_DESCRIPTION = content.split("=")[1];
				}
				if (content.contains("#SEND_DESRIPTION")) {
					SEND_DESRIPTION = content.split("=")[1];
				}
				if (content.contains("#QUEUE_DESCRIPTION")) {
					QUEUE_DESCRIPTION = content.split("=")[1];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull(BRWOSE_DESCRIPTION);
		assertNotNull(SEND_DESRIPTION);
		assertNotNull(QUEUE_DESCRIPTION);

		// XML FILES
		Connection connection = null;
		Statement stmt = null;
		String pathInitBrowseFiles = null;

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT pathbrowsefiles FROM \"USERS\" WHERE \"USERNAME\" = '" + username + "'");

			while (rs.next()) {
				pathInitBrowseFiles = rs.getString("pathbrowsefiles");
			}

			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(pathInitBrowseFiles);

		try {
			File folder = new File(pathInitBrowseFiles);
			for (File file : folder.listFiles()) {
				if (file.isFile() && file.getName().endsWith(".xml")) {
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(file);
					doc.getDocumentElement().normalize();

					Element docElement = doc.getDocumentElement();

					assertNotNull(docElement.getElementsByTagName("ns2:data_waznosci_potwierdzenia"));
					assertNotNull(docElement.getElementsByTagName("ns2:imie"));
					assertNotNull(docElement.getElementsByTagName("ns2:nazwisko"));
					assertNotNull(docElement.getElementsByTagName("ns2:numer_pesel"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void testDeleteFile() {
		int i = 0;
		boolean result = false;
		Connection connection = null;
		Statement stmt = null;
		String pathInitBrowseFiles = null;
		ArrayList<String> filePaths = new ArrayList<>();

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT pathbrowsefiles FROM \"USERS\" WHERE \"USERNAME\" = '" + username + "'");

			while (rs.next()) {
				pathInitBrowseFiles = rs.getString("pathbrowsefiles");
			}

			rs.close();
			stmt.close();
			connection.close();

			File folder = new File(pathInitBrowseFiles);
			for (File file : folder.listFiles()) {
				if (i % 10 == 0) {
					filePaths.add(file.getAbsolutePath());
				}
				i++;
			}

			for (String filePath : filePaths) {
				File file = new File(filePath);
				if (!file.isDirectory()) {
					File binPath = new File(System.getProperty("user.home"),
							"Desktop\\DeletedItems\\" + file.getName());
					if (file.renameTo(binPath)) {
						System.out.println(binPath);
						result = true;
					} else {
						File dir = new File(System.getProperty("user.home"), "Desktop\\DeletedItems");
						dir.mkdir();
						file.renameTo(binPath);
						result = true;
					}
				}
			}

			Assert.assertTrue(result);
			Assert.assertTrue(!result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPESELValidation() {
		Assert.assertTrue(new PeselValidator("92011408360").isValid());
	}

	// @Test
	public void testSendMail() {

		boolean result = false;
		String filePath = "C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems\\testFile.zip";

		try {
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
		} catch (MessagingException ex) {
			JOptionPane.showMessageDialog(null, "Logowanie nie powiodło się", "EWUŚ MANAGER",
					JOptionPane.ERROR_MESSAGE);
		}

		Assert.assertTrue(result);
	}

	@Test
	public void testZipFiles() {

		task.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("progress" == evt.getPropertyName()) {
					progessDialog.progressBarProcessingTask.setValue((Integer) evt.getNewValue());
				}
			}
		});
		progessDialog = new ProgressDialog("Buduję archiwum...");
		progessDialog.setVisible(true);

		task.execute();
	}

	class Task extends SwingWorker<Void, Void> {

		@Override
		public Void doInBackground() {
			task.setProgress(0);
			byte[] buffer = new byte[1024];

			Connection connection = null;
			Statement stmt = null;
			String pathInitBrowseFiles = null;

			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
						DefineUtils.DB_PASSWORD);
				connection.setAutoCommit(false);
				stmt = connection.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT pathbrowsefiles FROM \"USERS\" WHERE \"USERNAME\" = '" + username + "'");

				while (rs.next()) {
					pathInitBrowseFiles = rs.getString("pathbrowsefiles");
				}

				rs.close();
				stmt.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			assertNotNull(pathInitBrowseFiles);

			try {
				int i = 0;
				String zipPath = "C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems"
						+ DefineUtils.FILE_SEPARATOR + "EWUS_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
						+ ".zip";

				FileOutputStream fileOutputStream = new FileOutputStream(zipPath);
				ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

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

						long progress = ((100 * i) / folder.listFiles().length);
						task.setProgress((int) progress);
						in.close();
						i++;
					}
				}
				
				zipOutputStream.closeEntry();
				zipOutputStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		public void done() {
			progessDialog.dispose();
			System.out.println("Done");
		}
	}

	@Test
	public void testDBUpdate() {
		boolean result = false;

		try {
			DBData.username = username;

			DBData.getInstance().updatePath(DefineUtils.DB_pathbrowsefiles,
					"C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems\\Potwierdzenia");
			DBData.getInstance().updatePath(DefineUtils.DB_pathqueueexistingfile,
					"C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems\\MAIS_Kolejka.txt");
			DBData.getInstance().updatePath(DefineUtils.DB_pathqueuenewfile,
					"C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems\\MAIS_Kolejka.txt");
			DBData.getInstance().updatePath(DefineUtils.DB_pathsendmailfile,
					"C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems\\EWUS_2016-06-05.zip");
			DBData.getInstance().updatePath(DefineUtils.DB_pathsendmailreceiver, mailAdress);
			DBData.getInstance().updatePath(DefineUtils.DB_pathsendmailsender, mailAdress);
			DBData.getInstance().updatePath(DefineUtils.DB_pathsendzipsave,
					"C:\\Users\\Magdalena\\Documents\\Szkoła\\Computer Science and Information Technology\\Semestr 1\\Modelling and Analysis of Information Systems\\EWUS_2016-06-05.zip");

			result = true;
		} catch (Exception e) {
		}

		Assert.assertTrue(result);
	}

	@Test
	public void testDBConnection() {

		Connection connection = null;
		Statement stmt = null;

		String pathInitBrowseFiles = null;
		String pathInitSendZipFile = null;
		String pathInitSendMailSender = null;
		String pathInitSendMailReceiver = null;
		String pathInitSendMailFile = null;
		String pathInitQueueExistingFile = null;
		String pathInitQueueNewFile = null;

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM \"USERS\" WHERE \"USERNAME\" = '" + username + "'");
			while (rs.next()) {
				pathInitBrowseFiles = rs.getString("pathbrowsefiles");
				pathInitSendZipFile = rs.getString("pathsendzipsave");
				pathInitSendMailSender = rs.getString("pathsendmailsender");
				pathInitSendMailReceiver = rs.getString("pathsendmailreceiver");
				pathInitSendMailFile = rs.getString("pathsendmailfile");
				pathInitQueueExistingFile = rs.getString("pathqueueexistingfile");
				pathInitQueueNewFile = rs.getString("pathqueuenewfile");
			}
			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(pathInitBrowseFiles);
		assertNotNull(pathInitSendZipFile);
		assertNotNull(pathInitSendMailSender);
		assertNotNull(pathInitSendMailReceiver);
		assertNotNull(pathInitSendMailFile);
		assertNotNull(pathInitQueueExistingFile);
		assertNotNull(pathInitQueueNewFile);
	}

}
