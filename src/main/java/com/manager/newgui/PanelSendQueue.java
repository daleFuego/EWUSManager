package com.manager.newgui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import com.manager.dao.DBData;
import com.manager.logic.FileManager;
import com.manager.logic.MailManager;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PanelSendQueue extends JPanel {
	private JTextField textFieldSender;
	private JTextField textFieldReceiver;
	private JTextField textFieldFilePath;

	public PanelSendQueue() {
		setBorder(new TitledBorder(null, "Wysy\u0142anie pliku z kolejk\u0105 pacjent\u00F3w", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		setLayout(null);

		JLabel labelSender = new JLabel("Nadawca");
		labelSender.setBounds(10, 19, 65, 14);
		add(labelSender);

		textFieldSender = new JTextField(DBData.pathInitSendMailSender);
		textFieldSender.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldSender.setEnabled(false);
		textFieldSender.setEditable(false);
		textFieldSender.setColumns(10);
		textFieldSender.setBounds(83, 16, 352, 20);
		add(textFieldSender);

		JLabel labelReceiver = new JLabel("Odbiorca");
		labelReceiver.setBounds(10, 49, 65, 14);
		add(labelReceiver);

		textFieldReceiver = new JTextField(DBData.pathInitSendMailReceiver);
		textFieldReceiver.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldReceiver.setColumns(10);
		textFieldReceiver.setBounds(83, 46, 352, 20);
		add(textFieldReceiver);

		JLabel labelFilePath = new JLabel("Ścieżka");
		labelFilePath.setBounds(9, 79, 65, 14);
		add(labelFilePath);

		textFieldFilePath = new JTextField(DBData.pathInitQueueExistingFile);
		textFieldFilePath.setColumns(10);
		textFieldFilePath.setBounds(83, 76, 352, 20);
		add(textFieldFilePath);

		JButton buttonSend = new JButton("Wyślij");
		buttonSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MailManager mailManager = new MailManager(textFieldSender.getText(), textFieldReceiver.getText(),
						textFieldFilePath.getText(),
						"Kolejka " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				mailManager.sendMail();
			}
		});
		buttonSend.setBounds(562, 76, 109, 20);
		add(buttonSend);

		JButton buttonBrowse = new JButton("Przeglądaj");
		buttonBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileManager fileManager = new FileManager(textFieldFilePath);
				fileManager.browseFile();
			}
		});
		buttonBrowse.setBounds(444, 76, 109, 20);
		add(buttonBrowse);
	}

	public JTextField getTextFieldSender() {
		return textFieldSender;
	}

	public JTextField getTextFieldReceiver() {
		return textFieldReceiver;
	}

	public JTextField getTextFieldFilePath() {
		return textFieldFilePath;
	}
}
