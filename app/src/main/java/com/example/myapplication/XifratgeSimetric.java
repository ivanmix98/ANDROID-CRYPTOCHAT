package com.example.myapplication;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class XifratgeSimetric {
    KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
    SecretKey clauDES = keygenerator.generateKey();
    Cipher xifradorDES = Cipher.getInstance("DES/ECB/PKCS5Padding");
    public XifratgeSimetric() throws NoSuchAlgorithmException, NoSuchPaddingException {
    }

    public byte[] xifratgeSimetric(String sms) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] missatge = sms.getBytes();
        xifradorDES.init(Cipher.ENCRYPT_MODE, clauDES);
        // xifratge del missatge
        byte[] missatgeXifrat = xifradorDES.doFinal(missatge);
        return missatgeXifrat;
    }

    public String desxifraSimetric(byte[] sms) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        xifradorDES.init(Cipher.DECRYPT_MODE, clauDES);
        byte[] missatgeDes = xifradorDES.doFinal(sms);
        System.out.write(missatgeDes);
        String mensaje = missatgeDes.toString();

        return mensaje;
    }
}

