package com.manager.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.activation.DataHandler;
import javax.crypto.Cipher;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.manager.dao.DBData;

public class EncryptionUtils {

	private static EncryptionUtils encryptionUtils;

	private EncryptionUtils() {
	}

	public static EncryptionUtils getInstance() {
		if (encryptionUtils == null) {
			encryptionUtils = new EncryptionUtils();
		}

		return encryptionUtils;
	}

	public void generateKey(String keyFilePath, JTextField textFieldPrivateKey, JTextField textFieldPublicKey) {
		File[] keys = generateKey(keyFilePath);
		DBData.getInstance().update(DefineUtils.DB_TABLE_PATHS, DefineUtils.DB_pathprivatekey,
				keys[1].getAbsolutePath());
		DBData.getInstance().update(DefineUtils.DB_TABLE_PATHS, DefineUtils.DB_pathpublickey,
				keys[0].getAbsolutePath());

		textFieldPrivateKey.setText(keys[1].getAbsolutePath());
		textFieldPublicKey.setText(keys[0].getAbsolutePath());
	}

	public File[] generateKey(String keyFilePath) {
		KeyPairGenerator keyGen;
		File[] keys = new File[2];
		try {
			keyGen = KeyPairGenerator.getInstance(DefineUtils.ALGORITHM);

			keyGen.initialize(1024);
			final KeyPair key = keyGen.generateKeyPair();

			File privateKeyFile = new File(keyFilePath + DefineUtils.FILE_SEPARATOR + "private.key");
			File publicKeyFile = new File(keyFilePath + DefineUtils.FILE_SEPARATOR + "public.key");
			System.out.println(keyFilePath + DefineUtils.FILE_SEPARATOR + "private.key");
			System.out.println(keyFilePath + DefineUtils.FILE_SEPARATOR + "public.key");

			if (privateKeyFile.getParentFile() != null) {
				privateKeyFile.getParentFile().mkdirs();
			}
			privateKeyFile.createNewFile();

			if (publicKeyFile.getParentFile() != null) {
				publicKeyFile.getParentFile().mkdirs();
			}
			publicKeyFile.createNewFile();

			ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
			publicKeyOS.writeObject(key.getPublic());
			publicKeyOS.close();

			ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
			privateKeyOS.writeObject(key.getPrivate());
			privateKeyOS.close();

			keys[0] = publicKeyFile;
			keys[1] = privateKeyFile;
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "Nie udało się wygenerować kluczy", DefineUtils.APP_TITLE,
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return keys;
	}

	public boolean isKeyPresent(String keyPath) {
		File key = new File(keyPath);

		if (key.exists()) {
			return true;
		}

		return false;
	}

	public String[] encryptMessage(String attachement, String message, Path publicKeyFilePath) {
		File[] keys = null;
		File privateKeyFile = null;
		File publicKeyFile = null;
		String[] encryptedValues = new String[2];

		if (!isKeyPresent(publicKeyFilePath.toString())) {
			JOptionPane.showConfirmDialog(null, "Podane klucze nie mogły zostać wczytane, zostaną wygenerowane nowe",
					DefineUtils.APP_TITLE, JOptionPane.PLAIN_MESSAGE);
			keys = generateKey(publicKeyFilePath.getParent().toString());
			if (keys != null) {
				privateKeyFile = keys[1];
				publicKeyFile = keys[0];

				System.out.println(privateKeyFile.getAbsolutePath());
				System.out.println(publicKeyFile.getAbsolutePath());
			} else {
				return null;
			}
		} else {
			publicKeyFile = new File(publicKeyFilePath.toString());
			privateKeyFile = new File(DBData.pathInitPrivateKey);
		}

		try {

			FileInputStream fileInputStream = new FileInputStream(attachement);
			FileOutputStream fileOutputStream = new FileOutputStream(attachement + "_encrypted");
			final Cipher cipher = Cipher.getInstance(DefineUtils.ALGORITHM);
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(publicKeyFile));
			final PublicKey publicKey = (PublicKey) inputStream.readObject();

			byte[] block = new byte[32];
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] cipherMessage = cipher.doFinal(message.getBytes());

			while ((fileInputStream.read(block)) != -1) {
				byte[] inputfile = cipher.doFinal(block);
				fileOutputStream.write(inputfile);
			}

			fileInputStream.close();
			fileOutputStream.close();
			inputStream.close();

			encryptedValues[0] = cipherMessage.toString();
			encryptedValues[1] = attachement + "_encrypted";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return encryptedValues;
	}

	public void decryptMessage(String attachement, String message, Path privateKeyPath) {
		
		try {
			FileInputStream fileInputStream = new FileInputStream(attachement);
			FileOutputStream fileOutputStream = new FileOutputStream(attachement.split("_encrypted")[0]);
			final Cipher cipher = Cipher.getInstance(DefineUtils.ALGORITHM);
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(privateKeyPath.toFile()));
			final PrivateKey privateKey = (PrivateKey) inputStream.readObject();

			byte[] block = new byte[128];
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			while ((fileInputStream.read(block)) != -1) {
				byte[] inputfile = cipher.doFinal(block);
				fileOutputStream.write(inputfile);
			}

			fileInputStream.close();
			fileOutputStream.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void decryptMessage(DataHandler selectedAttachement, String message, Path path) {
		try {
			InputStream inputStream = selectedAttachement.getInputStream();

			File fileDir = new File(DefineUtils.FILE_SEPARATOR + "Pobrane załączniki");

			if(!fileDir.exists()){
				fileDir.mkdir();
			}
			
			File file = new File(DefineUtils.FILE_SEPARATOR + "Pobrane załączniki" + DefineUtils.FILE_SEPARATOR + selectedAttachement.getName());
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] buf = new byte[4096];
			int bytesRead;
			
			while ((bytesRead = inputStream.read(buf)) != -1) {
				fileOutputStream.write(buf, 0, bytesRead);
			}
			
			fileOutputStream.close();
			
			decryptMessage(file.getAbsolutePath(), "", path);

			Desktop.getDesktop().open(fileDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
