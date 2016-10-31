package com.manager.newgui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import com.manager.dao.DBData;
import com.manager.data.DataLoader;
import com.manager.logic.FileManager;
import com.manager.logic.MailManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PanelSendFiles extends JPanel {
	private JTextField textFieldFilePath;
	private JTextField textFieldReceiver;
	private JTextField textFieldSender;

	public PanelSendFiles() {
		setBorder(new TitledBorder(null, "Wysy\u0142anie paczki z potwierdzeniami", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		setLayout(null);

		textFieldFilePath = new JTextField();
		textFieldFilePath.setText(DBData.pathInitSendMailFile);
		textFieldFilePath.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldFilePath.setBounds(83, 77, 352, 20);
		add(textFieldFilePath);

		JLabel labelReceiver = new JLabel("Odbiorca");
		labelReceiver.setBounds(10, 51, 65, 14);
		add(labelReceiver);

		JButton buttonSend = new JButton("Wyślij");
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
		labelFilePath.setBounds(9, 80, 65, 14);
		add(labelFilePath);

		textFieldReceiver = new JTextField();
		textFieldReceiver.setText(DBData.pathInitSendMailReceiver);
		textFieldReceiver.setBounds(83, 46, 352, 20);
		add(textFieldReceiver);

		JButton buttonBrowse = new JButton("Przeglądaj");
		buttonBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileManager fileManager = new FileManager(textFieldFilePath);
				fileManager.browseZipFile();
			}
		});
		buttonBrowse.setBounds(444, 77, 109, 20);
		add(buttonBrowse);

		JLabel labelSender = new JLabel("Nadawca");
		labelSender.setBounds(10, 22, 65, 14);
		add(labelSender);

		textFieldSender = new JTextField(DBData.pathInitSendMailSender);
		textFieldSender.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldSender.setEnabled(false);
		textFieldSender.setBounds(83, 17, 352, 20);
		add(textFieldSender);
	}

	public JTextField getTextFieldFilePath() {
		return textFieldFilePath;
	}
}
