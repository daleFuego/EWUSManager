package com.manager.gui.panel.certificates;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class CertificatesTable extends JPanel {

	private JTable table;
	private DefaultTableModel model;
	private String deleteFile;

	public CertificatesTable(String filePath) {
		setLayout(null);
		setVisible(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 533, 325);
		add(scrollPane);

		table = new JTable();

		model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] { "Lp.", "Data", "Nazwisko", "Imię", "PESEL", "Plik" });
		table.setModel(model);

		scrollPane.setViewportView(table);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					try {
						deleteFile = table.getValueAt(table.getSelectedRow(), 5).toString();
					} catch (Exception ex) {
					}
				}
			}
		});
	}

	public JTable getTable() {
		return table;
	}

	public String getDeleteFile() {
		return deleteFile;
	}
}
