package com.manager.newgui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.manager.dao.DBData;
import com.manager.data.DataCompression;
import com.manager.data.DataLoader;
import com.manager.logic.FileManager;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PanelPackFiles extends JPanel {
	private JTextField textFieldFilePath;

	public PanelPackFiles(String pathToCertificates, JTextField textFieldSendFilesMailFile) {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Pakowanie potwierdze\u0144",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);

		JLabel labelFilePath = new JLabel("Ścieżka");
		labelFilePath.setBounds(9, 44, 65, 14);
		add(labelFilePath);

		JLabel labelInfo = new JLabel("Gdzie zapisać spakowane potwierdzenia?");
		labelInfo.setBounds(10, 19, 326, 14);
		add(labelInfo);

		JButton buttonPack = new JButton("Spakuj");
		buttonPack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataCompression dataCompression = new DataCompression(DataLoader.getInstance().getFileList(),
						textFieldFilePath.getText(), textFieldFilePath.getText());
				dataCompression.zipFiles(textFieldSendFilesMailFile);
			}
		});
		buttonPack.setBounds(562, 41, 109, 20);
		add(buttonPack);

		textFieldFilePath = new JTextField(DBData.pathInitBrowseFiles);
		textFieldFilePath.setBounds(83, 41, 352, 20);
		textFieldFilePath.setColumns(10);
		add(textFieldFilePath);

		JButton buttonBrowses = new JButton("Przeglądaj");
		buttonBrowses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileManager fileManager = new FileManager(textFieldFilePath);
				fileManager.browseDirectory();
			}
		});
		buttonBrowses.setBounds(444, 41, 109, 20);
		add(buttonBrowses);
	}

	public JTextField getTextFieldFilePath() {
		return textFieldFilePath;
	}
}
