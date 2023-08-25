package com.tpss.mpapp

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore

import android.util.Base64
import java.security.PrivateKey
import java.util.*

object SecurityUtil {
    // Change accordingly to your project's need
    private const val KEYSTORE_ALIAS =
        "com.tpss.myapp.publickeycryptography.key"

    /**
     * Generates the keypair with the alias [KEYSTORE_ALIAS] if it doesn't exist.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun getKeyPair(): KeyPair? {
        val ks: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        val aliases: Enumeration<String> = ks.aliases()
        val keyPair: KeyPair?

        /**
         * Check whether the keypair with the alias [KEYSTORE_ALIAS] exists.
         */
        if (aliases.toList().firstOrNull { it == KEYSTORE_ALIAS } == null) {
            // If it doesn't exist, generate new keypair
            val kpg: KeyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.DIGEST_SHA256,
                "AndroidKeyStore"
            )
            val parameterSpec: KeyGenParameterSpec = KeyGenParameterSpec.Builder(
                KEYSTORE_ALIAS,
                KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
            ).run {
                setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                build()
            }
            kpg.initialize(parameterSpec)

            keyPair = kpg.generateKeyPair()
        } else {
            // If it exists, load the existing keypair
            val entry = ks.getEntry(KEYSTORE_ALIAS, null) as? KeyStore.PrivateKeyEntry
            keyPair = KeyPair(entry?.certificate?.publicKey, entry?.privateKey)
        }
        return keyPair
    }

    /**
     * Generates the keypair with the alias [KEYSTORE_ALIAS] if it doesn't exist.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun generateKeyPair(): KeyPair? {
        val ks: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        val keyPair: KeyPair?
// Generate a key pair if it doesn't exist
//        if (!ks.containsAlias(KEYSTORE_ALIAS)) {
            val keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore"
            )

            val spec = KeyGenParameterSpec.Builder(
                KEYSTORE_ALIAS,
                KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
            )
                .setDigests(KeyProperties.DIGEST_SHA256)
                .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                .build()

            keyPairGenerator.initialize(spec)
            keyPair = keyPairGenerator.generateKeyPair()
//        }
        return keyPair
    }

    /**
     * Returns the public key with alias [KEYSTORE_ALIAS].
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun getPublicKey(): String? {
        val keyPair = generateKeyPair()
        val publicKey = keyPair?.public ?: return null
        return String(Base64.encode(publicKey.encoded, Base64.DEFAULT))
    }

    /**
     * Returns the private key with alias [KEYSTORE_ALIAS].
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun getPrivateKey(): PrivateKey? {
        val keyPair = generateKeyPair()
        return keyPair?.private
    }
}