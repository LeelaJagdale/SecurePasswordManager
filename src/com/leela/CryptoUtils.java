package com.leela;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Arrays;

public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static void encrypt(String key, File inputFile, File outputFile) throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public static void decrypt(String key, File inputFile, File outputFile) throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    private static SecretKeySpec padKey(String key) {
        // Pad or truncate key to 16 bytes (AES-128)
        byte[] keyBytes = new byte[16];
        byte[] originalBytes = key.getBytes();
        System.arraycopy(originalBytes, 0, keyBytes, 0, Math.min(originalBytes.length, keyBytes.length));
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    private static void doCrypto(int mode, String key, File inputFile, File outputFile) throws CryptoException {
        try {
            SecretKeySpec secretKey = padKey(key);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(mode, secretKey);

            try (FileInputStream inputStream = new FileInputStream(inputFile);
                 FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                
                byte[] inputBytes = new byte[(int) inputFile.length()];
                inputStream.read(inputBytes);

                byte[] outputBytes = cipher.doFinal(inputBytes);
                outputStream.write(outputBytes);
            }
        } catch (Exception ex) {
            throw new CryptoException("Error during " + (mode == Cipher.ENCRYPT_MODE ? "encryption" : "decryption"), ex);
        }
    }

    public static class CryptoException extends Exception {
        public CryptoException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}