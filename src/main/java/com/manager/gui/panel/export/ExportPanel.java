package com.manager.gui.panel.export;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class ExportPanel extends JPanel {
	private JTextArea textAreaDescription;
	private ExportInfoPanel panelSendInfo;
	private QueueExportPanel panelSendQueue;
	public ExportPanel() {
		setLayout(null);

		panelSendInfo = new ExportInfoPanel();
		panelSendInfo.getTextAreaDetails().setEditable(false);
		panelSendInfo.getTextAreaDetails().setRows(0);
		panelSendInfo.getTextAreaDetails().setFont(DefineUtils.FONT);
		panelSendInfo.setBorder(new TitledBorder(null, "Informacje o potwierdzeniach", TitledBorder.LEADING,
				TitledBorder.TOP, DefineUtils.FONT, new Color(0, 0, 0)));
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
		textAreaDescription.setFont(DefineUtils.FONT);
		textAreaDescription.setEditable(false);
		panelSendQueue = new QueueExportPanel();
		panelSendQueue.setBounds(5, 344, 679, 105);
		add(panelSendQueue);

	}
	
	public ExportInfoPanel getPanelSendInfo() {
		return panelSendInfo;
	}

	public QueueExportPanel getPanelSendQueue() {
		return panelSendQueue;
	}
}
