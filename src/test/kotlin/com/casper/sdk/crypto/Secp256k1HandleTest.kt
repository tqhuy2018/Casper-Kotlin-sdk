package com.casper.sdk.crypto

import com.casper.sdk.CasperUtils
import com.casper.sdk.ConstValues
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.crypto.params.ECPublicKeyParameters
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.signers.DSADigestSigner
import org.bouncycastle.crypto.signers.ECDSASigner
import org.bouncycastle.crypto.signers.PlainDSAEncoding
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.bouncycastle.util.encoders.Hex
import org.junit.jupiter.api.Test
import java.io.IOException

internal class Secp256k1HandleTest {
    @Test
    fun  testAll() {
        testWriteToPemFile()
        testLoadPrivateKey()
        testLoadPublicKey()
        testSignAndVerifyMessage()
    }
    fun testSignAndVerifyMessage() {
        val message:String = "e5c900aef7c4d512b6ca2b4083bc926c3697da6340f6ca6acfc0c2e05e69ebae"
        val message2:String = "0202d3de886567b1281eaa5687a85e14b4f2922e19b89a3f1014c7932f442c9d"
        //Test with auto generated key pair
        val keyPair = Secp256k1Handle.generateKey()
        val privateKey = keyPair.private as BCECPrivateKey
        val signature = Secp256k1Handle.signMessage(message,privateKey)
        println("Signature secp256k1 is:" + signature)
        assert(signature.length > 0)
        val CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1")
        val CURVE = ECDomainParameters(CURVE_PARAMS.getCurve(),CURVE_PARAMS.g, CURVE_PARAMS.n, CURVE_PARAMS.h)
        val HALF_CURVE_ORDER = CURVE_PARAMS.getN().shiftRight(1)
        val publicKey:BCECPublicKey = keyPair.public as BCECPublicKey
        val publicKeyBytes = publicKey.getQ().getEncoded(true)
        val ecPoint = CURVE.getCurve().decodePoint(publicKeyBytes)
        val ecPkparam =  ECPublicKeyParameters(ecPoint, CURVE)
        val signer =  DSADigestSigner( ECDSASigner(),  SHA256Digest(), PlainDSAEncoding.INSTANCE)
        signer.init(false, ecPkparam)
        signer.update(CasperUtils.fromStringToHexaBytes(message), 0, message.length/2)
        val result = signer.verifySignature(CasperUtils.fromStringToHexaBytes2(signature))
        println("Verify is: " + result)

        //Test with key load from Pem file
        var privatePath:String = ConstValues.PEM_READ_PRIVATE2_SECP256k1
        var publicPath:String = ConstValues.PEM_READ_PUBLIC_SECP256k1
       // privatePath = "Secp256k1/WriteSwiftPrivateKeySecp256k1.pem"
       // publicPath = "Secp256k1/SCALA_SECP256K1_public_key.pem"
        val privateKey2 = Secp256k1Handle.readPrivateKeyFromPemFile(privatePath)
        val publicKey2 = Secp256k1Handle.readPublicKeyFromPemFile(publicPath)
        val signature2 = Secp256k1Handle.signMessage(message,privateKey2)
        println("Signature 2 is: " + signature2)
        val publicKeyBytes2 = publicKey2.getQ().getEncoded(true)
        val ecPoint2 = CURVE.getCurve().decodePoint(publicKeyBytes2)
        val ecPkparam2 =  ECPublicKeyParameters(ecPoint2, CURVE)
        val signer2 =  DSADigestSigner( ECDSASigner(),  SHA256Digest(), PlainDSAEncoding.INSTANCE)
        signer2.init(false, ecPkparam2)
        signer2.update(CasperUtils.fromStringToHexaBytes(message), 0, message.length/2)
        val result2 = signer2.verifySignature(CasperUtils.fromStringToHexaBytes2(signature2))
        print("Verify 2 is: " + result2)

    }
    fun testLoadPublicKey() {
        val publicKey: BCECPublicKey = Secp256k1Handle.readPublicKeyFromPemFile(ConstValues.PEM_READ_PUBLIC_SECP256k1)
        //println("Size of public key:" + Hex.toHexString(publicKey.encoded).length )
        //Negative path 1, load public key from a wrong file format
        try {
            val publicKey2: BCECPublicKey = Secp256k1Handle.readPublicKeyFromPemFile(ConstValues.PEM_READ_PUBLIC_ED25519)
        } catch (e: IOException) {
            println("Error load public key from a wrong file format")
        }
        //Negative path 2, load public key from a non-exist file
        val wrongPemPath:String = "wrongEd25519PublicKey.pem"
        try {
            val publicKey2: BCECPublicKey = Secp256k1Handle.readPublicKeyFromPemFile(wrongPemPath)
        } catch (e: IOException) {
            println("Error load wrong public key from a wrong path")
        }
    }
    fun testLoadPrivateKey() {
        val privateKey: BCECPrivateKey = Secp256k1Handle.readPrivateKeyFromPemFile(ConstValues.PEM_READ_PRIVATE_SECP256k1)
        println("Private key secep256k1: " + Hex.toHexString(privateKey.encoded).length)
        assert(Hex.toHexString(privateKey.encoded).length == 288)
        //assert(Hex.toHexString(privateKey.encoded) == "954b81a59283ec5bcf7186148f9f8b2f1cdfb62ebbf54652ef6a246d6fcc65f2")
        //Negative path 1, load public key from a wrong file format
        try {
            val privateKey2: Ed25519PrivateKeyParameters = Ed25519Handle.readPrivateKeyFromPemFile(ConstValues.PEM_READ_PUBLIC_ED25519)
        } catch (e: IOException) {
            println("Error load private key from a wrong file format")
        }
        //Negative path 2, load private key from a non-exist file
        val wrongPemPath:String = "wrongEd25519PrivateKey.pem"
        try {
            val privateKey2: Ed25519PrivateKeyParameters = Ed25519Handle.readPrivateKeyFromPemFile(wrongPemPath)
        } catch (e: IOException) {
            println("Error load wrong private key from a wrong path")
        }

    }
    fun testWriteToPemFile() {
        val keyPair = Secp256k1Handle.generateKey()
        Secp256k1Handle.writePrivateKeyToPemFile(ConstValues.PEM_WRITE_PRIVATE_SEC256K1,keyPair.private as BCECPrivateKey)
        Secp256k1Handle.writePublicKeyToPemFile(ConstValues.PEM_WRITE_PUBLIC_SECP256K1,keyPair.public as BCECPublicKey)
        //Negative path: Write private pem file to a non-exist path
        try {
            Secp256k1Handle.writePrivateKeyToPemFile("some/nonexist/path/privateSecp256k1.pem",
                keyPair.private as BCECPrivateKey)
        } catch (e: IOException) {
            println("Error write private key to a non-exist file")
        }
        //Negative path: Write public pem file to a non-exist path
        try {
            Secp256k1Handle.writePublicKeyToPemFile("some/nonexist/path/publicSecp256k1.pem",keyPair.public as BCECPublicKey)
        } catch (e: IOException) {
            println("Error write public key to a non-exist file")
        }
    }
}