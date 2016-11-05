package com.manager.gui.panel.queue;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.manager.dao.DBData;
import com.manager.logic.FileManager;
import com.manager.logic.QueueManager;
import com.manager.utils.DefineUtils;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class QueuePanel extends JPanel {

	private JTextField textFieldFilePath;
	private JTextField textFieldSendQueue;

	private QueueManager queueManager;
	private QueueTable panelQueueTable;

	public QueuePanel() {
		setLayout(null);
		setVisible(true);

		panelQueueTable = new QueueTable("");
		panelQueueTable.setBounds(6, 105, 553, 345);
		add(panelQueueTable);

		JPanel panelControls = new JPanel();
		panelControls.setLayout(null);
		panelControls.setBounds(561, 105, 119, 345);
		add(panelControls);

		JButton buttonSave = new JButton("Zapisz");
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (queueManager != null) {
					queueManager.saveChanges();
				}
			}
		});
		buttonSave.setBounds(5, 220, 109, 23);
		panelControls.add(buttonSave);

		JButton buttonCreateNew = new JButton("Utwórz nowy");
		buttonCreateNew.setBounds(5, 40, 109, 20);
		buttonCreateNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (queueManager == null) {
					queueManager = new QueueManager(null, textFieldFilePath, panelQueueTable.getTable());
				}
				queueManager.createNewFile();
			}
		});
		panelControls.add(buttonCreateNew);

		JButton buttonReject = new JButton("Odrzuć");
		buttonReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (queueManager != null) {
					queueManager.rejectChanges();
				}
			}
		});
		buttonReject.setBounds(5, 283, 109, 20);
		panelControls.add(buttonReject);

		JButton buttonAddDate = new JButton("Dodaj termin");
		buttonAddDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (queueManager != null) {
					queueManager.addDate();
				}
			}
		});
		buttonAddDate.setBounds(5, 160, 109, 20);
		panelControls.add(buttonAddDate);

		JButton buttonAddVisit = new JButton("Dodaj wizytę");
		buttonAddVisit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (queueManager == null) {
					queueManager = new QueueManager(textFieldFilePath.getText(), textFieldFilePath,
							panelQueueTable.getTable());
				}
				setEnabled(false);
				queueManager.addVisit();
				setEnabled(true);
			}
		});
		buttonAddVisit.setBounds(5, 100, 109, 20);
		panelControls.add(buttonAddVisit);

		JPanel panelFilePath = new JPanel();
		panelFilePath.setBounds(6, 58, 674, 40);
		add(panelFilePath);
		panelFilePath.setLayout(null);

		JLabel labelFilePath = new JLabel("Podaj ścieżkę do pliku z kolejką");
		labelFilePath.setBounds(12, 13, 147, 14);
		panelFilePath.add(labelFilePath);

		textFieldFilePath = new JTextField();
		textFieldFilePath.setBounds(171, 10, 384, 20);
		panelFilePath.add(textFieldFilePath);
		textFieldFilePath.setText(DBData.pathInitQueueExistingFile);
		textFieldFilePath.setColumns(10);

		JButton buttonBrowse = new JButton("Przeglądaj");
		buttonBrowse.addActionListener(new ActionListener() {

			private FileManager fileManager;

			@Override
			public void actionPerformed(ActionEvent e) {
				fileManager = new FileManager(textFieldFilePath);
				queueManager = new QueueManager(fileManager.browseFile(), textFieldFilePath,
						panelQueueTable.getTable());
				queueManager.parseFile(false);
				textFieldSendQueue.setText(textFieldFilePath.getText());

			}
		});
		buttonBrowse.setBounds(565, 10, 109, 20);
		panelFilePath.add(buttonBrowse);

		JScrollPane scrollPaneDescription = new JScrollPane();
		scrollPaneDescription.setBounds(6, 7, 676, 44);
		add(scrollPaneDescription);

		JTextArea textAreaDescription = new JTextArea();
		textAreaDescription.setEditable(false);
		textAreaDescription.setLineWrap(true);
		textAreaDescription.setRows(2);
		textAreaDescription.setFont(new Font("Arial", Font.PLAIN, 11));
		textAreaDescription.setWrapStyleWord(true);
		textAreaDescription.setText(DefineUtils.QUEUE_DESCRIPTION);
		scrollPaneDescription.setViewportView(textAreaDescription);
	}

	public JTextField getTextFieldFilePath() {
		return textFieldFilePath;
	}

	public QueueTable getPanelQueueTable() {
		return panelQueueTable;
	}

	public void setQueueManager(QueueManager queueManager) {
		this.queueManager = queueManager;
	}

	public void setTextFieldSendQueue(JTextField textFieldSendQueue) {
		this.textFieldSendQueue = textFieldSendQueue;
	}
}
