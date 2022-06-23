package com.casper.sdk.crypto

import com.casper.sdk.ConstValues
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import org.bouncycastle.crypto.util.PrivateKeyFactory
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.util.encoders.Hex
import org.junit.jupiter.api.Test
import java.io.*

import java.security.KeyPair


internal class Ed25519HandleTest {
    @Test
    fun  testAll() {
        testSignAndVerifyMessage()
        testWriteToPemFile()
        //testGenerateKey()
        //testLoadPrivateKey()
        //testLoadPublicKey()
        //testWritePemPrivate()
    }
    fun testWriteToPemFile() {
        val keyPair = Ed25519Handle.generateKey()
        Ed25519Handle.writePrivateKeyToPemFile(ConstValues.PEM_PRIVATE_WRITE_ED25519,keyPair.private as Ed25519PrivateKeyParameters)
        Ed25519Handle.writePublicKeyToPemFile(ConstValues.PEM_PUBLIC_WRITE_ED25519,keyPair.public as Ed25519PublicKeyParameters)
        //Negative path: Write private pem file to a non-exist path
        try {
            Ed25519Handle.writePrivateKeyToPemFile("some/nonexist/path/privateEd25519.pem",
                keyPair.private as Ed25519PrivateKeyParameters)
        } catch (e:IOException) {
            println("Error write private key to a non-exist file")
        }
        //Negative path: Write public pem file to a non-exist path
        try {
            Ed25519Handle.writePublicKeyToPemFile("some/nonexist/path/publicEd25519.pem",keyPair.public as Ed25519PublicKeyParameters)
        } catch (e:IOException) {
            println("Error write public key to a non-exist file")
        }
    }
    fun  testSignAndVerifyMessage() {
        val message:String = "e5c900aef7c4d512b6ca2b4083bc926c3697da6340f6ca6acfc0c2e05e69ebae"
        val message2:String = "0202d3de886567b1281eaa5687a85e14b4f2922e19b89a3f1014c7932f442c9d"
        //Test with auto generated key pair
        val keyPair = Ed25519Handle.generateKey()
        val privateKey = keyPair.private as Ed25519PrivateKeyParameters
        val signature = Ed25519Handle.signMessage(message,privateKey)
        assert(signature.length > 0)
        println("SUCCESS SIGN, Signature is: " + signature)
        val isVerifySuccess:Boolean =  Ed25519Handle.verifyMessage(message,signature,keyPair.public as Ed25519PublicKeyParameters)
        assert(isVerifySuccess == true)
        //Test with key load from Pem file
        var privateFile:String = "Ed25519/writePrivate.pem"
        var publicFile:String = "Ed25519/writePublic.pem"
        privateFile = ConstValues.PEM_PRIVATE2_ED25519
        publicFile = ConstValues.PEM_PUBLIC_ED25519
        val privateKeyPem: Ed25519PrivateKeyParameters = Ed25519Handle.readPrivateKeyFromPemFile(privateFile)
        val signature2 = Ed25519Handle.signMessage(message,privateKeyPem)
        println("SUCCESS SIGN, Signature 2 is: " + signature2)
        assert(signature2.length > 0)
        val publicKeyPem:CLPublicKey = Ed25519Handle.readPublicKeyFromPemFile(publicFile)
        val publicKeyParameters:Ed25519PublicKeyParameters = Ed25519PublicKeyParameters(publicKeyPem.bytes)
        val isVerifySuccess2:Boolean = Ed25519Handle.verifyMessage(message,signature2,publicKeyParameters)
        assert(isVerifySuccess2 == true)

        //Negative path 1 - verify with a different public key - private key pair
        //private key from the auto generated, public key from the pem file
        val isVerifySuccess3:Boolean = Ed25519Handle.verifyMessage(message,signature,publicKeyParameters)
        assert(isVerifySuccess3 == false)
        //Negative path 2 - verify with a correct public key - private key pair but wrong message
        //private key from the auto generated, public key from the pem file
        val isVerifySuccess4:Boolean = Ed25519Handle.verifyMessage(message2,signature2,publicKeyParameters)
        assert(isVerifySuccess4 == false)
        //Negative path 3 - verify with a correct public key - private key pair but wrong signature
        //private key from the auto generated, public key from the pem file
        val isVerifySuccess5:Boolean = Ed25519Handle.verifyMessage(message,signature,publicKeyParameters)
        assert(isVerifySuccess5 == false)
    }
    fun testGenerateKey() {
        val keyPair = Ed25519Handle.generateKeyPair()
        println("Geneerated key pair, private key is: " + keyPair.private.toString())
        println("Geneerated key pair, public key is: " + keyPair.public.toString())
    }
    fun testWritePemPrivate() {
        //val keyPair: AsymmetricCipherKeyPair = Ed25519Handle.generateKey()
       // Ed25519Handle.writePrivateKeyToPemFile("privateKey1.pem",keyPair)
    }
    fun testLoadPublicKey() {
        val publicKey: CLPublicKey = Ed25519Handle.readPublicKeyFromPemFile(ConstValues.PEM_PUBLIC_ED25519)
        println("Public key is:" + Hex.toHexString(publicKey.bytes))
        assert(Hex.toHexString(publicKey.bytes) == "f1e2dc53cf3999d6fe3a3aef3227d6da44ca47f8a0027f2d17b9f697b2715e47")
        val wrongPemPath:String = "wrongEd25519PublicKey.pem"
        try {
            val publicKey2: CLPublicKey = Ed25519Handle.readPublicKeyFromPemFile(wrongPemPath)
        } catch (e: IOException) {
            println("Error load wrong public key from a wrong path")
        } catch (e:java.lang.Exception) {
            println("Error load wrong public key from a wrong path2")
        } finally {
            println("Error load wrong public key from a wrong path finally")
        }
    }
    fun testLoadPrivateKey() {
        val privateKey: Ed25519PrivateKeyParameters = Ed25519Handle.readPrivateKeyFromPemFile(ConstValues.PEM_PRIVATE_ED25519)
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
    }

}