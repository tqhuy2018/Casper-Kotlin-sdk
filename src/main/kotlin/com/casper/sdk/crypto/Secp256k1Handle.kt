package com.casper.sdk.crypto

import com.casper.sdk.CasperUtils
import org.bouncycastle.asn1.sec.SECNamedCurves
import org.bouncycastle.crypto.generators.ECKeyPairGenerator
import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.crypto.params.ECKeyGenerationParameters
import org.bouncycastle.crypto.params.ECPrivateKeyParameters
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.signers.ECDSASigner
import org.web3j.crypto.ECKeyPair
import java.io.IOException
import java.math.BigInteger
import java.security.SecureRandom
import java.util.*


class Secp256k1Handle {

    companion object {
        fun generateKeyPair() {
            val string:String = "a1e3ce382dafebc4ed9a9efc7d771f669745e2a88b33f2b5eb4efa8c47721346"
            val privateKey: ByteArray = CasperUtils.fromStringToHexaBytes(string)

            val ec = ECKeyPair.create(BigInteger(1, privateKey))
//ECKeyPair ec =  ECKeyPair.create(privateKey); // needs web3j utilitiy classes
//ECKeyPair ec =  ECKeyPair.create(privateKey); // needs web3j utilitiy classes
            println("Raw private key: " + ec.privateKey.toString(16))
            println("Raw public  key: " + ec.publicKey.toString(16))
        }

        fun generatePrivateKey() : ByteArray {
            var length = 0
            var privateKey: ByteArray
            do {
                val gen = ECKeyPairGenerator()
                val secureRandom = SecureRandom()
                val secnamecurves = SECNamedCurves.getByName("secp256k1")
                val ecParams = ECDomainParameters(secnamecurves.curve, secnamecurves.g,
                    secnamecurves.n, secnamecurves.h)
                val keyGenParam = ECKeyGenerationParameters(ecParams, secureRandom)
                gen.init(keyGenParam)
                val kp = gen.generateKeyPair()
                val privatekey = kp.private as ECPrivateKeyParameters
                privateKey = privatekey.d.toByteArray()
                length = privatekey.d.toByteArray().size
            } while (length != 32)
            val keyLength:Int = privateKey.size
            for(i in 0..keyLength-1) {
                println("i is: " + i + " Key byte is:" + privateKey.get(i))
            }
            return privateKey
        }

        @Throws(IOException::class)
        fun readPrivateKeyFromPemFile(fileName:String) : String {
            val classLoader = javaClass.classLoader
            val inputStream = classLoader.getResourceAsStream(fileName)
            try {
                var data: String? = CasperUtils.readFromInputStream(inputStream)
                if(data.isNullOrEmpty()) {
                    throw IOException()
                } else {
                    data = data.replace("-----BEGIN EC PRIVATE KEY-----", "")
                    data = data.replace("-----END EC PRIVATE KEY-----", "")
                    val base64Bytes = Base64.getDecoder().decode(data)
                    val ec = ECKeyPair.create(BigInteger(1, base64Bytes))
                    println("Raw private key: " + ec.privateKey.toString(16))
                    println("Raw public  key: " + ec.publicKey.toString(16))
                    return ec.privateKey.toString()
                }
            } catch (e:java.lang.Exception){
                println("Error catch")
            } finally {
                println("Error catch finally")
            }
            throw IOException()
        }
        fun signMessage(messageToSign:String,privateKey:String):String {
            val domain: ECDomainParameters = ECDomainParameters()
            val signer = ECDSASigner()
            signer.init(true, ECPrivateKeyParameters(BigInteger(1, getPrivateKey()), domain))
            val components = signer.generateSignature(CasperUtils.fromStringToHexaBytes(messageToSign))
            val signature = ECSignature(components[0], components[1])
        }
    }
}