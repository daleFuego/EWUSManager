package com.manager.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DefineUtils {

	public static String mailAdress = "";
	public static String mailPassword = "";
	
	public static String APP_TITLE		 			= "EWUŚ Manager";
	public static String APP_VERSION				= " wersja 2.0";
	public static String FILE_SEPARATOR	 			= "";
	public static String MAIL_PASSWORD	 			= "";
	public static String BRWOSE_DESCRIPTION		 	= "";
	public static String SEND_DESRIPTION 			= "";
	public static String QUEUE_DESCRIPTION 			= "";
	public static String DB_NAME 					= "jdbc:postgresql://localhost:5432/EWUSManager";
	public static String DB_USERNAME 				= "postgres";
	public static String DB_PASSWORD 				= "postgres";
	public static String DB_pathbrowsefiles 		= "pathbrowsefiles";
	public static String DB_pathsendzipsave 		= "pathsendzipsave";
	public static String DB_pathsendmailsender 		= "pathsendmailsender";
	public static String DB_pathsendmailreceiver 	= "pathsendmailreceiver";
	public static String DB_pathsendmailfile 		= "pathsendmailfile";
	public static String DB_pathqueueexistingfile 	= "pathqueueexistingfile";
	public static String DB_pathqueuenewfile 		= "pathqueuenewfile";

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
	}
}
