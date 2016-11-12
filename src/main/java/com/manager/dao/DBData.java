package com.manager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.DefaultListModel;

import com.manager.utils.DefineUtils;

public class DBData {

	private static DBData dbData;
	public static String username;

	public static String pathInitBrowseFiles = "";
	public static String pathInitSendMailSender = "";
	public static String pathInitSendMailReceiver = "";
	public static String pathInitSendMailFile = "";
	public static String pathInitQueueExistingFile = "";
	public static String pathInitArchive = "";
	public static String pathInitPublicKey = "";
	public static String pathInitPrivateKey = "";

	private DBData() {
	}

	public static DBData getInstance() {
		if (dbData == null) {
			dbData = new DBData();
		}
		return dbData;
	}

	public void getInitData() {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM \"" + DefineUtils.DB_TABLE_PATHS + "\"");
			while (rs.next()) {
				pathInitBrowseFiles = rs.getString(DefineUtils.DB_pathbrowsefiles);
				pathInitSendMailSender = rs.getString(DefineUtils.DB_pathsendmailsender);
				pathInitSendMailReceiver = rs.getString(DefineUtils.DB_pathsendmailreceiver);
				pathInitSendMailFile = rs.getString(DefineUtils.DB_pathsendmailfile);
				pathInitQueueExistingFile = rs.getString(DefineUtils.DB_pathqueueexistingfile);
				pathInitArchive = rs.getString(DefineUtils.DB_pathArchive);
				pathInitPublicKey = rs.getString(DefineUtils.DB_pathpublickey);
				pathInitPrivateKey = rs.getString(DefineUtils.DB_pathprivatekey);
			}
			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(String table, String target, String value) {
		String query = "UPDATE \"" + table + "\" SET " + target + "='" + value + "';";
		executeQuery(query);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DefaultListModel getReceivers() {
		DefaultListModel listModel = new DefaultListModel();

		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			stmt = connection.createStatement();

			String query = "SELECT * FROM \"" + DefineUtils.DB_TABLE_CONTACTS + "\"";

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				listModel.addElement(rs.getString("mailAddress"));
			}
			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listModel;
	}

	public void checkEncryptionStatus() {

		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			stmt = connection.createStatement();

			String query = "SELECT * FROM \"" + DefineUtils.DB_TABLE_ENCRYPTION + "\"";

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				DefineUtils.FILE_ENCRYPTION_STATUS = rs.getBoolean(1);
				DefineUtils.QUEUE_ENCRYPTION_STATUS = rs.getBoolean(2);
			}
			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeFromContacts(String mailAddress) {
		String query = "DELETE FROM \"" + DefineUtils.DB_TABLE_CONTACTS + "\" WHERE " + DefineUtils.DB_mailAddress
				+ " = '" + mailAddress + "';";
		executeQuery(query);
	}

	private void executeQuery(String query) {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.execute(query);
			connection.commit();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(String table, String target, boolean value) {
		String query = "UPDATE \"" + table + "\" SET " + target + "='" + value + "';";
		executeQuery(query);
	}

	public void insertOrUpdate(String table, String target, String value) {
		if (!checkIfExists(table, target, value)) {
			String query = "INSERT INTO \"" + table + "\" (" + target + ") VALUES ('" + value + "');";
			executeQuery(query);
		}
	}

	private boolean checkIfExists(String table, String target, String value) {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			stmt = connection.createStatement();

			String query = "SELECT * FROM \"" + table + "\"";

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (rs.getString(target).equals(value)) {
					return true;
				}
			}
			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
