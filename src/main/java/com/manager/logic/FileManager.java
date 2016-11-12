package com.manager.logic;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.manager.dao.DBData;
import com.manager.utils.DefineUtils;

public class FileManager {

	public String browseDirectory(String dbPath, String filter, String fileType) {
		String path = "";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(filter, fileType));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
			path = fileChooser.getSelectedFile().getAbsolutePath();
		}
		if (!dbPath.equals("")) {
			DBData.getInstance().update(DefineUtils.DB_TABLE_PATHS, dbPath, path);
		}
		return path;
	}

	public String browseFile(String dbPath, String filter, String fileType) {
		String path = "";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(filter, fileType));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
			path = fileChooser.getSelectedFile().getAbsolutePath();
		}
		DBData.getInstance().update(DefineUtils.DB_TABLE_PATHS, dbPath, path);
		return path;
	}
}
