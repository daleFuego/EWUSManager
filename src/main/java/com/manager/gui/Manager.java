package com.manager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.manager.dao.DBData;
import com.manager.data.DataLoader;
import com.manager.logic.QueueManager;
import com.manager.newgui.PanelCertificates;
import com.manager.newgui.PanelQueue;
import com.manager.newgui.PanelSend;
import com.manager.utils.CallTrace;
import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class Manager extends JFrame {
	private JTabbedPane tabbedPane;
	private QueueManager queueManager;
	private JPanel commonsPane;
	private JLabel lblCommonsAppVersionDesc;
	private JButton btnCommonsClose;
	private PanelQueue panelQueue;
	private PanelCertificates panelCertificates;

	public Manager() {
		setLocale(new Locale("pl", "PL"));
		setTitle(DefineUtils.APP_TITLE);
		DefineUtils.initDataLoad();
		DBData.getInstance().getInitData();

		initialize();
	}

	public void initialize() {

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 705, 487);
		getContentPane().setLayout(null);

		PanelSend panelSend = new PanelSend();

		panelQueue = new PanelQueue();
		panelQueue.setTextFieldSendQueue(panelSend.getPanelSendQueue().getTextFieldFilePath());
		panelQueue.setBounds(5, 0, 690, 459);
		tabbedPane.addTab("Kolejkowanie", null, panelQueue, null);

		panelCertificates = new PanelCertificates(panelSend.getPanelSendInfo().getTextAreaDetails());
		panelCertificates.setLocation(0, 0);
		panelCertificates.setSize(690, 459);
		tabbedPane.addTab("Potwierdzenia", null, panelCertificates, null);

		panelSend.setPathToCertificates(panelCertificates.getTextFieldFilePath().getText());
		tabbedPane.addTab("Wysyłanie plików", null, panelSend, null);
		getContentPane().add(tabbedPane);

		commonsPane = new JPanel();
		commonsPane.setBounds(0, 488, 699, 33);
		getContentPane().add(commonsPane);
		commonsPane.setLayout(null);

		lblCommonsAppVersionDesc = new JLabel(DefineUtils.APP_TITLE + DefineUtils.APP_VERSION);
		lblCommonsAppVersionDesc.setBounds(10, 9, 159, 14);
		commonsPane.add(lblCommonsAppVersionDesc);

		btnCommonsClose = new JButton("Zamknij");
		btnCommonsClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				quit();
			}
		});
		btnCommonsClose.setBounds(580, 6, 109, 20);
		commonsPane.add(btnCommonsClose);

		setVisible(true);
		setBounds(100, 100, 705, 553);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
		}

		fillParts();
	}

	private void fillParts() {
		if (!panelQueue.getTextFieldFilePath().equals("")) {
			queueManager = new QueueManager(panelQueue.getTextFieldFilePath().getText(),
					panelQueue.getTextFieldFilePath(), panelQueue.getPanelQueueTable().getTable());
			queueManager.parseFile(false);
			panelQueue.setQueueManager(queueManager);
		}

		if (!panelCertificates.getTextFieldFilePath().equals("")) {
			DataLoader.getInstance().setDirectory(panelCertificates.getTextFieldFilePath().getText());
			DataLoader.getInstance()
					.setTable(((DefaultTableModel) panelCertificates.getCertificatesTable().getTable().getModel()));
			panelCertificates.refreshTable();
		}
	}

	private void quit() {
		try {
			CallTrace.getInstance().clearLogs();
		} catch (Exception ex) {
		}
		System.exit(0);
	}
}
