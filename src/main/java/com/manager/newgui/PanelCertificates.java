package com.manager.newgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.manager.dao.DBData;
import com.manager.data.DataLoader;
import com.manager.logic.FileManager;
import com.manager.utils.CallTrace;
import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class PanelCertificates extends JPanel {
	private JTextField textFieldFilePath;
	private DataLoader dataLoader;
	private PanellCertificatesTable certificatesTable;
	private JTextArea textAreaFileDetails;
	private JTextArea panelSendDescription;
	private JButton buttonUndo;

	public PanelCertificates(JTextArea panelSendDescription) {
		setLayout(null);

		this.panelSendDescription = panelSendDescription;

		certificatesTable = new PanellCertificatesTable("");
		certificatesTable.setBounds(6, 105, 553, 347);
		add(certificatesTable);

		JScrollPane scrollPaneDescription = new JScrollPane();
		scrollPaneDescription.setBounds(6, 7, 676, 44);
		add(scrollPaneDescription);

		JTextArea textAreaDescription = new JTextArea();
		scrollPaneDescription.setViewportView(textAreaDescription);
		textAreaDescription.setWrapStyleWord(true);
		textAreaDescription.setText(DefineUtils.BRWOSE_DESCRIPTION);
		textAreaDescription.setRows(2);
		textAreaDescription.setLineWrap(true);
		textAreaDescription.setFont(new Font("Arial", Font.PLAIN, 11));
		textAreaDescription.setEditable(false);

		JPanel panelControls = new JPanel();
		panelControls.setBounds(569, 105, 111, 343);
		add(panelControls);
		panelControls.setLayout(null);

		JButton buttonDelete = new JButton("Usuń wpis");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (dataLoader.deleteFile(certificatesTable.getDeleteFile())) {
					refreshTable();
					buttonUndo.setEnabled(true);
					CallTrace.log("#delete:" + certificatesTable.getDeleteFile());
				}
			}
		});
		buttonDelete.setBounds(1, 281, 109, 20);
		panelControls.add(buttonDelete);

		buttonUndo = new JButton("Cofnij");
		buttonUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Fix this one day
				if (dataLoader != null) {
					dataLoader.undoDeleteFile();
					refreshTable();
					buttonUndo.setEnabled(false);
				}
			}
		});
		buttonUndo.setBounds(1, 312, 109, 20);
		panelControls.add(buttonUndo);
		buttonUndo.setEnabled(false);

		JPanel panelFileDetails = new JPanel();
		panelFileDetails.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelFileDetails.setBounds(1, 11, 107, 125);
		panelControls.add(panelFileDetails);
		panelFileDetails.setLayout(null);

		textAreaFileDetails = new JTextArea();
		textAreaFileDetails.setBounds(3, 2, 100, 120);
		panelFileDetails.add(textAreaFileDetails);
		textAreaFileDetails.setEditable(false);
		textAreaFileDetails.setWrapStyleWord(true);
		textAreaFileDetails.setRows(2);
		textAreaFileDetails.setFont(new Font("Arial", Font.PLAIN, 11));

		JPanel panelFilePath = new JPanel();
		panelFilePath.setBounds(6, 58, 674, 40);
		add(panelFilePath);
		panelFilePath.setLayout(null);

		JLabel labelFilePath = new JLabel("Podaj ścieżkę do potwierdzeń");
		labelFilePath.setBounds(8, 13, 163, 14);
		panelFilePath.add(labelFilePath);

		textFieldFilePath = new JTextField();
		textFieldFilePath.setBounds(168, 10, 387, 20);
		panelFilePath.add(textFieldFilePath);
		textFieldFilePath.setText(DBData.pathInitBrowseFiles);
		textFieldFilePath.setColumns(10);

		JButton buttonBrowse = new JButton("Przeglądaj");
		buttonBrowse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				FileManager fileManager = new FileManager(textFieldFilePath);
				DataLoader.getInstance().setDirectory(fileManager.browseDirectory());
				DataLoader.getInstance().setTable((DefaultTableModel) certificatesTable.getTable().getModel());
				refreshTable();
			}
		});
		buttonBrowse.setBounds(565, 10, 109, 20);
		panelFilePath.add(buttonBrowse);

	}

	public void refreshTable() {
		try {
			((DefaultTableModel) certificatesTable.getTable().getModel()).getDataVector().removeAllElements();
			certificatesTable.getTable().repaint();
			DataLoader.getInstance().fillTable();
			textAreaFileDetails.setText(DataLoader.getInstance().provideInfo());
			panelSendDescription.setText(DataLoader.getInstance().provideInfoLong());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public JTextField getTextFieldFilePath() {
		return textFieldFilePath;
	}

	public PanellCertificatesTable getCertificatesTable() {
		return certificatesTable;
	}
}
