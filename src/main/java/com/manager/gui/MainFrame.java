package com.manager.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.manager.dao.DBData;
import com.manager.data.DataLoader;
import com.manager.gui.panel.certificates.CertificatesPanel;
import com.manager.gui.panel.export.ExportPanel;
import com.manager.gui.panel.export.FileExportPanel;
import com.manager.gui.panel.export.FileZipPanel;
import com.manager.gui.panel.queue.QueuePanel;
import com.manager.logic.QueueManager;
import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JTabbedPane tabbedPane;
	private QueueManager queueManager;
	private JPanel commonsPane;
	private JLabel lblCommonsAppVersionDesc;
	private JButton btnCommonsClose;
	private QueuePanel panelQueue;
	private CertificatesPanel panelCertificates;
	private ExportPanel panelSend;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		setResizable(false);
		setLocale(new Locale("pl", "PL"));
		setTitle(DefineUtils.APP_TITLE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icon.png")));
		DefineUtils.initDataLoad();
		DBData.getInstance().getInitData();
		DBData.getInstance().checkEncryptionStatus();

		initialize();
	}

	public void initialize() {

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 705, 487);
		tabbedPane.setFont(DefineUtils.FONT);
		getContentPane().setLayout(null);

		panelSend = new ExportPanel();

		panelQueue = new QueuePanel();
		panelQueue.setTextFieldSendQueue(panelSend.getPanelSendQueue().getTextFieldFilePath());
		panelQueue.setBounds(5, 0, 690, 459);
		tabbedPane.addTab("Kolejkowanie", null, panelQueue, null);

		panelCertificates = new CertificatesPanel(panelSend.getPanelSendInfo().getTextAreaDetails());
		panelCertificates.setLocation(0, 0);
		panelCertificates.setSize(690, 459);
		tabbedPane.addTab("Potwierdzenia", null, panelCertificates, null);

		FileExportPanel panelSendFiles = new FileExportPanel();
		panelSendFiles.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Wysy\u0142anie paczki z potwierdzeniami",
						TitledBorder.LEADING, TitledBorder.TOP, DefineUtils.FONT, new Color(0, 0, 0)));
		panelSendFiles.setBounds(5, 230, 679, 105);
		panelSend.add(panelSendFiles);
		FileZipPanel panelPackFiles = new FileZipPanel(panelCertificates.getTextFieldFilePath().getText(),
				panelSendFiles.getTextFieldFilePath());
		panelPackFiles.setBorder(new TitledBorder(null, "Pakowanie potwierdze\u0144", TitledBorder.LEADING,
				TitledBorder.TOP, DefineUtils.FONT, new Color(0, 0, 0)));
		panelPackFiles.setBounds(5, 146, 679, 75);
		panelSend.add(panelPackFiles);
		tabbedPane.addTab("Wysyłanie plików", null, panelSend, null);
		getContentPane().add(tabbedPane);

		commonsPane = new JPanel();
		commonsPane.setBounds(0, 488, 699, 33);
		getContentPane().add(commonsPane);
		commonsPane.setLayout(null);

		lblCommonsAppVersionDesc = new JLabel(DefineUtils.APP_TITLE + DefineUtils.APP_VERSION);
		lblCommonsAppVersionDesc.setFont(DefineUtils.FONT);
		lblCommonsAppVersionDesc.setBounds(10, 9, 159, 14);
		commonsPane.add(lblCommonsAppVersionDesc);

		btnCommonsClose = new JButton("Zamknij");
		btnCommonsClose.setFont(DefineUtils.FONT);
		btnCommonsClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCommonsClose.setBounds(580, 6, 109, 20);
		commonsPane.add(btnCommonsClose);

		setVisible(true);
		setBounds(100, 100, 705, 553);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
		}

		fillParts();
	}

	private void fillParts() {
		if (!panelQueue.getTextFieldFilePath().equals("")) {
			queueManager = new QueueManager(panelQueue.getTextFieldFilePath(),
					panelQueue.getPanelQueueTable().getTable());
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
}
