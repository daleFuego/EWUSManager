package com.model.table;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class TableModel  extends AbstractTableModel{

	private Object[][] values;

	public TableModel(int numberOfRows, int numberOfColumns) {
		values = new Object[numberOfRows][numberOfColumns];
	}

	@Override
	public int getRowCount() {
		return values.length;
	}

	@Override
	public int getColumnCount() {
		return values[0].length;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Integer.class;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return values[rowIndex][columnIndex];
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		values[rowIndex][columnIndex] = aValue.toString();
		fireTableCellUpdated(rowIndex, columnIndex);
	}
}