package com.manager.data;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.manager.gui.ProgressDialog;
import com.manager.utils.DefineUtils;

public class DataCompression {

	private ArrayList<ArrayList<String>> dataList;
	private String outputDirectory;
	private String sourceDirectory;
	private String zipPath = "";
	private ProgressDialog progessDialog;
	private Task task;
	private JTextField textFieldSendFilesMailFile;

	public DataCompression(ArrayList<ArrayList<String>> dataList, String outputDirectory, String sourceDirectory) {
		this.dataList = dataList;
		this.outputDirectory = outputDirectory;
		this.sourceDirectory = sourceDirectory;
	}

	public void zipFiles(JTextField textFieldSendFilesMailFile) {
		this.textFieldSendFilesMailFile = textFieldSendFilesMailFile;
		task = new Task();
		task.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("progress" == evt.getPropertyName()) {
					progessDialog.progressBarProcessingTask.setValue((Integer) evt.getNewValue());
				}
			}
		});
		progessDialog = new ProgressDialog("Buduję archiwum...");

		task.execute();
	}

	class Task extends SwingWorker<Void, Void> {

		@Override
		public Void doInBackground() {
			task.setProgress(0);

			byte[] buffer = new byte[1024];

			try {
				zipPath = outputDirectory + DefineUtils.FILE_SEPARATOR + "EWUS_"
						+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".zip";

				FileOutputStream fileOutputStream = new FileOutputStream(zipPath);
				ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

				for (int index = 0; index < dataList.size(); index++) {
					String fileName = dataList.get(index).get(4);

					zipOutputStream.putNextEntry(new ZipEntry(fileName));
					FileInputStream in = new FileInputStream(sourceDirectory + DefineUtils.FILE_SEPARATOR + fileName);

					int len;

					while ((len = in.read(buffer)) > 0) {
						zipOutputStream.write(buffer, 0, len);
					}
					long progress = ((100 * index) / dataList.size());
					task.setProgress((int) progress);
					in.close();
				}

				zipOutputStream.closeEntry();
				zipOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		public void done() {
			progessDialog.dispose();
			textFieldSendFilesMailFile.setText(zipPath);
		}
	}
}
