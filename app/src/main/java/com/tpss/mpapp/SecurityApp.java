package com.tpss.mpapp;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateException;

public class SecurityApp {

    protected String biometricKeyAlias = "biometric_key";

    private Signature createCryptoSignature(String payloadData) {
        try {
            byte[] byteData = payloadData.getBytes();
            PrivateKey privateKey = getPrivateKey();
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(byteData);
            return signature;
        }catch (Exception err){
            Log.e("Security", "generateKeyPair: " + err.getLocalizedMessage());
            return null;
        }
    }

    protected KeyPair generateKeyPair() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore"
                );

                KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                        biometricKeyAlias,
                        KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                        .setDigests(KeyProperties.DIGEST_SHA256)
                .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                .build();

                keyPairGenerator.initialize(spec);
                return keyPairGenerator.generateKeyPair();

        } catch (Exception e) {
            Log.e("Security", "generateKeyPair: " + e.getLocalizedMessage() );
        }
        return null;
    }

    protected PrivateKey getPrivateKey() {
        KeyPair kp = generateKeyPair();
        if(kp != null){
            return kp.getPrivate();
        }
        return null;
    }
}
