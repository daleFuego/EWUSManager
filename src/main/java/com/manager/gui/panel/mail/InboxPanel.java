package com.manager.gui.panel.mail;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class InboxPanel extends JPanel {
	@SuppressWarnings("rawtypes")
	public InboxPanel() {
		setLayout(null);
		
		JScrollPane scrollPaneMails = new JScrollPane();
		scrollPaneMails.setBounds(12, 11, 379, 215);
		add(scrollPaneMails);
		
		JList listMails = new JList();
		scrollPaneMails.setViewportView(listMails);
	}
}
