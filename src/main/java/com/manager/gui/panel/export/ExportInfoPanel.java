package com.manager.gui.panel.export;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class ExportInfoPanel extends JPanel {
	private JTextArea textAreaDetails;

	public ExportInfoPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Informacje o potwierdzeniach",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);

		JScrollPane scrollPaneDetails = new JScrollPane();
		scrollPaneDetails.setBounds(10, 17, 660, 47);
		add(scrollPaneDetails);

		textAreaDetails = new JTextArea();
		textAreaDetails.setRows(2);
		textAreaDetails.setLineWrap(true);
		scrollPaneDetails.setViewportView(textAreaDetails);
	}

	public JTextArea getTextAreaDetails() {
		return textAreaDetails;
	}
}
