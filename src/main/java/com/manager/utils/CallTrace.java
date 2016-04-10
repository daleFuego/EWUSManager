package com.manager.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CallTrace {
	private static CallTrace callTrace;
	private static Logger logger = Logger.getLogger("CallTrace");
	private static String path;

	private CallTrace() {
	}

	public static CallTrace getInstance() {
		if (callTrace == null) {
			callTrace = new CallTrace();
		}
		return callTrace;
	}

	public static void log(String message) {
		try {
			path = "C:\\Users\\Magdalena\\Desktop\\Log.log";
			FileHandler fileHandler = new FileHandler(path, true);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
			logger.addHandler(fileHandler);
			logger.info(message);
		} catch (SecurityException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public String getLogFile() {
		return path;
	}

	public void clearLogs() {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getLogFile()));
			bufferedWriter.write("");
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
