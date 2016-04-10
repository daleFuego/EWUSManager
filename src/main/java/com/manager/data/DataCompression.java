package com.manager.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DataCompression {

	private ArrayList<ArrayList<String>> dataList;
	private String outputDirectory;
	private String sourceDirectory;
	private String zipPath = "";

	public DataCompression(ArrayList<ArrayList<String>> dataList, String outputDirectory, String sourceDirectory) {
		this.dataList = dataList;
		this.outputDirectory = outputDirectory;
		this.sourceDirectory = sourceDirectory;
	}

	public void zipFiles() {
		byte[] buffer = new byte[1024];

		try {
			zipPath = outputDirectory + "\\ewus.zip";
			FileOutputStream fileOutputStream = new FileOutputStream(zipPath);
			ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

			System.out.println("Output to Zip : " + outputDirectory);

			for (int index = 0; index < dataList.size(); index++) {
				String fileName = dataList.get(index).get(4);

				System.out.println("File Added : " + fileName);
				ZipEntry zipEntry = new ZipEntry(fileName);
				zipOutputStream.putNextEntry(zipEntry);

				FileInputStream in = new FileInputStream(sourceDirectory + "\\" + fileName);

				int len;

				while ((len = in.read(buffer)) > 0) {
					zipOutputStream.write(buffer, 0, len);
				}
				in.close();
			}

			zipOutputStream.closeEntry();
			zipOutputStream.close();

			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String shareZipPath() {
		return zipPath;
	}

}
