package com.manager.logic;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.manager.dao.DBData;
import com.manager.utils.DefineUtils;

public class FileManager {

	private String pathBrowser = DBData.pathInitBrowseFiles;
	private String pathSend = DBData.pathInitSendMailFile;
	private String pathQueue = DBData.pathInitQueueExistingFile;
	private JTextField textFieldFilePath;

	public FileManager(JTextField textFieldFilePath) {
		this.textFieldFilePath = textFieldFilePath;
	}

	public String browseDirectory() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("xml files (*.xml)", "xml"));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
			this.pathBrowser = fileChooser.getSelectedFile().getAbsolutePath();
		}
		DBData.getInstance().updatePath(DefineUtils.DB_pathbrowsefiles, pathBrowser);
		textFieldFilePath.setText(pathBrowser);
		return pathBrowser;
	}

	public String browseZipFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Zip Files", "zip"));
		if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
			this.pathSend = fileChooser.getSelectedFile().getAbsolutePath();
		}
		DBData.getInstance().updatePath(DefineUtils.DB_pathsendmailfile, pathSend);
		textFieldFilePath.setText(pathSend);
		return pathSend;
	}

	public String browseFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("text files (*.txt)", "txt"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
			this.pathQueue = fileChooser.getSelectedFile().getAbsolutePath();
		}
		DBData.getInstance().updatePath(DefineUtils.DB_pathqueueexistingfile, pathQueue);
		textFieldFilePath.setText(pathQueue);
		return pathQueue;
	}
}
