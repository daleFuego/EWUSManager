package com.manager.gui.panel.mail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.manager.dao.DBData;
import com.manager.logic.FileManager;
import com.manager.utils.DefineUtils;
import com.manager.utils.EncryptionUtils;

@SuppressWarnings("serial")
public class EncryptionPanel extends JPanel {
	private JTextField textFieldPublicKeyPath;
	private JTextField textFieldPrivateKeyPath;
	private JButton buttonEncryptOptions;
	private JButton buttonLoadPublicKey;
	private JButton buttonLoadPrivateKey;
	private JButton buttonGenerateKeys;

	public EncryptionPanel(boolean encryptionMode, String attachement, String message) {
		setLayout(null);
		setSize(529, 96);
		setVisible(true);

		if (encryptionMode) {
			buttonEncryptOptions = new JButton("Szyfruj");
		} else {
			buttonEncryptOptions = new JButton("Deszyfruj");
		}
		buttonEncryptOptions.setFont(DefineUtils.FONT);
		buttonEncryptOptions.setBounds(370, 67, 154, 20);
		add(buttonEncryptOptions);

		JLabel labelPublicKeyPath = new JLabel("Ścieżka klucza publicznego");
		labelPublicKeyPath.setFont(DefineUtils.FONT);
		labelPublicKeyPath.setBounds(5, 12, 143, 14);
		add(labelPublicKeyPath);

		JLabel labelPrivateKeyPath = new JLabel("Ścieżka klucza prywatnego");
		labelPrivateKeyPath.setFont(DefineUtils.FONT);
		labelPrivateKeyPath.setBounds(5, 41, 143, 14);
		add(labelPrivateKeyPath);

		textFieldPublicKeyPath = new JTextField(DBData.pathInitPublicKey);
		textFieldPublicKeyPath.setFont(DefineUtils.FONT);
		textFieldPublicKeyPath.setColumns(10);
		textFieldPublicKeyPath.setBounds(153, 9, 212, 20);
		add(textFieldPublicKeyPath);

		textFieldPrivateKeyPath = new JTextField(DBData.pathInitPrivateKey);
		textFieldPrivateKeyPath.setFont(DefineUtils.FONT);
		textFieldPrivateKeyPath.setColumns(10);
		textFieldPrivateKeyPath.setBounds(153, 38, 212, 20);
		add(textFieldPrivateKeyPath);

		buttonLoadPublicKey = new JButton("Załaduj klucz publiczny");
		buttonLoadPublicKey.setFont(DefineUtils.FONT);
		buttonLoadPublicKey.setBounds(370, 9, 154, 20);
		buttonLoadPublicKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldPublicKeyPath.setText(
						(new FileManager().browseFile(DefineUtils.DB_pathpublickey, "Plik klucza (*.key)", "key")));
			}
		});
		add(buttonLoadPublicKey);

		buttonLoadPrivateKey = new JButton("Załaduj klucz prywatny");
		buttonLoadPrivateKey.setFont(DefineUtils.FONT);
		buttonLoadPrivateKey.setBounds(370, 38, 154, 20);
		buttonLoadPrivateKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldPrivateKeyPath.setText(
						(new FileManager().browseFile(DefineUtils.DB_pathprivatekey, "Plik klucza (*.key)", "key")));
			}
		});
		add(buttonLoadPrivateKey);

		buttonGenerateKeys = new JButton("Generuj klucze");
		buttonGenerateKeys.setFont(DefineUtils.FONT);
		buttonGenerateKeys.setBounds(5, 67, 359, 20);
		buttonGenerateKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pathToSaveKeys = (new FileManager()).browseDirectory("", "Wszystkie pliki (*.*)", "*.*");
				EncryptionUtils.getInstance().generateKey(pathToSaveKeys, textFieldPrivateKeyPath,
						textFieldPublicKeyPath);
			}
		});
		add(buttonGenerateKeys);

	}

	public JTextField getTextFieldPrivateKeyPath() {
		return textFieldPrivateKeyPath;
	}
	public JButton getButtonEncryptOptions() {
		return buttonEncryptOptions;
	}
	public JTextField getTextFieldPublicKeyPath() {
		return textFieldPublicKeyPath;
	}
	public JButton getButtonLoadPublicKey() {
		return buttonLoadPublicKey;
	}
	public JButton getButtonLoadPrivateKey() {
		return buttonLoadPrivateKey;
	}
	public JButton getButtonGenerateKeys() {
		return buttonGenerateKeys;
	}
}
