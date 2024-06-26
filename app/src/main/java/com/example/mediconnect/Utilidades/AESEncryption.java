package com.example.mediconnect.Utilidades;


import android.content.Context;
import android.util.Base64;

import com.example.mediconnect.R;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption {

    private final Context context;
    private static AESEncryption aesEncryption;
    private String textoSecreto;

    private AESEncryption(Context context) {
        this.context = context;
    }

    public static AESEncryption getInstance(Context context) {
        if(aesEncryption == null){
           aesEncryption = new AESEncryption(context);
           return aesEncryption;
        }
        return aesEncryption;
    }

    private SecretKey generarSecretKey() throws IOException {

        textoSecreto = this.getSecretKey();
        byte[] textoBytes = textoSecreto.getBytes(StandardCharsets.UTF_8);
        byte[] hash = Hashing.sha256().hashBytes(textoBytes).asBytes();
        final int maxLength = 256 / Byte.SIZE;
        byte[] key = Bytes.ensureCapacity(hash, maxLength, 0);
        return new SecretKeySpec(key, "AES");
    }

    public String cifrar(String mensajeOriginal) throws Exception {
        SecretKey secretKey = generarSecretKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherText = cipher.doFinal(mensajeOriginal.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeToString(cipherText, Base64.DEFAULT);
    }

    public String descifrar(String mensajeCifrado) throws Exception {
        byte[] cipherText = Base64.decode(mensajeCifrado, Base64.DEFAULT);
        SecretKey secretKey = generarSecretKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedText = cipher.doFinal(cipherText);
        return new String(decryptedText, StandardCharsets.UTF_8);
    }


    private String getSecretKey() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = context.getResources().openRawResource(R.raw.env);
        properties.load(inputStream);
        String secretKey = properties.getProperty("secretKey.AES");
        return secretKey;
    }
}
