package com.manager.gui.panel.export;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import com.manager.dao.DBData;
import com.manager.data.DataLoader;
import com.manager.gui.AddresssBook;
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

		textFieldReceiver = new JTextField();
		textFieldReceiver.setText(DBData.pathInitSendMailReceiver);
		textFieldReceiver.setBounds(83, 46, 352, 20);
		textFieldReceiver.setFont(DefineUtils.FONT);
		textFieldReceiver.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					new AddresssBook(textFieldReceiver);
				}

			}
		});
		add(textFieldReceiver);

		JCheckBox chckbxEncryption = new JCheckBox("Włącz szyfrowanie wiadomości");
		chckbxEncryption.setBounds(444, 18, 227, 23);
		chckbxEncryption.setSelected(DefineUtils.FILE_ENCRYPTION_STATUS);
		chckbxEncryption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DBData.getInstance().update(DefineUtils.DB_TABLE_ENCRYPTION, DefineUtils.DB_fileencryptiostatus,
						chckbxEncryption.isSelected());
			}
		});
		add(chckbxEncryption);

		JLabel labelReceiver = new JLabel("Odbiorca");
		labelReceiver.setFont(DefineUtils.FONT);
		labelReceiver.setBounds(10, 51, 65, 14);
		add(labelReceiver);

		JButton buttonSend = new JButton("Wyślij");
		buttonSend.setFont(DefineUtils.FONT);
		buttonSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MailManager mailManager = new MailManager(textFieldSender.getText(), textFieldReceiver.getText(),
						DataLoader.getInstance().ewusMailTopic());

				mailManager.sendMail(textFieldFilePath.getText(), chckbxEncryption.isSelected());
				
				DBData.getInstance().insertOrUpdate(DefineUtils.DB_TABLE_CONTACTS, DefineUtils.DB_mailAddress,
						textFieldReceiver.getText());
				DBData.getInstance().insertOrUpdate(DefineUtils.DB_TABLE_CONTACTS, DefineUtils.DB_mailAddress,
						textFieldSender.getText());
				DBData.getInstance().insertOrUpdate(DefineUtils.DB_TABLE_PATHS, DefineUtils.DB_pathsendmailsender,
						textFieldSender.getText());
				DBData.getInstance().insertOrUpdate(DefineUtils.DB_TABLE_PATHS, DefineUtils.DB_pathsendmailreceiver,
						textFieldReceiver.getText());
			}
		});
		buttonSend.setBounds(562, 77, 109, 20);
		add(buttonSend);

		JLabel labelFilePath = new JLabel("Ścieżka");
		labelFilePath.setFont(DefineUtils.FONT);
		labelFilePath.setBounds(9, 80, 65, 14);
		add(labelFilePath);

		JButton buttonBrowse = new JButton("Przeglądaj");
		buttonBrowse.setFont(DefineUtils.FONT);
		buttonBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldFilePath.setText(
						new FileManager().browseFile(DefineUtils.DB_pathsendmailfile, "Archiwum zip (*.zip)", "zip"));
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
		textFieldSender.setBounds(83, 17, 352, 20);
		textFieldSender.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					new AddresssBook(textFieldSender);
				}

			}
		});
		add(textFieldSender);
	}

	public JTextField getTextFieldFilePath() {
		return textFieldFilePath;
	}
}
