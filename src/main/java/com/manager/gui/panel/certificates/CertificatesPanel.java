package com.manager.gui.panel.certificates;

import java.awt.Color;
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
import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class CertificatesPanel extends JPanel {
	private JTextField textFieldFilePath;
	private CertificatesTable certificatesTable;
	private JTextArea textAreaFileDetails;
	private JTextArea panelSendDescription;
	private JButton buttonUndo;

	public CertificatesPanel(JTextArea panelSendDescription) {
		setLayout(null);

		this.panelSendDescription = panelSendDescription;

		certificatesTable = new CertificatesTable("");
		certificatesTable.setBounds(6, 105, 553, 347);
		add(certificatesTable);

		JScrollPane scrollPaneDescription = new JScrollPane();
		scrollPaneDescription.setBounds(6, 7, 676, 44);
		add(scrollPaneDescription);

		JTextArea textAreaDescription = new JTextArea();
		scrollPaneDescription.setViewportView(textAreaDescription);
		textAreaDescription.setWrapStyleWord(true);
		textAreaDescription.setText(DefineUtils.CERTIFICATES_DESCRIPTION);
		textAreaDescription.setRows(2);
		textAreaDescription.setLineWrap(true);
		textAreaDescription.setFont(DefineUtils.FONT);
		textAreaDescription.setEditable(false);

		JPanel panelControls = new JPanel();
		panelControls.setBounds(569, 105, 111, 343);
		add(panelControls);
		panelControls.setLayout(null);

		JButton buttonDelete = new JButton("Usuń wpis");
		buttonDelete.setFont(DefineUtils.FONT);
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (DataLoader.getInstance().deleteFile(certificatesTable.getDeleteFile())) {
					refreshTable();
					buttonUndo.setEnabled(true);
				}
			}
		});
		buttonDelete.setBounds(1, 281, 109, 20);
		panelControls.add(buttonDelete);

		buttonUndo = new JButton("Cofnij");
		buttonUndo.setFont(DefineUtils.FONT);
		buttonUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Fix this one day
				DataLoader.getInstance().undoDeleteFile();
				refreshTable();
				buttonUndo.setEnabled(false);
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
		textAreaFileDetails.setFont(DefineUtils.FONT);

		JPanel panelFilePath = new JPanel();
		panelFilePath.setBounds(6, 58, 674, 40);
		add(panelFilePath);
		panelFilePath.setLayout(null);

		JLabel labelFilePath = new JLabel("Podaj ścieżkę do potwierdzeń");
		labelFilePath.setFont(DefineUtils.FONT);
		labelFilePath.setBounds(8, 13, 185, 14);
		panelFilePath.add(labelFilePath);

		textFieldFilePath = new JTextField();
		textFieldFilePath.setBounds(192, 10, 363, 20);
		textFieldFilePath.setFont(DefineUtils.FONT);
		panelFilePath.add(textFieldFilePath);
		textFieldFilePath.setText(DBData.pathInitBrowseFiles);
		textFieldFilePath.setColumns(10);

		JButton buttonBrowse = new JButton("Przeglądaj");
		buttonBrowse.setFont(DefineUtils.FONT);
		buttonBrowse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				DataLoader.getInstance().setDirectory((new FileManager()).browseDirectory(DefineUtils.DB_pathbrowsefiles, "Pliki xml (*.xml)", "xml"));
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
			if (certificatesTable.getTable().getRowCount() > 0) {
				certificatesTable.getTable().setRowSelectionInterval(0, 0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public JTextField getTextFieldFilePath() {
		return textFieldFilePath;
	}

	public CertificatesTable getCertificatesTable() {
		return certificatesTable;
	}
}
