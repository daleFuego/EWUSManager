package com.manager.panel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.manager.gui.AddDate;
import com.manager.gui.AddVisit;

public class QueueManager {

	private JEditorPane editorPane;
	private String filePath;
	private AddVisit addVisit;
	private AddDate addDate;
	private JTextField textField;

	public QueueManager(JEditorPane editorPane, String filePath, JTextField textFieldQueueMainFiles) {
		this.editorPane = editorPane;
		this.filePath = filePath;
		this.textField = textFieldQueueMainFiles;
	}

	public void parseFile() {
		String msg;
		editorPane.setText("");
		editorPane.setCaretPosition(0);
		textField.setText(filePath);
		try {
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			while ((msg = bufferedReader.readLine()) != null) {
				if (editorPane.getCaretPosition() != 0) {
					editorPane.setText(editorPane.getText() + "\n" + msg);
				} else {
					editorPane.setText(editorPane.getText() + msg);
				}
			}
		} catch (IOException e) {
		}
	}

	public void saveChanges() {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));

			bufferedWriter.write(editorPane.getText());
			bufferedWriter.flush();
			bufferedWriter.close();
			
			JOptionPane.showMessageDialog(null,"Zmiany zostały pomyślnie zapisane", "Kolejkowanie", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
			JOptionPane.showMessageDialog(null,"Wystąpił błąd w trakcie zapisywania dokumentu: " + ioe.getMessage(), "Kolejkowanie", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void createNewFile() {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(editorPane.getParent()) == JFileChooser.APPROVE_OPTION) {
			File file = new File(fileChooser.getSelectedFile().toString() + ".txt");
			try {
				file.createNewFile();
				filePath = fileChooser.getSelectedFile().toString() + ".txt";
				parseFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void addDate() {
		addDate = new AddDate(this);
		addDate.setVisible(true);
	}

	public void addVisit() {
		addVisit = new AddVisit(this);
		addVisit.setVisible(true);
	}

	public void rejectChanges() {
		editorPane.setText("");
		editorPane.setCaretPosition(0);
		parseFile();
	}

	public void deliverData(String day, String name, String surname, String pesel, String date) {
		addVisit.setVisible(false);
		editorPane
				.setText(editorPane.getText() + "\n" + day + "\t" + name + "\t" + surname + "\t" + pesel + "\t" + date);
	}

	public void deliverData(String date) {
		editorPane.setText(editorPane.getText() + "\n====================================\nPierwszy wolny termin na "
				+ date + "\n====================================");
		addDate.setVisible(false);
	}
}
