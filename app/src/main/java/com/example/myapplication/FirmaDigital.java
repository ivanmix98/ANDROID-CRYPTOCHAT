package com.example.myapplication;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class FirmaDigital {

    //funcion que genera las claves publica/privada RSa
    public KeyPair clavesPuvPriv() throws NoSuchAlgorithmException {
        KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
        generador.initialize(1024);
        KeyPair parClaves = generador.genKeyPair();

        return parClaves;
    }
    //funcion que genera la firma digital
    public byte[] signData(byte[] data, PrivateKey priv) {
        byte[] signature = null;
        try {
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initSign(priv);
            signer.update(data);
            signature = signer.sign();
        } catch (Exception ex) {
            System.err.println("Error signant les dades: " + ex);
        }
        return signature;
    }

    //funcion que valida la firma digital
    public boolean validateSignature(byte[] data, byte[] signature, PublicKey pub)
    {
        boolean isValid = false;
        try {
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initVerify(pub);
            signer.update(data);
            isValid = signer.verify(signature);
        } catch (Exception ex) {
            System.err.println("Error validant les dades: " + ex);
        }
        return isValid;
    }
}
