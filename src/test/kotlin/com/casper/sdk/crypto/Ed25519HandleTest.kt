package com.casper.sdk.crypto

import org.junit.jupiter.api.Test
import java.io.BufferedReader

import java.io.IOException

import java.io.InputStream
import java.io.InputStreamReader


internal class Ed25519HandleTest {
    @Test
    fun  testAll() {
        testLoadPrivateKey()
    }
    fun testLoadPrivateKey() {
        val fileName:String = "KotlinEd25519PrivateKey.pem"
        Ed25519Handle.readPrivateKeyFromPemFile(fileName)
        Ed25519Handle.generateKey()
    }

}