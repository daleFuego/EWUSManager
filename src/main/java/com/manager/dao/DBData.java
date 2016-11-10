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
				pathInitBrowseFiles = rs.getString("pathbrowsefiles");
				pathInitSendMailSender = rs.getString("pathsendmailsender");
				pathInitSendMailReceiver = rs.getString("pathsendmailreceiver");
				pathInitSendMailFile = rs.getString("pathsendmailfile");
				pathInitQueueExistingFile = rs.getString("pathqueueexistingfile");
				pathInitArchive = rs.getString("pathArchive");
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
		System.out.println(query);
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
			System.out.println(query);

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

	public void removeFromContacts(String mailAddress) {
		String query = "DELETE FROM \"" + DefineUtils.DB_TABLE_CONTACTS + "\" WHERE \"mailAddress\" = '" + mailAddress
				+ "';";
		System.out.println(query);
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

}
