package com.manager.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.manager.utils.DefineUtils;

public class DataLoader {

	private static DataLoader dataLoader;
	private String directory;

	private DefaultTableModel table;
	private ArrayList<ArrayList<String>> dataList;

	private DataLoader() {
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public void setTable(DefaultTableModel table) {
		this.table = table;
	}

	public static DataLoader getInstance() {
		if (dataLoader == null) {
			dataLoader = new DataLoader();
		}
		return dataLoader;
	}

	private ArrayList<ArrayList<String>> loadFiles() {
		dataList = new ArrayList<ArrayList<String>>();

		try {
			Cipher desCipher = Cipher.getInstance("DES");
			desCipher.init(Cipher.ENCRYPT_MODE, KeyGenerator.getInstance("DES").generateKey());

			File folder = new File(directory);
			
			
			
			for (File file : sortFiles(folder)) {
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

					ArrayList<String> infoSet = new ArrayList<String>();

					infoSet.add(nodeDate.item(0).getTextContent());
					infoSet.add(nodeName.item(0).getTextContent());
					infoSet.add(nodeSurname.item(0).getTextContent());
					infoSet.add(nodePesel.item(0).getTextContent());
					infoSet.add(file.getName());

					dataList.add(infoSet);
				}
			}
			
		} catch (Exception e) {
		}

		return dataList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private File[] sortFiles(File folder) {
		File [] files = folder.listFiles();

		Arrays.sort( files, new Comparator()
		{
		    public int compare(Object o1, Object o2) {

		        if (((File)o1).lastModified() > ((File)o2).lastModified()) {
		            return -1;
		        } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
		            return +1;
		        } else {
		            return 0;
		        }
		    }

		});
		
		return files;
	}

	public boolean deleteFile(String fileName) {
		boolean result = false;
		
		String filePath = directory + DefineUtils.FILE_SEPARATOR + fileName;
		System.out.println(filePath);
		File file = new File(filePath);
		if (!file.isDirectory()) {
			File binPath = new File("DeletedItems" + DefineUtils.FILE_SEPARATOR + fileName);
			System.out.println(binPath);
			if (file.renameTo(binPath)) {
				result = true;
			} else {
				File dir = new File("DeletedItems");
				dir.mkdir();
				file.renameTo(binPath);
				result = true;
			}
		}
		return result;
	}

	public boolean undoDeleteFile() {
		// TODO
		return false;
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
		try {
			return "Ilość potwierdzeń: \n   " + dataList.size() + "\n" + "\nOkres czasu: \nod \n   "
					+ dataList.get(0).get(0) + " \ndo \n   " + dataList.get(dataList.size() - 1).get(0) + "\n";
		} catch (IndexOutOfBoundsException e) {
			return "Brak potwierdzeń";
		}
	}

	public String provideInfoLong() {
		try {
			if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
				return "Ilość potwierdzeń: \n   " + dataList.size() + "\n" + "\nOkres czasu: \nod \n   "
						+ dataList.get(0).get(0) + " \ndo \n   " + dataList.get(dataList.size() - 1).get(0) + "\n";
			} else {
				return "Ilość potwierdzeń: " + dataList.size() + "\n" + "Okres czasu: od " + dataList.get(0).get(0)
						+ " do " + dataList.get(dataList.size() - 1).get(0) + "\n";
			}

		} catch (IndexOutOfBoundsException e) {
			return "Brak potwierdzeń";
		}
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
