package com.manager.gui.panel.mail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class MailClientFrame extends JFrame {

	private MessagesPanel messagesPanel;

	public MailClientFrame() {
		setResizable(false);
		setTitle(DefineUtils.APP_TITLE + " | " + DefineUtils.APP_MAIL_SUBTITLE);
		setVisible(true);
		setSize(837, 676);
		getContentPane().setLayout(null);
		DefineUtils.loadInits();
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		initialize();
	}

	private void initialize() {
		messagesPanel = new MessagesPanel();
		messagesPanel.setSize(822, 585);
		messagesPanel.setLocation(4, 11);
		getContentPane().add(messagesPanel);

		JPanel panelCommons = new JPanel();
		panelCommons.setLayout(null);
		panelCommons.setBounds(4, 605, 822, 31);
		getContentPane().add(panelCommons);

		JLabel labelVersion = new JLabel(DefineUtils.APP_TITLE + DefineUtils.APP_VERSION);
		labelVersion.setFont(DefineUtils.FONT);
		labelVersion.setBounds(10, 9, 159, 14);
		panelCommons.add(labelVersion);

		JButton buttonExit = new JButton("Zamknij");
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		buttonExit.setFont(DefineUtils.FONT);
		buttonExit.setBounds(703, 6, 109, 20);
		panelCommons.add(buttonExit);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
		}
	}
}
