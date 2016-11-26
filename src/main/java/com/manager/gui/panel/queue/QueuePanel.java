package com.manager.gui.panel.queue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.manager.dao.DBData;
import com.manager.logic.FileManager;
import com.manager.logic.QueueManager;
import com.manager.utils.DefineUtils;

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
		buttonSave.setFont(DefineUtils.FONT);
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
		buttonCreateNew.setFont(DefineUtils.FONT);
		buttonCreateNew.setBounds(5, 40, 109, 20);
		buttonCreateNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (queueManager == null) {
					queueManager = new QueueManager(textFieldFilePath, panelQueueTable.getTable());
				}
				queueManager.createNewFile();
			}
		});
		panelControls.add(buttonCreateNew);

		JButton buttonReject = new JButton("Odrzuć");
		buttonReject.setFont(DefineUtils.FONT);
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
		buttonAddDate.setFont(DefineUtils.FONT);
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
		buttonAddVisit.setFont(DefineUtils.FONT);
		buttonAddVisit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (queueManager == null) {
					queueManager = new QueueManager(textFieldFilePath,
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
		labelFilePath.setFont(DefineUtils.FONT);
		labelFilePath.setBounds(12, 13, 191, 14);
		panelFilePath.add(labelFilePath);

		textFieldFilePath = new JTextField();
		textFieldFilePath.setBounds(198, 10, 357, 20);
		textFieldFilePath.setFont(DefineUtils.FONT);
		panelFilePath.add(textFieldFilePath);
		textFieldFilePath.setText(DBData.pathInitQueueExistingFile);
		textFieldFilePath.setColumns(10);

		JButton buttonBrowse = new JButton("Przeglądaj");
		buttonBrowse.setFont(DefineUtils.FONT);
		buttonBrowse.addActionListener(new ActionListener() {

			private FileManager fileManager;

			public void actionPerformed(ActionEvent e) {
				fileManager = new FileManager();
				textFieldFilePath.setText(fileManager.browseFile(DefineUtils.DB_pathqueueexistingfile, "Pliki tekstowe (.txt)", "txt"));
				queueManager = new QueueManager(textFieldFilePath,
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
		textAreaDescription.setFont(DefineUtils.FONT);
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
