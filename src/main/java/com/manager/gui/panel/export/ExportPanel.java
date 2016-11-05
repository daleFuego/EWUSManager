package com.manager.gui.panel.export;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class ExportPanel extends JPanel {
	private JTextArea textAreaDescription;
	private ExportInfoPanel panelSendInfo;
	private String pathToCertificates;
	private QueueExportPanel panelSendQueue;

	public ExportPanel() {
		setLayout(null);

		panelSendInfo = new ExportInfoPanel();
		panelSendInfo.getTextAreaDetails().setEditable(false);
		panelSendInfo.getTextAreaDetails().setRows(0);
		panelSendInfo.getTextAreaDetails().setFont(new Font("Arial", Font.PLAIN, 11));
		panelSendInfo.setBorder(new TitledBorder(null, "Informacje o potwierdzeniach", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSendInfo.setBounds(5, 62, 679, 75);
		add(panelSendInfo);

		JScrollPane scrollPaneDescription = new JScrollPane();
		scrollPaneDescription.setBounds(7, 9, 676, 44);
		add(scrollPaneDescription);

		textAreaDescription = new JTextArea();
		textAreaDescription.setText(DefineUtils.SEND_DESRIPTION);
		scrollPaneDescription.setViewportView(textAreaDescription);
		textAreaDescription.setWrapStyleWord(true);
		textAreaDescription.setRows(2);
		textAreaDescription.setLineWrap(true);
		textAreaDescription.setFont(new Font("Arial", Font.PLAIN, 11));
		textAreaDescription.setEditable(false);
		FileExportPanel panelSendFiles = new FileExportPanel();
		panelSendFiles.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Wysy\u0142anie paczki z potwierdzeniami",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSendFiles.setBounds(5, 230, 679, 105);
		add(panelSendFiles);
		FileZipPanel panelPackFiles = new FileZipPanel(pathToCertificates, panelSendFiles.getTextFieldFilePath());
		panelPackFiles.setBorder(new TitledBorder(null, "Pakowanie potwierdze\u0144", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelPackFiles.setBounds(5, 146, 679, 75);
		add(panelPackFiles);
		panelSendQueue = new QueueExportPanel();
		panelSendQueue.setBounds(5, 344, 679, 105);
		add(panelSendQueue);

	}
	
	public ExportInfoPanel getPanelSendInfo() {
		return panelSendInfo;
	}

	public void setPathToCertificates(String pathToCertificates) {
		this.pathToCertificates = pathToCertificates;
	}

	public QueueExportPanel getPanelSendQueue() {
		return panelSendQueue;
	}
}
