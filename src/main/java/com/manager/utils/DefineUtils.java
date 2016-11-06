package com.manager.utils;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DefineUtils {
	
	public static String mailAdress = "";
	public static String mailPassword = "";
	
	public static String APP_TITLE		 			= "EWUŚ Manager";
	public static String APP_VERSION				= " wersja ";
	public static String FILE_SEPARATOR	 			= "";
	public static String MAIL_PASSWORD	 			= "";
	public static String CERTIFICATES_DESCRIPTION	= "";
	public static String SEND_DESRIPTION 			= "";
	public static String QUEUE_DESCRIPTION 			= "";
	public static String DB_NAME 					= "jdbc:postgresql://localhost:5432/EWUSManager";
	public static String DB_USERNAME 				= "postgres";
	public static String DB_PASSWORD 				= "postgres";
	public static String DB_TABLE_PATHS 			= "PATHS";
	public static String DB_TABLE_CONTACTS 			= "CONTACTS";
	public static String DB_pathbrowsefiles 		= "pathbrowsefiles";
	public static String DB_pathsendmailsender 		= "pathsendmailsender";
	public static String DB_pathsendmailreceiver 	= "pathsendmailreceiver";
	public static String DB_pathsendmailfile 		= "pathsendmailfile";
	public static String DB_pathqueueexistingfile 	= "pathqueueexistingfile";
	public static String DB_pathArchive 			= "\"pathArchive\"";
	public static String DB_pathDeletedItems 		= "\"pathDeletedItems\"";
	public static String DB_mailAddress 			= "mailAddress";
	
	public static Font FONT 						= new Font("Arial", Font.PLAIN, 11);

	public static void initDataLoad() {
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			FILE_SEPARATOR = "\\";
		} else {
			FILE_SEPARATOR = "/";
		}

		String content = "";
		try {
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(new FileReader("Init.txt"));
			while ((content = bufferedReader.readLine()) != null) {
				if (content.contains("#BRWOSE_DESCRIPTION")) {
					CERTIFICATES_DESCRIPTION = content.split("=")[1];
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

		Properties properties = new Properties();
		try {
			properties.load(DefineUtils.class.getResourceAsStream("/default.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		APP_VERSION += properties.getProperty("app.version");
	}
}
