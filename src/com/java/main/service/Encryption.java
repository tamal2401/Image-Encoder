package com.java.main.service;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Encryption {

	private String filePath;
	private String sKey;
	private boolean isExists = true;
	
	public Encryption() {

	}
	
	public Encryption(String filePath, String sKey) {
		super();
		this.filePath = filePath;
		this.sKey = sKey;
	}
	
	public void sourceDecoder() {

		File files = new File(filePath);
		File[] fileArr = files.listFiles();
		List<File> fileList = Arrays.asList(fileArr);
		boolean folderCreated = false;
		filePath = filePath + File.separator + HelperLogicUtil.ENCRYPTED_Folder;

		File tmpDir = new File(filePath);
		isExists = tmpDir.exists();
		if(!isExists) {
			folderCreated = new File(filePath).mkdir();
		}
		try {
			if(folderCreated) {
				fileList.stream()
				.forEach(each -> fileEncryption(each));
			} else {
				JOptionPane.showMessageDialog(null, "Error occured while creating the encryption folder");
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error occured while creating the encryption folder in specified path \n "+filePath);
		}
	}
	
	private FileInfo fileEncryption(File imageFile) {
		FileInputStream imageInFile = null;
		try {
			// Reading a Image file from file system
			imageInFile = new FileInputStream(imageFile);
			byte imageData[] = new byte[(int) imageFile.length()];
			imageInFile.read(imageData);

			// Converting Image byte array into Base64 String
			String imageDataString = HelperLogicUtil.encodeImage(imageData);

			// Encrypting the image using AES algorithm
			String encryptedString = HelperLogicUtil.encrypt(imageDataString, sKey);

			// saving the image to encrypted folder
			saveImageToPath(encryptedString, imageFile.getName());
			System.out.println("File encryption Successfull");
			

			System.out.println("Image Successfully Manipulated!");
		} catch (Exception e) {
			System.out.println("Error while ENCRYPTING the file and saving it in class path with error code :: " + e.getMessage());
		} finally {
			try {
				imageInFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void saveImageToPath(String encryptedString, String fileName) throws Exception {

		if (null != encryptedString) {
			try {
				FileOutputStream imageOutFile = new FileOutputStream(HelperLogicUtil.getResourcesPath(filePath, fileName));
				byte[] byteArray = encryptedString.getBytes();
				imageOutFile.write(byteArray);	
			} catch(Exception e) {
				System.out.println("Error Occured ::"+e.getMessage());
			}
		} else {
			throw new Exception("encrypted string is null");
		}

	}
}
