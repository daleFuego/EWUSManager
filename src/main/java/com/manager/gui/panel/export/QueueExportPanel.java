package com.manager.gui.panel.export;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.manager.dao.DBData;
import com.manager.logic.FileManager;
import com.manager.logic.MailManager;
import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class QueueExportPanel extends JPanel {
	private JTextField textFieldSender;
	private JTextField textFieldReceiver;
	private JTextField textFieldFilePath;

	public QueueExportPanel() {
		setBorder(new TitledBorder(null, "Wysy\u0142anie pliku z kolejk\u0105 pacjent\u00F3w", TitledBorder.LEADING,
				TitledBorder.TOP, DefineUtils.FONT, new Color(0, 0, 0)));
		setLayout(null);

		JLabel labelSender = new JLabel("Nadawca");
		labelSender.setFont(DefineUtils.FONT);
		labelSender.setBounds(10, 19, 65, 14);
		add(labelSender);

		textFieldSender = new JTextField(DBData.pathInitSendMailSender);
		textFieldSender.setFont(DefineUtils.FONT);
		textFieldSender.setEnabled(false);
		textFieldSender.setEditable(false);
		textFieldSender.setColumns(10);
		textFieldSender.setBounds(83, 16, 352, 20);
		textFieldSender.setFont(DefineUtils.FONT);
		add(textFieldSender);

		JLabel labelReceiver = new JLabel("Odbiorca");
		labelReceiver.setFont(DefineUtils.FONT);
		labelReceiver.setBounds(10, 49, 65, 14);
		add(labelReceiver);

		textFieldReceiver = new JTextField(DBData.pathInitSendMailReceiver);
		textFieldReceiver.setFont(DefineUtils.FONT);
		textFieldReceiver.setFont(DefineUtils.FONT);
		textFieldReceiver.setColumns(10);
		textFieldReceiver.setBounds(83, 46, 352, 20);
		add(textFieldReceiver);

		JLabel labelFilePath = new JLabel("Ścieżka");
		labelFilePath.setFont(DefineUtils.FONT);
		labelFilePath.setBounds(9, 79, 65, 14);
		add(labelFilePath);

		textFieldFilePath = new JTextField(DBData.pathInitQueueExistingFile);
		textFieldFilePath.setColumns(10);
		textFieldFilePath.setFont(DefineUtils.FONT);
		textFieldFilePath.setBounds(83, 76, 352, 20);
		add(textFieldFilePath);

		JButton buttonSend = new JButton("Wyślij");
		buttonSend.setFont(DefineUtils.FONT);
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
		buttonBrowse.setFont(DefineUtils.FONT);
		buttonBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileManager fileManager = new FileManager(textFieldFilePath);
				fileManager.browseFile();
			}
		});
		buttonBrowse.setBounds(444, 76, 109, 20);
		add(buttonBrowse);
		
		JButton button = new JButton("Książka adresowa");
		button.setFont(DefineUtils.FONT);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddresssBook(textFieldReceiver);
			}
		});
		button.setBounds(445, 46, 226, 20);
		button.setFont(DefineUtils.FONT);
		add(button);
	}

	public JTextField getTextFieldFilePath() {
		return textFieldFilePath;
	}
}
