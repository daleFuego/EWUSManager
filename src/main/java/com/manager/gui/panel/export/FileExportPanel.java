package com.manager.gui.panel.export;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.manager.dao.DBData;
import com.manager.data.DataLoader;
import com.manager.logic.FileManager;
import com.manager.logic.MailManager;
import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class FileExportPanel extends JPanel {
	private JTextField textFieldFilePath;
	private JTextField textFieldReceiver;
	private JTextField textFieldSender;

	public FileExportPanel() {
		setBorder(new TitledBorder(null, "Wysy\u0142anie paczki z potwierdzeniami", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(null);

		textFieldFilePath = new JTextField();
		textFieldFilePath.setText(DBData.pathInitArchive);
		textFieldFilePath.setFont(DefineUtils.FONT);
		textFieldFilePath.setBounds(83, 77, 352, 20);
		add(textFieldFilePath);

		JLabel labelReceiver = new JLabel("Odbiorca");
		labelReceiver.setFont(DefineUtils.FONT);
		labelReceiver.setBounds(10, 51, 65, 14);
		add(labelReceiver);

		JButton buttonSend = new JButton("Wyślij");
		buttonSend.setFont(DefineUtils.FONT);
		buttonSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MailManager mailManager = new MailManager(textFieldSender.getText(), textFieldReceiver.getText(),
						textFieldFilePath.getText(), DataLoader.getInstance().ewusMailTopic());
				mailManager.sendMail();
			}
		});
		buttonSend.setBounds(562, 77, 109, 20);
		add(buttonSend);

		JLabel labelFilePath = new JLabel("Ścieżka");
		labelFilePath.setFont(DefineUtils.FONT);
		labelFilePath.setBounds(9, 80, 65, 14);
		add(labelFilePath);

		textFieldReceiver = new JTextField();
		textFieldReceiver.setText(DBData.pathInitSendMailReceiver);
		textFieldReceiver.setBounds(83, 46, 352, 20);
		textFieldReceiver.setFont(DefineUtils.FONT);
		add(textFieldReceiver);

		JButton buttonBrowse = new JButton("Przeglądaj");
		buttonBrowse.setFont(DefineUtils.FONT);
		buttonBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileManager fileManager = new FileManager(textFieldFilePath);
				fileManager.browseZipFile();
			}
		});
		buttonBrowse.setBounds(444, 77, 109, 20);
		add(buttonBrowse);

		JLabel labelSender = new JLabel("Nadawca");
		labelSender.setFont(DefineUtils.FONT);
		labelSender.setBounds(10, 22, 65, 14);
		add(labelSender);

		textFieldSender = new JTextField(DBData.pathInitSendMailSender);
		textFieldSender.setFont(DefineUtils.FONT);
		textFieldSender.setEnabled(false);
		textFieldSender.setBounds(83, 17, 352, 20);
		add(textFieldSender);
		
		JButton btnBrowseContacts = new JButton("Książka adresowa");
		btnBrowseContacts.setFont(DefineUtils.FONT);
		btnBrowseContacts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddresssBook(textFieldReceiver);
			}
		});
		btnBrowseContacts.setBounds(445, 46, 226, 20);
		add(btnBrowseContacts);
	}

	public JTextField getTextFieldFilePath() {
		return textFieldFilePath;
	}
}
