package com.model.table;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class TableCellRender extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus,
			int row, int column) {
		@SuppressWarnings("unused")
		Component c = super.getTableCellRendererComponent(table, "", true, false, row, column);
		
		return this;
	}
}