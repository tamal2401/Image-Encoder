package com.java.main.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class HelperLogicUtil {

	private static SecretKeySpec secretKey;
    private static byte[] key;
    
	protected static final String DECRYPTED_Folder = "DecryptedImages";
	protected static final String ENCRYPTED_Folder = "EncryptedImages";
	protected static final String BASE_Folder = "ImagesToEncrypt";
    
	/*
	 * Key generator for Encrypting the image more Securely
	 * @param = String keyValue
	 * 
	 */
    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    /*
	 * Encryption logic in AES algorithm to encrypt converted string
	 * @param1 = String keyValue
	 * @param2 = String converted_imageFile
	 * 
	 */
    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
    
    /*
   	 * Decryption logic in AES algorithm to decrypt converted string
   	 * @param1 = String keyValue
   	 * @param2 = String converted_imageFile
   	 * 
   	 */
    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    
    /*
     * Get the resource path for the provited folder and the file
     */
	protected static String getResourcesPath(String filePath, String fileName) {
		String resourcesPath = filePath + File.separator + fileName;
		return resourcesPath;
	}

	/*
	 * Base64 Encoding logic from image file to string
	 */
	protected static String encodeImage(byte[] imageData) {
		return Base64.getEncoder().encodeToString(imageData);
	}
	
	/*
	 * Base64 Decoding logic from String to image
	 */
	protected static byte[] decodeImage(String imageDataString) {
		return Base64.getDecoder().decode(imageDataString);
	}
}
