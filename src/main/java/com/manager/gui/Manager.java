package com.manager.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.manager.dao.DBData;
import com.manager.data.DataCompression;
import com.manager.data.DataLoader;
import com.manager.panel.FileManager;
import com.manager.panel.MailManager;
import com.manager.panel.QueueManager;
import com.manager.utils.CallTrace;
import com.manager.utils.DefineUtils;
import com.model.table.TableCellRender;

@SuppressWarnings("serial")
public class Manager extends JFrame {

	private JPanel panelQueueMain;
	private JLabel lblQueueMainFiles;
	private JLabel lblSendFilesMailReceiver;
	private JLabel lblSendFilesMailSender;
	private JLabel lblSendZipFile;
	private JLabel lblSendFilesMailFile;
	private JLabel lblSendZipFileQ;
	private JTextField textFieldBrowseFiles;
	private JTextArea textAreaSendInfo;
	private JTextArea textAreaQueueInfo;
	private JTabbedPane tabbedPane;
	private JPanel panelQueue;
	private JPanel panelBrowse;
	private JPanel panelSend;
	private JPanel panelSendFilesInfo;
	private JPanel panelSendMail;
	private JPanel panelSendZip;
	private JLabel lblBrowseFiles;
	private JButton btnBrowseBrowseFiles;
	private JButton btnSendFilesMailSend;
	private JButton btnSendExit;
	private JButton btnBrowseDeleteEntry;
	private JButton btnBrowseExit;
	private JButton btnBrowseUndoDelete;
	private JButton btnSendZipZip;
	private JButton btnSendZipBrowse;
	private JButton btnSendFilesMailBrowse;
	private JButton btnQueueMainFiles;
	private JTable tableBrowseMain;
	private JPanel panelBrowseMain;
	private JScrollPane scrollPaneBrowseMain;
	private JScrollPane scrollPaneSendFilesInfo;
	private String deleteFile;
	private DataLoader dataLoader;
	private DefaultTableModel tableModel;
	private JTextArea textAreaSendFilesInfo;
	private JTextField textFieldSendFilesMailReceiver;
	private JTextField textFieldSendZipFile;
	private JTextField textFieldSendFilesMailFile;
	private JTextField textFieldSendFilesMailSender;
	private JTextField textFieldQueueMainFiles;
	private JScrollPane scrollPaneQueueMainFileContent;
	private JButton btnQueueExit;
	private JButton btnQueueMainSaveChanges;
	private JButton btnQueueMainCreateNewFile;
	private JButton btnQueueMainRejectChanges;
	private JButton btnQueueMainAddVisit;
	private JButton btnQueueMainAddDate;
	private JTextArea textAreaBrowseInfo;
	private JPanel panelSendConsoleQueueFile;
	private JEditorPane editorPaneQueueMainFileContent;
	private QueueManager queueManager;
	private JScrollPane scrollPaneBrowseInfo;
	private JScrollPane scrollPaneQueueInfo;
	private JScrollPane scrollPaneSendInfo;
	private JTextField textFieldSendQueueMailSender;
	private JTextField textFieldSendQueueMailReceiver;
	private JTextField textFieldSendQueueMailFile;

	public Manager() {
		setLocale(new Locale("pl", "PL"));
		setTitle("EWUŚ MANAGER");
		DefineUtils defineUtils = new DefineUtils();
		defineUtils.initDataLoad();
		DBData.getInstance().getInitData();

		initialize();
	}

	public void initialize() {

		final JFrame frame = this;

		//LABEL
		lblSendFilesMailSender = new JLabel("Nadawca");
		lblSendFilesMailSender.setBounds(10, 20, 73, 14);
		lblBrowseFiles = new JLabel("Podaj ścieżkę do potwierdzeń");
		lblBrowseFiles.setBounds(5, 68, 171, 20);
		lblSendFilesMailFile = new JLabel("Ścieżka");
		lblSendFilesMailFile.setBounds(10, 78, 73, 14);
		lblSendZipFile = new JLabel("Ścieżka");
		lblSendZipFile.setBounds(10, 47, 73, 14);
		lblSendZipFileQ = new JLabel("Gdzie zapisać spakowane potwierdzenia?");
		lblSendZipFileQ.setBounds(10, 19, 326, 14);
		lblSendFilesMailReceiver = new JLabel("Odbiorca");
		lblSendFilesMailReceiver.setBounds(10, 49, 73, 14);
		lblQueueMainFiles = new JLabel("Podaj ścieżkę do pliku z kolejką");
		lblQueueMainFiles.setBounds(10, 21, 219, 14);

		//TEXT FIELD
		textFieldBrowseFiles = new JTextField();
		textFieldBrowseFiles.setBounds(181, 68, 389, 20);
		textFieldBrowseFiles.setColumns(10);
		textFieldBrowseFiles.setText(DBData.pathInitBrowseFiles);
		textFieldSendFilesMailReceiver = new JTextField();
		textFieldSendFilesMailReceiver.setText(DBData.pathInitSendMailReceiver);
		textFieldSendFilesMailReceiver.setBounds(80, 45, 352, 20);
		textFieldSendFilesMailFile = new JTextField();
		textFieldSendFilesMailFile.setBounds(80, 75, 352, 20);
		textFieldSendFilesMailFile.setText(DBData.pathInitQueueNewFile);
		textFieldSendZipFile = new JTextField(DBData.pathInitBrowseFiles);
		textFieldSendZipFile.setBounds(80, 44, 352, 20);
		textFieldSendZipFile.setColumns(10);
		textFieldSendFilesMailSender = new JTextField();
		textFieldSendFilesMailSender.setText("Editing not enabled");
		textFieldSendFilesMailSender.setBounds(80, 15, 352, 20);
		textFieldSendFilesMailSender.setEnabled(false);
		textFieldSendFilesMailSender.setText(DBData.pathInitSendMailSender);
		textFieldQueueMainFiles = new JTextField();
		textFieldQueueMainFiles.setBounds(197, 18, 346, 20);
		textFieldQueueMainFiles.setColumns(10);
		textFieldQueueMainFiles.setText(DBData.pathInitQueueExistingFile);

		//TEXTAREA		
		textAreaSendFilesInfo = new JTextArea();
		textAreaSendFilesInfo.setFont(new Font("Arial", Font.PLAIN, 11));
		textAreaSendFilesInfo.setColumns(2);
		textAreaSendFilesInfo.setRows(1);
		textAreaSendFilesInfo.setEditable(false);
		textAreaQueueInfo = new JTextArea();
		textAreaBrowseInfo = new JTextArea();
		textAreaBrowseInfo.setFont(new Font("Arial", Font.PLAIN, 11));
		textAreaBrowseInfo.setWrapStyleWord(true);
		textAreaBrowseInfo.setText(DefineUtils.BRWOSE_DESCRIPTION);
		textAreaBrowseInfo.setRows(2);
		textAreaBrowseInfo.setLineWrap(true);
		textAreaBrowseInfo.setEditable(false);
		textAreaQueueInfo.setFont(new Font("Arial", Font.PLAIN, 11));
		textAreaQueueInfo.setWrapStyleWord(true);
		textAreaQueueInfo.setText(DefineUtils.QUEUE_DESCRIPTION);
		textAreaQueueInfo.setRows(2);
		textAreaQueueInfo.setLineWrap(true);
		textAreaQueueInfo.setEditable(false);
		textAreaSendInfo = new JTextArea();
		textAreaSendInfo.setFont(new Font("Arial", Font.PLAIN, 11));
		textAreaSendInfo.setEditable(false);
		textAreaSendInfo.setRows(2);
		textAreaSendInfo.setLineWrap(true);
		textAreaSendInfo.setWrapStyleWord(true);
		textAreaSendInfo.setText(DefineUtils.SEND_DESRIPTION);

		//BUTTON		
		btnBrowseExit = new JButton("Zamknij");
		btnBrowseExit.setBounds(577, 459, 109, 20);
		btnBrowseUndoDelete = new JButton("Cofnij");
		btnBrowseUndoDelete.setBounds(577, 151, 109, 20);
		btnBrowseUndoDelete.setEnabled(false);
		btnBrowseBrowseFiles = new JButton("Przeglądaj");
		btnBrowseBrowseFiles.setBounds(577, 68, 109, 20);
		btnBrowseDeleteEntry = new JButton("Usuń wpis");
		btnBrowseDeleteEntry.setBounds(577, 120, 109, 20);
		btnSendExit = new JButton("Zamknij");
		btnSendExit.setBounds(577, 462, 109, 20);
		btnSendZipZip = new JButton("Spakuj");
		btnSendZipZip.setBounds(561, 44, 109, 20);
		btnSendFilesMailSend = new JButton("Wyślij");
		btnSendFilesMailSend.setBounds(561, 75, 109, 20);
		btnSendFilesMailBrowse = new JButton("Przeglądaj");
		btnSendFilesMailBrowse.setBounds(442, 75, 109, 20);
		btnSendZipBrowse = new JButton("Przeglądaj");
		btnSendZipBrowse.setBounds(442, 44, 109, 20);
		btnQueueMainFiles = new JButton("Przeglądaj");
		btnQueueMainFiles.setBounds(553, 17, 109, 20);
		btnQueueMainSaveChanges = new JButton("Zapisz");
		btnQueueMainSaveChanges.setBounds(553, 265, 109, 20);
		btnQueueMainCreateNewFile = new JButton("Utwórz nowy");
		btnQueueMainCreateNewFile.setBounds(553, 64, 109, 20);
		btnQueueMainRejectChanges = new JButton("Odrzuć");
		btnQueueMainRejectChanges.setBounds(553, 332, 109, 20);
		btnQueueMainAddVisit = new JButton("Dodaj wizytę");
		btnQueueMainAddVisit.setBounds(553, 131, 109, 20);
		btnQueueMainAddDate = new JButton("Dodaj termin");
		btnQueueMainAddDate.setBounds(553, 198, 109, 20);
		btnQueueExit = new JButton("Zamknij");
		btnQueueExit.setBounds(577, 459, 109, 20);

		btnBrowseBrowseFiles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileManager fileManager = new FileManager(textFieldBrowseFiles);
				dataLoader = new DataLoader(fileManager.browseDirectory(), tableModel);
				refreshTable();
			}
		});
		btnSendZipBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileManager fileManager = new FileManager(textFieldSendZipFile);
				fileManager.browseDirectory();
			}
		});
		btnSendFilesMailBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileManager fileManager = new FileManager(textFieldSendFilesMailFile);
				fileManager.browseFile();
			}
		});

		btnBrowseDeleteEntry.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (dataLoader.deleteFile(deleteFile)) {
					refreshTable();
					btnBrowseUndoDelete.setEnabled(true);
					CallTrace.log("#delete:" + deleteFile);
				}
			}
		});

		btnBrowseExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});

		btnSendExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});

		btnQueueExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});
		btnBrowseUndoDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (dataLoader != null) {
					dataLoader.undoDeleteFile();
					refreshTable();
					btnBrowseUndoDelete.setEnabled(false);
				}
			}
		});
		btnSendZipZip.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DataCompression dataCompression = new DataCompression(dataLoader.getFileList(),
						textFieldSendZipFile.getText(), textFieldBrowseFiles.getText());
				dataCompression.zipFiles();
				textFieldSendFilesMailFile.setText(dataCompression.shareZipPath());
			}
		});
		btnQueueMainFiles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileManager fileManager = new FileManager(textFieldQueueMainFiles);
				editorPaneQueueMainFileContent.setCaretPosition(0);
				editorPaneQueueMainFileContent.setText("");
				queueManager = new QueueManager(editorPaneQueueMainFileContent, fileManager.browseFile(),
						textFieldQueueMainFiles);
				queueManager.parseFile();
			}
		});
		btnSendFilesMailSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MailManager mailManager = new MailManager(frame, textFieldSendFilesMailSender.getText(),
						textFieldSendFilesMailReceiver.getText(), textFieldSendFilesMailFile.getText(), dataLoader.ewusMailTopic());
				mailManager.sendMail();
			}
		});
		btnQueueMainSaveChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (queueManager != null) {
					queueManager.saveChanges();
				}
			}
		});
		btnQueueMainCreateNewFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (queueManager != null) {
					queueManager.createNewFile();
				} else {
					FileManager fileManager = new FileManager(textFieldQueueMainFiles);
					queueManager = new QueueManager(editorPaneQueueMainFileContent, fileManager.browseDirectory(),
							textFieldQueueMainFiles);
					queueManager.parseFile();
				}
			}
		});
		btnQueueMainAddDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (queueManager != null) {
					queueManager.addDate();
				}
			}
		});
		btnQueueMainRejectChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (queueManager != null) {
					queueManager.rejectChanges();
				}
				editorPaneQueueMainFileContent.repaint();
			}
		});
		btnQueueMainAddVisit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (queueManager != null) {
					setEnabled(false);
					queueManager.addVisit();
					setEnabled(true);
				}
			}
		});

		//TABLE
		tableModel = new DefaultTableModel();
		tableBrowseMain = new JTable(tableModel);
		tableBrowseMain.setDefaultRenderer(String.class, new TableCellRender());
		tableBrowseMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String[] columnNames = { "Lp.", "Data", "Nazwisko", "Imię", "PESEL", "Plik" };
		for (int i = 0; i < columnNames.length; i++) {
			tableBrowseMain.addColumn(new TableColumn(i));
		}
		for (int i = 0; i < columnNames.length; i++) {
			tableModel.addColumn(columnNames[i]);
		}
		tableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				try {
					String begin = tableModel.getValueAt(0, 1).toString();
					String end = tableModel.getValueAt(tableModel.getRowCount() - 1, 1).toString();
					TitledBorder tb = (TitledBorder) panelBrowseMain.getBorder();
					tb.setTitle("Potwierdzenia za okres od " + begin + " do " + end);
				} catch (Exception ex) {
				}
			}
		});
		tableBrowseMain.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					try {
						deleteFile = tableBrowseMain.getValueAt(tableBrowseMain.getSelectedRow(), 5).toString();
					} catch (Exception ex) {
					}
				}
			}
		});

		//EDITORPANE
		editorPaneQueueMainFileContent = new JEditorPane();
		editorPaneQueueMainFileContent.setLocale(new Locale("pl", "PL"));
		editorPaneQueueMainFileContent.setEditable(false);

		//SCROLLPANE	
		scrollPaneBrowseMain = new JScrollPane();
		scrollPaneBrowseMain.setLocation(10, 21);
		scrollPaneBrowseMain.setSize(545, 348);
		scrollPaneBrowseMain.setViewportView(tableBrowseMain);
		scrollPaneSendFilesInfo = new JScrollPane();
		scrollPaneSendFilesInfo.setBounds(6, 16, 664, 68);
		scrollPaneSendFilesInfo.setViewportView(textAreaSendFilesInfo);
		scrollPaneQueueMainFileContent = new JScrollPane();
		scrollPaneQueueMainFileContent.setBounds(10, 46, 533, 325);
		scrollPaneBrowseInfo = new JScrollPane();
		scrollPaneBrowseInfo.setBorder(null);
		scrollPaneBrowseInfo.setBounds(10, 11, 676, 44);
		scrollPaneBrowseInfo.setViewportView(textAreaBrowseInfo);
		scrollPaneSendInfo = new JScrollPane();
		scrollPaneSendInfo.setBorder(null);
		scrollPaneSendInfo.setBounds(10, 7, 676, 44);
		scrollPaneSendInfo.setViewportView(textAreaSendInfo);
		scrollPaneQueueInfo = new JScrollPane();
		scrollPaneQueueInfo.setBorder(null);
		scrollPaneQueueInfo.setBounds(10, 11, 676, 44);
		scrollPaneQueueInfo.setViewportView(textAreaQueueInfo);
		scrollPaneQueueMainFileContent.setViewportView(editorPaneQueueMainFileContent);

		//PANEL
		panelSendConsoleQueueFile = new JPanel();
		panelSendConsoleQueueFile.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Wysy\u0142anie pliku z kolejk\u0105 pacjent\u00F3w", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSendConsoleQueueFile.setBounds(10, 347, 680, 108);
		panelSendConsoleQueueFile.setLayout(null);
		panelQueueMain = new JPanel();
		panelQueueMain.setBorder(new TitledBorder(null, "Zarz\u0105dzanie zapisami", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelQueueMain.setBounds(10, 66, 676, 382);
		panelQueueMain.setLayout(null);
		panelQueueMain.add(lblQueueMainFiles);

		panelQueueMain.add(textFieldQueueMainFiles);
		panelQueueMain.add(btnQueueMainFiles);
		panelQueueMain.add(scrollPaneQueueMainFileContent);
		panelQueueMain.add(btnQueueMainSaveChanges);
		panelQueueMain.add(btnQueueMainCreateNewFile);
		panelQueueMain.add(btnQueueMainRejectChanges);
		panelQueueMain.add(btnQueueMainAddDate);
		panelQueueMain.add(btnQueueMainAddVisit);
		panelSendMail = new JPanel();
		panelSendMail.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Wysy\u0142anie pliku z potwierdzeniami", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSendMail.setBounds(10, 237, 680, 103);
		panelSendMail.setLayout(null);
		panelSendMail.add(textFieldSendFilesMailReceiver);
		panelSendMail.add(lblSendFilesMailReceiver);
		panelSendMail.add(btnSendFilesMailSend);
		panelSendMail.add(lblSendFilesMailFile);
		panelSendMail.add(textFieldSendFilesMailFile);
		panelSendMail.add(btnSendFilesMailBrowse);
		panelSendMail.add(lblSendFilesMailSender);
		panelSendMail.add(textFieldSendFilesMailSender);
		panelSendZip = new JPanel();
		panelSendZip.setBorder(new TitledBorder(null, "Pakowanie potwierdze\u0144", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelSendZip.setBounds(10, 155, 680, 75);
		panelSendZip.setLayout(null);
		panelSendZip.add(lblSendZipFile);
		panelSendZip.add(lblSendZipFileQ);
		panelSendZip.add(btnSendZipZip);
		panelSendZip.add(textFieldSendZipFile);
		panelSendZip.add(btnSendZipBrowse);
		panelSendFilesInfo = new JPanel();
		panelSendFilesInfo.setBorder(new TitledBorder(null, "Informacje o potwierdzeniach", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelSendFilesInfo.setBounds(10, 58, 680, 90);
		panelSendFilesInfo.setLayout(null);
		panelSendFilesInfo.add(scrollPaneSendFilesInfo);
		panelBrowseMain = new JPanel();
		panelBrowseMain.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBrowseMain.setBounds(5, 99, 565, 380);
		panelBrowseMain.setLayout(null);
		panelBrowseMain.add(scrollPaneBrowseMain);
		panelBrowse = new JPanel();
		panelBrowse.setLayout(null);
		panelBrowse.add(textFieldBrowseFiles);
		panelBrowse.add(btnBrowseBrowseFiles);
		panelBrowse.add(btnBrowseDeleteEntry);
		panelBrowse.add(btnBrowseUndoDelete);
		panelBrowse.add(btnBrowseExit);
		panelBrowse.add(lblBrowseFiles);
		panelBrowse.add(panelBrowseMain);
		panelBrowse.add(scrollPaneBrowseInfo);
		panelSend = new JPanel();
		panelSend.setLayout(null);
		panelSend.add(panelSendConsoleQueueFile);
		
		JLabel lblSendQueueMailSender = new JLabel("Nadawca");
		lblSendQueueMailSender.setBounds(10, 23, 73, 14);
		panelSendConsoleQueueFile.add(lblSendQueueMailSender);
		
		textFieldSendQueueMailSender = new JTextField(DBData.pathInitSendMailSender);
		textFieldSendQueueMailSender.setEnabled(false);
		textFieldSendQueueMailSender.setEditable(false);
		textFieldSendQueueMailSender.setBounds(80, 18, 352, 20);
		panelSendConsoleQueueFile.add(textFieldSendQueueMailSender);
		textFieldSendQueueMailSender.setColumns(10);
		
		JLabel lblSendQueueMailReceiver = new JLabel("Odbiorca");
		lblSendQueueMailReceiver.setBounds(10, 52, 73, 14);
		panelSendConsoleQueueFile.add(lblSendQueueMailReceiver);
		
		textFieldSendQueueMailReceiver = new JTextField(DBData.pathInitSendMailReceiver);
		textFieldSendQueueMailReceiver.setBounds(80, 48, 352, 20);
		panelSendConsoleQueueFile.add(textFieldSendQueueMailReceiver);
		textFieldSendQueueMailReceiver.setColumns(10);
		
		JLabel lblSendQueueMailFile = new JLabel("Ścieżka");
		lblSendQueueMailFile.setBounds(10, 81, 73, 14);
		panelSendConsoleQueueFile.add(lblSendQueueMailFile);
		
		textFieldSendQueueMailFile = new JTextField(DBData.pathInitQueueExistingFile);
		textFieldSendQueueMailFile.setBounds(80, 78, 352, 20);
		panelSendConsoleQueueFile.add(textFieldSendQueueMailFile);
		textFieldSendQueueMailFile.setColumns(10);
		
		JButton btnSendQueueMailSend = new JButton("Wyślij");
		btnSendQueueMailSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MailManager mailManager = new MailManager(frame, textFieldSendFilesMailSender.getText(),
						textFieldSendFilesMailReceiver.getText(), textFieldSendQueueMailFile.getText(), "Kolejka " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				mailManager.sendMail();
			}
		});
		btnSendQueueMailSend.setBounds(561, 78, 109, 20);
		panelSendConsoleQueueFile.add(btnSendQueueMailSend);
		
		JButton btnSendQueueMailBrowse = new JButton("Przeglądaj");
		btnSendQueueMailBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileManager fileManager = new FileManager(textFieldSendQueueMailFile);
				fileManager.browseFile();
			}
		});
		btnSendQueueMailBrowse.setBounds(442, 78, 109, 20);
		panelSendConsoleQueueFile.add(btnSendQueueMailBrowse);
		panelSend.add(panelSendFilesInfo);
		panelSend.add(panelSendMail);
		panelSend.add(btnSendExit);
		panelSend.add(panelSendZip);
		panelSend.add(scrollPaneSendInfo);
		panelQueue = new JPanel();
		panelQueue.setLayout(null);
		panelQueue.add(scrollPaneQueueInfo);
		panelQueue.add(panelQueueMain);
		panelQueue.add(btnQueueExit);

		//PANE
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Kolejka", null, panelQueue, null);
		tabbedPane.addTab("Potwierdzenia", null, panelBrowse, null);
		tabbedPane.addTab("Wysyłanie", null, panelSend, null);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		//FRAME
		setVisible(true);
		setBounds(100, 100, 711, 547);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		fillParts();
	}

	private void fillParts() {
		if(!textFieldBrowseFiles.getText().equals("")){
			dataLoader = new DataLoader(textFieldBrowseFiles.getText(), tableModel);
			refreshTable();
		}
		if(!textFieldQueueMainFiles.getText().equals("")){
			queueManager = new QueueManager(editorPaneQueueMainFileContent, textFieldQueueMainFiles.getText(),
					textFieldQueueMainFiles);
			queueManager.parseFile();
		}

	}

	private void refreshTable() {
		try {
			((DefaultTableModel) tableBrowseMain.getModel()).getDataVector().removeAllElements();
			tableBrowseMain.repaint();
			if (dataLoader != null) {
				dataLoader.fillTable();
				textAreaSendFilesInfo.setText(dataLoader.provideInfo());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
