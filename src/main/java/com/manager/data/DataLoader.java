package com.manager.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.manager.utils.CallTrace;
import com.manager.utils.DefineUtils;

public class DataLoader {

	private String directory;
	private DefaultTableModel table;
	private ArrayList<ArrayList<String>> dataList;

	public DataLoader(String directory, DefaultTableModel tableModel) {
		this.directory = directory;
		this.table = tableModel;
	}

	private ArrayList<ArrayList<String>> loadFiles() {
		dataList = new ArrayList<ArrayList<String>>();
		try {
			File folder = new File(directory);
			for (File file : folder.listFiles()) {
				if (file.isFile() && file.getName().endsWith(".xml")) {
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(file);
					doc.getDocumentElement().normalize();

					Element docElement = doc.getDocumentElement();

					NodeList nodeDate = docElement.getElementsByTagName("ns2:data_waznosci_potwierdzenia");
					NodeList nodeName = docElement.getElementsByTagName("ns2:imie");
					NodeList nodeSurname = docElement.getElementsByTagName("ns2:nazwisko");
					NodeList nodePesel = docElement.getElementsByTagName("ns2:numer_pesel");

					ArrayList<String> infoSet = new ArrayList<>();

					infoSet.add(nodeDate.item(0).getTextContent());
					infoSet.add(nodeName.item(0).getTextContent());
					infoSet.add(nodeSurname.item(0).getTextContent());
					infoSet.add(nodePesel.item(0).getTextContent());
					infoSet.add(file.getName());

					dataList.add(infoSet);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	public boolean deleteFile(String fileName) {
		boolean result = false;
		String filePath = directory + DefineUtils.FILE_SEPARATOR + fileName;
		File file = new File(filePath);
		if (!file.isDirectory()) {
			File binPath = new File(System.getProperty("user.home"),
					"Desktop" + DefineUtils.FILE_SEPARATOR + "DeletedItems" + DefineUtils.FILE_SEPARATOR + fileName);
			if (file.renameTo(binPath)) {
				result = true;
			} else {
				File dir = new File(System.getProperty("user.home"),
						"Desktop" + DefineUtils.FILE_SEPARATOR + "DeletedItems");
				dir.mkdir();
				file.renameTo(binPath);
				result = true;
			}
		}
		return result;
	}

	public boolean undoDeleteFile() {
		boolean result = false;
		String msg;
		try {
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(new FileReader(CallTrace.getInstance().getLogFile()));
			while ((msg = bufferedReader.readLine()) != null) {
				if (msg.contains("#delete")) {
					String deletedFilePath = msg.split(":")[2];
					String restoredFilePath = directory + DefineUtils.FILE_SEPARATOR + deletedFilePath;
					File restoredFile = new File(restoredFilePath);
					File binFile = new File(System.getProperty("user.home"), "Desktop" + DefineUtils.FILE_SEPARATOR
							+ "DeletedItems" + DefineUtils.FILE_SEPARATOR + deletedFilePath);
					binFile.renameTo(restoredFile);
					CallTrace.getInstance().clearLogs();
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return result;
	}

	public boolean fillTable() {
		boolean result = false;
		if (dataList != null) {
			dataList.removeAll(dataList);
		}
		loadFiles();
		try {
			for (int i = 0; i < dataList.size(); i++) {
				Vector<Object> data = new Vector<Object>();
				data.add(i);
				data.add(dataList.get(i).get(0));
				data.add(dataList.get(i).get(1));
				data.add(dataList.get(i).get(2));
				data.add(dataList.get(i).get(3));
				data.add(dataList.get(i).get(4));

				table.addRow(data);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public String provideInfo() {
		String infoText = "";
		try {
			infoText = "Ilość potwierdzeń: " + dataList.size() + "\n" + "Okres czasu: od " + dataList.get(0).get(0)
					+ " do " + dataList.get(dataList.size() - 1).get(0) + "\n";
		} catch (Exception ex) {

		}
		return infoText;
	}

	public String ewusMailTopic() {
		String infoText = "";
		try {
			infoText = "Potwierdzenia EWUS (od " + dataList.get(0).get(0) + " do "
					+ dataList.get(dataList.size() - 1).get(0) + ")";
		} catch (Exception ex) {
			infoText = "Potwierdzenia EWUS";
		}
		return infoText;
	}

	public ArrayList<ArrayList<String>> getFileList() {
		return dataList;
	}
}
