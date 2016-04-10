package com.manager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.manager.utils.DefineUtils;

public class DBData {

	private static DBData dbData;
	private static String username;

	public static String pathInitBrowseFiles = "";
	public static String pathInitSendZipFile = "";
	public static String pathInitSendMailSender = "";
	public static String pathInitSendMailReceiver = "";
	public static String pathInitSendMailFile = "";
	public static String pathInitQueueExistingFile = "";
	public static String pathInitQueueNewFile = "";

	private DBData() {
	}

	public static DBData getInstance() {
		if (dbData == null) {
			dbData = new DBData();
		}
		return dbData;
	}

	public ArrayList<String> getInitLoginData() {
		ArrayList<String> pass = new ArrayList<String>();
		Connection connection = null;
		Statement stmt = null;
		String username = "";
		String password = "";

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT USERNAME, PASSWORD FROM USERS WHERE SAVED = 1");
			while (rs.next()) {
				username = rs.getString("username");
				password = rs.getString("password");
			}

			rs.close();
			stmt.close();
			connection.close();

			pass.add(username);
			pass.add(password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pass;
	}

	public static boolean verifyLoginData(String userName, String password) {
		boolean result = false;
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT PASSWORD FROM USERS WHERE USERNAME = '" + userName + "'");
			while (rs.next()) {
				String dbPassword = rs.getString("password");
				if (password.equals(dbPassword)) {
					result = true;
					username = userName;
				}
			}
			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS WHERE USERNAME = '" + username + "'");
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
	}
																																																												
	public void updatePath(String target, String path) {
		Connection connection = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(DefineUtils.DB_NAME, DefineUtils.DB_USERNAME,
					DefineUtils.DB_PASSWORD);
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			
			System.out.println("UPDATE USERS SET " + target + "='" + path + "' WHERE USERNAME = '" + username + "'");
			String query = "UPDATE USERS SET " + target + "='" + path + "' WHERE USERNAME = '" + username + "';";

			stmt.executeUpdate(query);
			connection.commit();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
