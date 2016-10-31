package com.manager.logic;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.manager.newgui.QueueTableModel;

public class QueueFileParser {

	private String filePath;
	private JTable queueTable;

	public QueueFileParser(String filePath, JTable queueTable) {
		this.filePath = filePath;
		this.queueTable = queueTable;
	}

	public void fillTable() {
		String msg;
		ArrayList<ArrayList<String>> dataList = new ArrayList<>();

		try {
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(filePath), "UTF-8"));

			int iter = 0;

			while ((msg = bufferedReader.readLine()) != null) {

				if (iter > 0 && !msg.contains("=")) {
					String[] parts;
					if (msg.contains("Pierwszy")) {
						((QueueTableModel) queueTable.getModel()).setRowColour(0, new Color(112, 146, 190));
						parts = msg.split(" ");
					} else {
						parts = msg.split("\t");
					}

					ArrayList<String> tmp = new ArrayList<>();

					for (String part : parts) {
						tmp.add(part);
					}

					dataList.add(tmp);
				}

				iter++;
			}

			try {
				for (int i = 0; i < dataList.size(); i++) {
					Vector<Object> data = new Vector<Object>();
					for (String tmp : dataList.get(i)) {
						data.add(tmp);
					}

					((DefaultTableModel) queueTable.getModel()).addRow(data);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} catch (IOException e) {
		}

	}
}
