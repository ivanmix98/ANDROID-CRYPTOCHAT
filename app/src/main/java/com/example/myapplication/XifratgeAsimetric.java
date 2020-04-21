package com.example.myapplication;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class XifratgeAsimetric {

    public KeyPair crearClaus() throws NoSuchAlgorithmException {
        System.out.println("Crear clave pública y privada");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);//tamaño de la clave
        KeyPair clavesRSA = keyGen.generateKeyPair();
        return clavesRSA;
    }

    public byte[] xifratgeASimetric(String sms, PublicKey clavePublica, Cipher cifrador) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, IOException {
        byte[] missatge = sms.getBytes();

        cifrador.init(Cipher.ENCRYPT_MODE, clavePublica);
        System.out.println("Cifrar con clave pública el Texto:");
        System.out.write(missatge);
        byte[] missatgeCifrado = cifrador.doFinal(missatge);
        System.out.println("Texto CIFRADO");
        System.out.write(missatgeCifrado);
        return missatgeCifrado;
    }

    public String desxifraASimetric(byte[] sms, PrivateKey clavePrivada, Cipher cifrador) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        cifrador.init(Cipher.DECRYPT_MODE, clavePrivada);
        System.out.println("Descifrar con clave privada");
       byte[] mensaje = cifrador.doFinal(sms);
       System.out.println("Texto DESCIFRADO");
        System.out.write(mensaje);
        String missatge = mensaje.toString();
        return missatge;
    }
}
