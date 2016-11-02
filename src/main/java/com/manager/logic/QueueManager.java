package com.manager.logic;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.manager.dao.DBData;
import com.manager.gui.panel.queue.DateFrame;
import com.manager.gui.panel.queue.VisitDialog;
import com.manager.gui.panel.queue.QueueTableModel;
import com.manager.utils.DefineUtils;

public class QueueManager {

	private String filePath;
	private VisitDialog addVisit;
	private DateFrame addDate;
	private JTextField textField;
	private JTable queueTable;
	private String tmpFileName;
	private String tmpText;

	public QueueManager(String filePath, JTextField textFieldQueueMainFiles, JTable queueTable) {
		this.filePath = filePath;
		this.textField = textFieldQueueMainFiles;
		this.queueTable = queueTable;

		if (filePath != null) {
			textField.setText(filePath);
		}

		tmpFileName = "tmp.txt";
	}

	public void parseFile(boolean useTmpFile) {
		String msg;
		ArrayList<ArrayList<String>> dataList = new ArrayList<>();
		ArrayList<String> partsData = new ArrayList<>();
		BufferedReader bufferedReader;
		int index = 0;
		tmpText = "";

		try {
			if (useTmpFile) {
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(tmpFileName), "UTF-8"));
			} else {
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			}

			while ((msg = bufferedReader.readLine()) != null) {
				tmpText += msg + System.lineSeparator();
				if (index > 0 && !msg.contains("=")) {
					String[] parts;
					if (msg.contains("Pierwszy")) {
						((QueueTableModel) queueTable.getModel()).setRowColour(0, new Color(112, 146, 190));
						parts = msg.split(" ");
					} else {
						parts = msg.split("\t");
					}

					partsData = new ArrayList<>();

					for (String part : parts) {
						partsData.add(part);
					}

					dataList.add(partsData);
				}

				index++;
			}

			fillTable(dataList);

		} catch (IOException e) {
		}
	}

	private void fillTable(ArrayList<ArrayList<String>> dataList) {
		try {
			for (int i = 0; i < dataList.size(); i++) {
				Vector<Object> data = new Vector<Object>();

				for (String tmp : dataList.get(i)) {
					data.add(tmp);
				}

				((DefaultTableModel) queueTable.getModel()).addRow(data);
			}

			if (queueTable.getRowCount() > 1) {
				queueTable.scrollRectToVisible(queueTable.getCellRect(queueTable.getRowCount() - 1, 0, true));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void refreshTable(boolean useTmpFile) {
		try {
			((DefaultTableModel) queueTable.getModel()).getDataVector().removeAllElements();
			queueTable.repaint();
			parseFile(useTmpFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void saveChanges() {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));

			bufferedWriter.write(tmpText);
			bufferedWriter.flush();
			bufferedWriter.close();

			JOptionPane.showMessageDialog(null, "Zmiany zostały pomyślnie zapisane", "Kolejkowanie",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
			JOptionPane.showMessageDialog(null, "Wystąpił błąd w trakcie zapisywania dokumentu: " + ioe.getMessage(),
					"Kolejkowanie", JOptionPane.ERROR_MESSAGE);
		}
		refreshTable(false);

	}

	public void createNewFile() {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(queueTable.getParent()) == JFileChooser.APPROVE_OPTION) {
			File file = new File(fileChooser.getSelectedFile().toString() + ".txt");
			try {
				file.createNewFile();
				filePath = fileChooser.getSelectedFile().toString() + ".txt";
				DBData.getInstance().updatePath(DefineUtils.DB_pathqueueexistingfile, filePath);
				textField.setText(filePath);
				refreshTable(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void addDate() {
		addDate = new DateFrame(this);
		addDate.setVisible(true);
	}

	public void addVisit() {
		addVisit = new VisitDialog(this);
		addVisit.setVisible(true);
	}

	public void rejectChanges() {
		refreshTable(false);
	}

	public void deliverData(String formattedMsg) {
		if (addDate != null) {
			addDate.setVisible(false);
		}
		if (addVisit != null) {
			addVisit.setVisible(false);
		}

		tmpText += formattedMsg;
		writeTmpFile(tmpText);
		refreshTable(true);
	}

	private void writeTmpFile(String msg) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(tmpFileName), "UTF-8"));
			bufferedWriter.write(msg);
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
