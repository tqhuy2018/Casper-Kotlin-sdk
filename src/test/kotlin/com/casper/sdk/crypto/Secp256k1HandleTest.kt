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
        val fileName:String = "KotlinSecp256k1PrivateKey.pem"
        //val privateKeyStr:String = Secp256k1Handle.readPrivateKeyFromPemFile(fileName)
       // Secp256k1Handle.signMessage3("aa")
        //Secp256k1Handle.loadPemFile(fileName,"aaa111")
        //Secp256k1Handle.signMessage("abc133","abc")
    }
    fun testKeyGeneration() {
        Secp256k1Handle.generateKeyPair()
    }
}