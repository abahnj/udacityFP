package com.norvera.confession.data.billingrepo

import android.text.TextUtils
import android.util.Base64
import timber.log.Timber
import java.io.IOException
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec

/**
 * Copyright (C) 2018 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


object Security {
    private val TAG = "IABUtil/Security"
    private val KEY_FACTORY_ALGORITHM = "RSA"
    private val SIGNATURE_ALGORITHM = "SHA1withRSA"

    /**
     * This is the codelab's public key. For you own app, you must get your own.
     *
     * BASE_64_ENCODED_PUBLIC_KEY should be YOUR APPLICATION'S PUBLIC KEY
     * (that you got from the Google Play developer console, usually under Services
     * & APIs tab). This is not your developer public key, it's the *app-specific*
     * public key.
     *
     * Just like everything else in this class, this public key should be kept on
     * your server. But if you don't have a server, then you should obfuscate your
     * app so that hackers cannot get it. If you cannot afford a sophisticated
     * obfuscator, instead of just storing the entire literal string here embedded
     * in the program,  construct the key at runtime from pieces or use bit
     * manipulation (for example, XOR with some other string) to hide the actual
     * key.  The key itself is not secret information, but we don't want to make it
     * easy for an attacker to replace the public key with one of their own and
     * then fake messages from the server.
     */
    val BASE_64_ENCODED_PUBLIC_KEY =
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlrCudSHlj1oLUL8t/33VfNOrcBISpL0cc6WskVkLXHx+QaNjrJnupzyNvYT7jW+EwyjiUJ0T18NW+rEvvtakRaERN6sgpKXWAkemCpOI/Ouvz3sTKMt9c5WjWcABjU4HHyUbc4AskNqqrqu1XLZABU0U+PlZF1tDsapka3kxA1LQNC3ljFzs8KomUDm0M4J5W2p3LYR1ZtqedANE8p/iuyOsqO/BiiO4B0yJSbmR1NmB9Bj/0o81g74mR+AMiQ2OMilPZ+jZ3orSFilx1urET+AY8JqwhpfZB1BTMSxFZZP53osbWMWROqESGAklKqgJxO2JK6Y7qETbVsEwc9t/SQIDAQAB"

    /**
     * Verifies that the data was signed with the given signature
     *
     * @param base64PublicKey the base64-encoded public key to use for verifying.
     * @param signedData the signed JSON string (signed, not encrypted)
     * @param signature the signature for the data, signed with the private key
     * @throws IOException if encoding algorithm is not supported or key specification
     * is invalid
     */
    @Throws(IOException::class)
    fun verifyPurchase(
        base64PublicKey: String, signedData: String,
        signature: String
    ): Boolean {
        if ((TextUtils.isEmpty(signedData) || TextUtils.isEmpty(base64PublicKey)
                    || TextUtils.isEmpty(signature))
        ) {
            Timber.w("Purchase verification failed: missing data.")
            return false
        }
        val key = generatePublicKey(base64PublicKey)
        return verify(key, signedData, signature)
    }

    /**
     * Generates a PublicKey instance from a string containing the Base64-encoded public key.
     *
     * @param encodedPublicKey Base64-encoded public key
     * @throws IOException if encoding algorithm is not supported or key specification
     * is invalid
     */
    @Throws(IOException::class)
    private fun generatePublicKey(encodedPublicKey: String): PublicKey {
        try {
            val decodedKey = Base64.decode(encodedPublicKey, Base64.DEFAULT)
            val keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM)
            return keyFactory.generatePublic(X509EncodedKeySpec(decodedKey))
        } catch (e: NoSuchAlgorithmException) {
            // "RSA" is guaranteed to be available.
            throw RuntimeException(e)
        } catch (e: InvalidKeySpecException) {
            val msg = "Invalid key specification: $e"
            Timber.w(msg)
            throw IOException(msg)
        }
    }

    /**
     * Verifies that the signature from the server matches the computed signature on the data.
     * Returns true if the data is correctly signed.
     *
     * @param publicKey public key associated with the developer account
     * @param signedData signed data from server
     * @param signature server signature
     * @return true if the data and signature match
     */
    private fun verify(publicKey: PublicKey, signedData: String, signature: String): Boolean {
        val signatureBytes: ByteArray
        try {
            signatureBytes = Base64.decode(signature, Base64.DEFAULT)
        } catch (e: IllegalArgumentException) {
            Timber.w("Base64 decoding failed.")
            return false
        }
        try {
            val signatureAlgorithm = Signature.getInstance(SIGNATURE_ALGORITHM)
            signatureAlgorithm.initVerify(publicKey)
            signatureAlgorithm.update(signedData.toByteArray())
            if (!signatureAlgorithm.verify(signatureBytes)) {
                Timber.w("Signature verification failed...")
                return false
            }
            return true
        } catch (e: NoSuchAlgorithmException) {
            // "RSA" is guaranteed to be available.
            throw RuntimeException(e)
        } catch (e: InvalidKeyException) {
            Timber.w("Invalid key specification.")
        } catch (e: SignatureException) {
            Timber.w("Signature exception.")
        }
        return false
    }
}