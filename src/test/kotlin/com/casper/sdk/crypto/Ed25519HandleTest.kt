package com.casper.sdk.crypto

import com.casper.sdk.ConstValues
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import org.bouncycastle.util.encoders.Hex
import org.junit.jupiter.api.Test
import java.io.*

//Test for Ed25519 Crypto, with total of 19 test case
internal class Ed25519HandleTest {
    @Test
    fun  testAll() {
        testWriteToPemFile()
        testLoadPrivateKey()
        testLoadPublicKey()
        testSignAndVerifyMessage()
    }
    //This function do the work of generate the (private/public) key pair and then write them to pem files
    //Total test case: 4 - 2 for positive path and 2 for negative path
    fun testWriteToPemFile() {
        //Generate the public/private key pair
        val keyPair = Ed25519Handle.generateKey()
        //Write private key to pem file
        Ed25519Handle.writePrivateKeyToPemFile(ConstValues.PEM_WRITE_PRIVATE_ED25519,keyPair.private as Ed25519PrivateKeyParameters)
        //Write public key to pem file
        Ed25519Handle.writePublicKeyToPemFile(ConstValues.PEM_WRITE_PUBLIC_ED25519,keyPair.public as Ed25519PublicKeyParameters)
        //Negative path: Write private pem file to a non-exist path - IOException is thrown
        try {
            Ed25519Handle.writePrivateKeyToPemFile("some/nonexist/path/privateEd25519.pem",
                keyPair.private as Ed25519PrivateKeyParameters)
        } catch (e:IOException) {
            println("Error write private key to a non-exist file")
        }
        //Negative path: Write public pem file to a non-exist path - IOException is thrown
        try {
            Ed25519Handle.writePublicKeyToPemFile("some/nonexist/path/publicEd25519.pem",keyPair.public as Ed25519PublicKeyParameters)
        } catch (e:IOException) {
            println("Error write public key to a non-exist file")
        }
    }
    //This function test the sign and verify a message using Ed25519 Crypto
    //Total test case: 7 in which 4 for positive test case, 3 for negative test case
    fun  testSignAndVerifyMessage() {
        val message = "e5c900aef7c4d512b6ca2b4083bc926c3697da6340f6ca6acfc0c2e05e69ebae"
        val message2 = "0202d3de886567b1281eaa5687a85e14b4f2922e19b89a3f1014c7932f442c9d"
        //Test with auto generated key pair
        val keyPair = Ed25519Handle.generateKey()
        val privateKey = keyPair.private as Ed25519PrivateKeyParameters
        val signature = Ed25519Handle.signMessage(message,privateKey)
        assert(signature.length > 0)
        val isVerifySuccess:Boolean =  Ed25519Handle.verifyMessage(message,signature,keyPair.public as Ed25519PublicKeyParameters)
        assert(isVerifySuccess == true)
        //Test with key load from Pem file
        /*var privateFile = "Ed25519/writePrivate.pem"
        var publicFile = "Ed25519/writePublic.pem"
        privateFile = ConstValues.PEM_READ_PRIVATE2_ED25519
        publicFile = ConstValues.PEM_READ_PUBLIC_ED25519*/
        val privateKeyPem: Ed25519PrivateKeyParameters = Ed25519Handle.readPrivateKeyFromPemFile(ConstValues.PEM_READ_PRIVATE2_ED25519)
        val signature2 = Ed25519Handle.signMessage(message,privateKeyPem)
        assert(signature2.length > 0)
        val publicKeyParameters:Ed25519PublicKeyParameters = Ed25519Handle.readPublicKeyFromPemFile(ConstValues.PEM_READ_PUBLIC_ED25519)
        val isVerifySuccess2:Boolean = Ed25519Handle.verifyMessage(message,signature2,publicKeyParameters)
        assert(isVerifySuccess2 == true)

        //Negative path 1 - verify with a different public key - private key pair
        //private key from the auto generated, public key from the pem file
        val isVerifySuccess3:Boolean = Ed25519Handle.verifyMessage(message,signature,publicKeyParameters)
        assert(isVerifySuccess3 == false)
        //Negative path 2 - verify with a correct public key - private key pair but wrong message
        //private key is from pem file, public key is from pem file
        val isVerifySuccess4:Boolean = Ed25519Handle.verifyMessage(message2,signature2,publicKeyParameters)
        assert(isVerifySuccess4 == false)
        //Negative path 3 - verify with a correct public key - private key pair but wrong signature
        //private key is from the auto generated, public key is from the auto generated
        val isVerifySuccess5:Boolean = Ed25519Handle.verifyMessage(message,signature,publicKeyParameters)
        assert(isVerifySuccess5 == false)
    }
    //This function test the load public key from Pem file, the public key is in Ed25519PublicKeyParameters format
    //Total test case: 4 with 1 for positive path, 3 for negative path
    fun testLoadPublicKey() {
        val publicKey: Ed25519PublicKeyParameters = Ed25519Handle.readPublicKeyFromPemFile(ConstValues.PEM_READ_PUBLIC_ED25519)
        assert(Hex.toHexString(publicKey.encoded) == "fb0afcf77ef310bc65cee526c0d6197b9803d3a7d73de5d7fef93ee5988e32a3")
        assert(Hex.toHexString(publicKey.encoded).length == 64)
        //Negative path 1, load public key from a wrong file format
        try {
            Ed25519Handle.readPublicKeyFromPemFile(ConstValues.PEM_READ_PRIVATE_ED25519)
        } catch (e: IOException) {
            println("Error load public key from a wrong file format")
        }
        //Negative path 2, load public key from a wrong file format
        try {
            Ed25519Handle.readPublicKeyFromPemFile(ConstValues.PEM_READ_PUBLIC_SECP256k1)
        } catch (e: IOException) {
            println("Error load public key from a wrong file format")
        }
        //Negative path 3, load public key from a non-exist file
        val wrongPemPath = "wrongEd25519PublicKey.pem"
        try {
            Ed25519Handle.readPublicKeyFromPemFile(wrongPemPath)
        } catch (e: IOException) {
            println("Error load wrong public key from a wrong path")
        }
    }
    //This function test the load private key from Pem file, the public key is in Ed25519PrivateKeyParameters format
    //Total test case: 4 with 1 for positive path, 3 for negative path
    fun testLoadPrivateKey() {
        val privateKey: Ed25519PrivateKeyParameters = Ed25519Handle.readPrivateKeyFromPemFile(ConstValues.PEM_READ_PRIVATE_ED25519)
        assert(Hex.toHexString(privateKey.encoded) == "954b81a59283ec5bcf7186148f9f8b2f1cdfb62ebbf54652ef6a246d6fcc65f2")
        //Negative path 1, load public key from a wrong file format
        try {
           Ed25519Handle.readPrivateKeyFromPemFile(ConstValues.PEM_READ_PUBLIC_ED25519)
        } catch (e: IOException) {
            println("Error load private key from a wrong file format")
        }
        //Negative path 2, load public key from a wrong file format
        try {
            Ed25519Handle.readPrivateKeyFromPemFile(ConstValues.PEM_READ_PRIVATE2_SECP256k1)
        } catch (e: IOException) {
            println("Error load private key from a wrong file format")
        }
        //Negative path 3, load private key from a non-exist file
        val wrongPemPath = "wrongEd25519PrivateKey.pem"
        try {
            Ed25519Handle.readPrivateKeyFromPemFile(wrongPemPath)
        } catch (e: IOException) {
            println("Error load wrong private key from a wrong path")
        }
    }

}