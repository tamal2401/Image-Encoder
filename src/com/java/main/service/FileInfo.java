package com.java.main.service;

import java.awt.HeadlessException;
import java.util.Arrays;

import javax.swing.JFrame;

public class FileInfo extends JFrame {

	private byte[] imageByteArray;
	private String fileName;
	
	public FileInfo(byte[] imageByteArray, String fileName) throws HeadlessException {
		super();
		this.imageByteArray = imageByteArray;
		this.fileName = fileName;
	}

	public byte[] getImageByteArray() {
		return imageByteArray;
	}

	public void setImageByteArray(byte[] imageByteArray) {
		this.imageByteArray = imageByteArray;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "FileInfo [imageByteArray=" + Arrays.toString(imageByteArray) + ", fileName=" + fileName + "]";
	}
	
	
}
