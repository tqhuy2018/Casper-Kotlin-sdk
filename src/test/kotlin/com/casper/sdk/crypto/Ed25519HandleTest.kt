package com.casper.sdk.crypto

import com.casper.sdk.ConstValues
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey
import org.bouncycastle.util.encoders.Hex
import org.junit.jupiter.api.Test
import java.io.BufferedReader

import java.io.IOException

import java.io.InputStream
import java.io.InputStreamReader


internal class Ed25519HandleTest {
    @Test
    fun  testAll() {
        testGenerateKey()
        testLoadPrivateKey()
    }
    fun testGenerateKey() {
        Ed25519Handle.generateKey()
    }
    fun testLoadPrivateKey() {
        val privateKey: Ed25519PrivateKeyParameters = Ed25519Handle.readPrivateKeyFromPemFile(ConstValues.PEM_PRIVATE_ED25519)
        assert(privateKey is Ed25519PrivateKeyParameters)
        assert(Hex.toHexString(privateKey.encoded) == "954b81a59283ec5bcf7186148f9f8b2f1cdfb62ebbf54652ef6a246d6fcc65f2")
        val wrongPemPath:String = "wrongEd25519PrivateKey.pem"
        try {
            val privateKey2: Ed25519PrivateKeyParameters = Ed25519Handle.readPrivateKeyFromPemFile(wrongPemPath)
        } catch (e: IOException) {
            println("Error load wrong private key from a wrong path")
        } catch (e:java.lang.Exception) {
            println("Error load wrong private key from a wrong path2")
        } finally {
            println("Error load wrong private key from a wrong path finally")
        }
        Ed25519Handle.generateKey()
    }

}