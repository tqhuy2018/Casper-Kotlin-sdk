package com.casper.sdk.crypto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Secp256k1HandleTest {
    @Test
    fun  testAll() {
        testKeyGeneration()
        testLoadPrivateKey()
    }
    fun testLoadPrivateKey() {
        val fileName:String = "KotlinEd25519PrivateKey.pem"
        //val privateKeyStr:String = Secp256k1Handle.readPrivateKeyFromPemFile(fileName)
        Secp256k1Handle.GenerateKey2()
        //Secp256k1Handle.signMessage("abc133","abc")
    }
    fun testKeyGeneration() {
        Secp256k1Handle.generateKeyPair()
    }
}