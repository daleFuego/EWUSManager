package com.manager.utils;

import java.util.Properties;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class EmailSession {

	private static EmailSession emailSession;
	private Store store;

	private EmailSession() {
	}

	public static EmailSession getInstance() {
		if (emailSession == null) {
			emailSession = new EmailSession();
			emailSession.initialize();
		}

		return emailSession;
	}

	private void initialize() {
		String protocol = "imap";
        String host = "imap.gmail.com";
        String port = "993";
        
        Properties properties = new Properties();
        
        // server setting
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
 
        // SSL setting
        properties.setProperty(
                String.format("mail.%s.socketFactory.class", protocol),
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(
                String.format("mail.%s.socketFactory.fallback", protocol),
                "false");
        properties.setProperty(
                String.format("mail.%s.socketFactory.port", protocol),
                String.valueOf(port));

		Session emailSession = Session.getDefaultInstance(properties);

		try {
			store = emailSession.getStore(protocol);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		
		
	}

	public Store getStore() {
		return store;
	}

}
