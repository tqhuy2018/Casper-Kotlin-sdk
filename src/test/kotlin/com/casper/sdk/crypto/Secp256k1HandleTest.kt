package com.casper.sdk.crypto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Secp256k1HandleTest {
    @Test
    fun  testAll() {
        testKeyGeneration()
    }
    fun testLoadPrivateKey() {
        val fileName:String = "KotlinEd25519PrivateKey.pem"
        Secp256k1Handle.readPrivateKeyFromPemFile(fileName)
    }
    fun testKeyGeneration() {
        Secp256k1Handle.generateKeyPair()
    }
}