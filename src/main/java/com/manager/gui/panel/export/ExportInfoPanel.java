package com.manager.gui.panel.export;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class ExportInfoPanel extends JPanel {
	private JTextArea textAreaDetails;

	public ExportInfoPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Informacje o potwierdzeniach",
				TitledBorder.LEADING, TitledBorder.TOP, DefineUtils.FONT, new Color(0, 0, 0)));
		setLayout(null);

		JScrollPane scrollPaneDetails = new JScrollPane();
		scrollPaneDetails.setBounds(10, 17, 660, 47);
		add(scrollPaneDetails);

		textAreaDetails = new JTextArea();
		textAreaDetails.setColumns(3);
		textAreaDetails.setFont(DefineUtils.FONT);
		textAreaDetails.setRows(2);
		textAreaDetails.setLineWrap(true);
		scrollPaneDetails.setViewportView(textAreaDetails);
	}

	public JTextArea getTextAreaDetails() {
		return textAreaDetails;
	}
}
