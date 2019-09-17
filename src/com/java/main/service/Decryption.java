package com.java.main.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.java.main.service.model.FileInfo;

public class Decryption {

	private String filePath;
	private String sKey;
	private boolean isWrongKey = false;

	public Decryption() {

	}

	public Decryption(String filePath, String sKey) {
		super();
		this.filePath = filePath;
		this.sKey = sKey;
	}

	public void sourceDecoder() {
		File files = new File(filePath);
		File[] fileArr = files.listFiles();
		List<File> fileList = Arrays.asList(fileArr);

		filePath = filePath + File.separator + HelperLogicUtil.DECRYPTED_Folder;

		List<FileInfo> fileInfoList = fileList.stream()
				.map(each -> fileDecryption(each))
				.collect(Collectors.toList());

		if(!isWrongKey) {
			File tmpDir = new File(filePath);
			boolean isExists = tmpDir.exists();
			boolean folderCreated = false;
			if(!isExists) {
				folderCreated = new File(filePath).mkdir();
			}
			if(!folderCreated) {
				System.out.println("Error occured during creating folder");
				JOptionPane.showMessageDialog(null, "Error occured while creating the decrypting folder");
			} else {
				fileInfoList.forEach(each -> SaveDecryptedImageToFolder(each.getImageByteArray(),each.getFileName()));
			}
		}

	}

	private FileInfo fileDecryption(File imageFile) {
		FileInputStream imageInFile = null;
		FileInfo fileInfo = null;
		try {
			// Reading a Image file from file system
			imageInFile = new FileInputStream(imageFile);
			byte imageData[] = new byte[(int) imageFile.length()];
			imageInFile.read(imageData);

			// Converting Image byte array into Base64 String
			String imageDataString = new String(imageData);

			// decrypting the encrypted file into String
			String encryptedString = HelperLogicUtil.decrypt(imageDataString, sKey);

			// Base64 decoding the String to bytestream
			byte[] imageByteArray = HelperLogicUtil.decodeImage(encryptedString);

			fileInfo = new FileInfo(imageByteArray, imageFile.getName());

			System.out.println("File Decryption successfull");

			imageInFile.close();
		} catch (Exception e) {
			System.out.println("Error while DECRYPTING the file and saving it in class path with error code :: " + e.getMessage());
			isWrongKey = true;
		} finally {
			try {
				imageInFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileInfo;
	}

	private void SaveDecryptedImageToFolder(byte[] imageByteArray, String fileName) {
		try {
			if (null != imageByteArray) {
				FileOutputStream imageOutFile = new FileOutputStream(HelperLogicUtil.getResourcesPath(filePath, fileName));
				imageOutFile.write(imageByteArray);
				imageOutFile.close();
			} else {
				System.out.println("ByteStream is null for ::"+fileName);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
