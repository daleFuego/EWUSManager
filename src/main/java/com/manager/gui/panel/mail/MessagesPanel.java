package com.manager.gui.panel.mail;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.manager.gui.ProgressDialog;
import com.manager.utils.DefineUtils;
import com.manager.utils.EmailSession;
import com.manager.utils.EncryptionUtils;

@SuppressWarnings("serial")
public class MessagesPanel extends JPanel {

	@SuppressWarnings("rawtypes")
	private JList listInbox;
	private JTextArea textAreaDecryptedMessage;
	private JTextArea textAreaOrginalMessage;
	@SuppressWarnings("rawtypes")
	private JList listOutbox;
	private Task task;
	private ProgressDialog progessDialog;
	private EncryptionPanel encryptionPanel;
	private DataHandler selectedAttachement;

	@SuppressWarnings({ "rawtypes" })
	public MessagesPanel() {
		setLayout(null);

		JPanel panelText = new JPanel();
		panelText.setBorder(
				new TitledBorder(null, "Wiadomo\u015Bci", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelText.setBounds(275, 0, 541, 581);
		add(panelText);
		panelText.setLayout(null);

		JPanel panelOrginalMessage = new JPanel();
		panelOrginalMessage.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Orginalna wiadomo\u015B\u0107", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelOrginalMessage.setBounds(0, 16, 541, 223);
		panelText.add(panelOrginalMessage);
		panelOrginalMessage.setLayout(null);

		JScrollPane scrollPaneOrginalMessage = new JScrollPane();
		scrollPaneOrginalMessage.setBounds(6, 16, 529, 200);
		panelOrginalMessage.add(scrollPaneOrginalMessage);

		textAreaOrginalMessage = new JTextArea();
		textAreaOrginalMessage.setEditable(false);
		textAreaOrginalMessage.setFont(DefineUtils.FONT);
		scrollPaneOrginalMessage.setViewportView(textAreaOrginalMessage);

		JPanel panelDecryptedMessage = new JPanel();
		panelDecryptedMessage.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Deszyfrowana wiadomo\u015B\u0107", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDecryptedMessage.setBounds(0, 353, 541, 223);
		panelText.add(panelDecryptedMessage);
		panelDecryptedMessage.setLayout(null);

		JScrollPane scrollPaneDecryptedMessage = new JScrollPane();
		scrollPaneDecryptedMessage.setBounds(6, 16, 529, 200);
		panelDecryptedMessage.add(scrollPaneDecryptedMessage);

		textAreaDecryptedMessage = new JTextArea();
		textAreaDecryptedMessage.setEditable(false);
		textAreaDecryptedMessage.setFont(DefineUtils.FONT);
		scrollPaneDecryptedMessage.setViewportView(textAreaDecryptedMessage);

		encryptionPanel = new EncryptionPanel(false, "", "");
		encryptionPanel.setBounds(6, 248, 529, 96);
		encryptionPanel.setVisible(true);
		encryptionPanel.getButtonEncryptOptions().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EncryptionUtils.getInstance().decryptMessage(selectedAttachement, "",
						Paths.get(encryptionPanel.getTextFieldPrivateKeyPath().getText()));

			}
		});
		panelText.add(encryptionPanel);

		JTabbedPane tabbedPaneMailBox = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneMailBox.setBounds(12, 0, 253, 581);
		tabbedPaneMailBox.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				updateControls(tabbedPaneMailBox.getSelectedIndex() == 0 ? true : false, false);
			}
		});
		add(tabbedPaneMailBox);

		JPanel panelInbox = new JPanel();
		panelInbox.setBorder(null);
		tabbedPaneMailBox.addTab("Odebrane", null, panelInbox, null);
		panelInbox.setLayout(null);

		JScrollPane scrollPaneInbox = new JScrollPane();
		scrollPaneInbox.setBounds(6, 2, 235, 549);
		panelInbox.add(scrollPaneInbox);

		listInbox = new JList();
		listInbox.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		scrollPaneInbox.setViewportView(listInbox);

		JPanel panelOutbox = new JPanel();
		tabbedPaneMailBox.addTab("Wysłane", null, panelOutbox, null);
		panelOutbox.setLayout(null);

		JScrollPane scrollPaneOutbox = new JScrollPane();
		scrollPaneOutbox.setBounds(6, 2, 235, 549);
		panelOutbox.add(scrollPaneOutbox);

		listOutbox = new JList();
		listOutbox.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPaneOutbox.setViewportView(listOutbox);

		configureMailbox();
	}

	private void configureMailbox() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				task = new Task();
				progessDialog = new ProgressDialog("Pobieram wiadomości...");
				progessDialog.setVisible(true);
				progessDialog.setSize(300, 50);

				task.execute();
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public JList getListInbox() {
		return listInbox;
	}

	private void setOrginalInboxMessage(JTextArea textAreaOrginalMessage, Message selectedValue) {
		String from = "", date = "", subject = "", attachement = "", content = "";
		try {
			from = "Od: \t" + Arrays.toString(selectedValue.getFrom()) + "\n";
			date = "Otrzymano: \t" + selectedValue.getReceivedDate() + "\n";
			subject = "Temat: \t" + selectedValue.getSubject() + "\n";
			attachement = "Załącznik: \t";
			content = "\n\nTreść wiadomości:\n--------------------------------------------------------------------------------\n";

			if (selectedValue.getContent() instanceof Multipart) {
				Multipart multipart = (Multipart) selectedValue.getContent();

				for (int j = 0; j < multipart.getCount(); j++) {
					BodyPart bodyPart = multipart.getBodyPart(j);
					String disposition = bodyPart.getDisposition();

					if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) {
						selectedAttachement = bodyPart.getDataHandler();
						attachement += selectedAttachement.getName() + " ";
					} else if (bodyPart != null && bodyPart.isMimeType("text/plain")) {
						content += bodyPart.getContent().toString();
						selectedAttachement = null;
					}
				}
			}
			attachement += "\n";
		} catch (Exception e) {
			e.printStackTrace();
		}

		String formattedMessage = from + date + subject + attachement + content;
		textAreaOrginalMessage.setText(formattedMessage);
	}

	private void setOrginalOutboxMessage(JTextArea textAreaOrginalMessage, Message selectedValue) {
		String to = "", date = "", subject = "", attachement = "", content = "";
		try {
			to = "Do: \t" + Arrays.toString(selectedValue.getAllRecipients()) + "\n";
			date = "Wysłano: \t" + selectedValue.getSentDate() + "\n";
			subject = "Temat: \t" + selectedValue.getSubject() + "\n";
			attachement = "Załącznik: \t";
			content = "\n\nTreść wiadomości:\n--------------------------------------------------------------------------------\n";

			if (selectedValue.getContent() instanceof Multipart) {
				Multipart multipart = (Multipart) selectedValue.getContent();

				for (int j = 0; j < multipart.getCount(); j++) {
					BodyPart bodyPart = multipart.getBodyPart(j);

					if (bodyPart != null && bodyPart.isMimeType("text/plain")) {
						content += bodyPart.getContent().toString();
					}
				}
			}
			attachement += "\n";
		} catch (Exception e) {
			e.printStackTrace();
		}

		String formattedMessage = to + date + subject + attachement + content;
		textAreaOrginalMessage.setText(formattedMessage);
	}

	class MyCellRendererInbox extends DefaultListCellRenderer {

		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean iss, boolean chf) {
			super.getListCellRendererComponent(list, value, index, iss, chf);
			try {
				Message entry = (Message) value;
				ImageIcon fillingIcon = new ImageIcon(super.getClass().getClassLoader().getResource("mail_in.png"));
				setIcon(fillingIcon);
				setText(entry.getSubject());

			} catch (Exception e) {
				e.printStackTrace();
			}

			return this;
		}

	}

	class MyCellRendererOutbox extends DefaultListCellRenderer {

		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean iss, boolean chf) {
			super.getListCellRendererComponent(list, value, index, iss, chf);
			try {
				Message entry = (Message) value;
				ImageIcon fillingIcon = new ImageIcon(super.getClass().getClassLoader().getResource("mail_out.png"));
				setIcon(fillingIcon);
				setText(entry.getSubject());
			} catch (Exception e) {
				e.printStackTrace();
			}

			return this;
		}

	}

	class Task extends SwingWorker<Void, Void> {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Void doInBackground() {
			task.setProgress(0);

			try {
				listInbox.setFont(DefineUtils.FONT);
				listInbox.setCellRenderer(new MyCellRendererInbox());
				listInbox.setFixedCellHeight(32);
				listOutbox.setFont(DefineUtils.FONT);
				listOutbox.setCellRenderer(new MyCellRendererOutbox());
				listOutbox.setFixedCellHeight(32);

				MouseListener mouseListenerInbox = new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 1) {
							setOrginalInboxMessage(textAreaOrginalMessage, (Message) listInbox.getSelectedValue());
							textAreaOrginalMessage.setCaretPosition(0);

							updateControls(true, checkAttachement());
						}
					}
				};

				MouseListener mouseListenerOutbox = new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 1) {
							setOrginalOutboxMessage(textAreaOrginalMessage, (Message) listOutbox.getSelectedValue());
							textAreaOrginalMessage.setCaretPosition(0);

							updateControls(false, checkAttachement());
						}
					}
				};

				listInbox.addMouseListener(mouseListenerInbox);
				listOutbox.addMouseListener(mouseListenerOutbox);

				try {
					DefaultListModel listModelInbox = new DefaultListModel();
					Folder emailFolder = EmailSession.getInstance().getStore().getFolder("INBOX");
					emailFolder.open(Folder.READ_ONLY);

					Message[] messages = emailFolder.getMessages();

					for (int i = 0; i < DefineUtils.INBOX_SIZE; i++) {
						try {
							listModelInbox.addElement(messages[messages.length - i - 1]);
							long progress = ((100 * i) / (DefineUtils.INBOX_SIZE + DefineUtils.OUTBOX_SIZE));
							progessDialog.progressBarProcessingTask.setValue((int) progress);
							progessDialog.progressBarProcessingTask.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					listInbox.setModel(listModelInbox);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					DefaultListModel listModelOutbox = new DefaultListModel();
					Folder emailFolder = EmailSession.getInstance().getStore().getFolder("[Gmail]/Wysłane");
					emailFolder.open(Folder.READ_ONLY);

					Message[] messages = emailFolder.getMessages();

					for (int i = 0; i < DefineUtils.OUTBOX_SIZE; i++) {
						try {
							listModelOutbox.addElement(messages[messages.length - i - 1]);
							long progress = ((100 * (i + DefineUtils.INBOX_SIZE))
									/ (DefineUtils.INBOX_SIZE + DefineUtils.OUTBOX_SIZE));
							progessDialog.progressBarProcessingTask.setValue((int) progress);
							progessDialog.progressBarProcessingTask.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					listOutbox.setModel(listModelOutbox);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		public void done() {
			progessDialog.dispose();
		}
	}

	private void updateControls(boolean value, boolean isValidAttachement) {
		encryptionPanel.getTextFieldPrivateKeyPath().setEnabled(value);
		encryptionPanel.getButtonEncryptOptions().setEnabled(isValidAttachement);
		encryptionPanel.getTextFieldPublicKeyPath().setEnabled(value);
		encryptionPanel.getButtonLoadPublicKey().setEnabled(value);
		encryptionPanel.getButtonLoadPrivateKey().setEnabled(value);
		encryptionPanel.getButtonGenerateKeys().setEnabled(value);
		if (!value) {
			textAreaDecryptedMessage.setText("");
		}
		textAreaDecryptedMessage.setEnabled(value);
	}

	public EncryptionPanel getEncryptionPanel() {
		return encryptionPanel;
	}

	private boolean checkAttachement() {
		if (selectedAttachement != null && selectedAttachement.getName().contains("_encrypted")) {
			return true;
		}
		return false;
	}
}
