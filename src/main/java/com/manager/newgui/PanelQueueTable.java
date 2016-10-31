package com.manager.newgui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.manager.logic.QueueFileParser;

@SuppressWarnings("serial")
public class PanelQueueTable extends JPanel {

	private JTable table;
	private QueueTableModel model;

	public PanelQueueTable(String filePath) {
		setLayout(null);
		setVisible(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 533, 325);
		add(scrollPane);

		table = new JTable();

		model = new QueueTableModel();
		model.setColumnIdentifiers(new String[] { "Data zapisu", "Imię", "Nazwisko", "PESEL", "Data wizyty" });
		table.setModel(model);
		MyTableCellRenderer cellRenderer = new MyTableCellRenderer();
		for (int tableNumber = 0; tableNumber < table.getModel().getColumnCount(); tableNumber++) {
			table.getColumnModel().getColumn(tableNumber).setCellRenderer(cellRenderer);
		}

		scrollPane.setViewportView(table);

		QueueFileParser queueFileParser = new QueueFileParser(filePath, table);
		queueFileParser.fillTable();
	}

	class MyTableCellRenderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			QueueTableModel model = (QueueTableModel) table.getModel();
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			String s = (String) table.getValueAt(row, 0);
			if (s.contains("Pierwszy")) {
				c.setBackground(model.getRowColour(row));
			} else if (!s.contains("Pierwszy") && !isSelected) {
				c.setBackground(Color.WHITE);
			}
			return c;
		}
	}

	public JTable getTable() {
		return table;
	}
}
