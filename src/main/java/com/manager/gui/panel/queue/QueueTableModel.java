package com.manager.gui.panel.queue;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class QueueTableModel extends DefaultTableModel {

	List<Color> rowColours = Arrays.asList(Color.RED, Color.GREEN, Color.CYAN);

	public void setRowColour(int row, Color c) {
		rowColours.set(row, c);
	}

	public Color getRowColour(int row) {
		return rowColours.get(0);
	}

}