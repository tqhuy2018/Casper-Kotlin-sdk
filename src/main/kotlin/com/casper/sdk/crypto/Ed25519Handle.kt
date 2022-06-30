package com.casper.sdk.crypto

import com.casper.sdk.CasperUtils
import com.casper.sdk.CasperUtils.Companion.toHex
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
import org.bouncycastle.crypto.Signer
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import org.bouncycastle.crypto.signers.Ed25519Signer
import org.bouncycastle.crypto.util.PrivateKeyFactory
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.bouncycastle.openssl.jcajce.JcaPEMWriter
import java.io.*
import java.security.SecureRandom
import java.security.Security
//Class for handling Ed25519 Crypto tasks
//The following tasks are being implemented:
//Sign message
//Verify message
//Generate key pair
//Read private/public key from Pem file
//Write private/public key from Pem file
class Ed25519Handle {
    companion object {
        /*
        This function signs the message in form of String, with Ed25519 private key in form of Ed25519PrivateKeyParameters object
        The signature is a hexa string
         */
        fun signMessage(messageToSign:String,privateKey:Ed25519PrivateKeyParameters):String {
             //val message10:String = messageToSign
            val signer: Signer = Ed25519Signer()
            signer.init(true, privateKey)
            signer.update(CasperUtils.fromStringToHexaBytes(messageToSign), 0, messageToSign.length/2)
             val signature: ByteArray = signer.generateSignature()
            val signatureHexa : String = signature.toHex()
            return  signatureHexa
        }
        /*
       This function verifies the message in form of String, and signature in form of hexa string, with Ed25519 public key in form of Ed25519PublicKeyParameters object
       If the signature is generated from the private key and the verification use the corresponding public key
       of the private key for the signed message, then the true value is returned, otherwise false value returned
        */
        fun verifyMessage(originalMessage:String,signature:String,publicKey:Ed25519PublicKeyParameters) : Boolean{
            val verifier: Signer = Ed25519Signer()
            verifier.init(false, publicKey)
            verifier.update(CasperUtils.fromStringToHexaBytes(originalMessage), 0, originalMessage.length/2)
            val verified = verifier.verifySignature(CasperUtils.fromStringToHexaBytes2(signature))
            return verified
        }
        //Write private key to a pem file, base on the private key as Ed25519PrivateKeyParameters
        //If the folder for the file is correct, the pem file is written
        //Otherwise IOException is thrown
        @Throws(IOException::class)
        fun writePrivateKeyToPemFile(filePath:String, forPrivateKey:Ed25519PrivateKeyParameters) {
            Security.addProvider( BouncyCastleProvider())
            val stringWriter = StringWriter(4096)
            val writer = JcaPEMWriter(stringWriter)
            writer.writeObject(PrivateKeyInfoFactory.createPrivateKeyInfo(forPrivateKey))
            writer.flush()
            val strPem = stringWriter.toString()
            try {
                File(filePath).writeText(strPem)
            } catch (e:IOException) {
                throw IOException()
            }
        }
        //Write public key to a pem file, base on the private key as Ed25519PublicKeyParameters
        //If the folder for the file is correct, the pem file is written
        //Otherwise IOException is thrown
        @Throws(IOException::class)
        fun writePublicKeyToPemFile(filePath:String, forPublicKey:Ed25519PublicKeyParameters) {
            Security.addProvider( BouncyCastleProvider())
            val stringWriter = StringWriter(4096)
            val writer = JcaPEMWriter(stringWriter)
            writer.writeObject(SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(forPublicKey))
            writer.flush()
            val strPem = stringWriter.toString()
            File(filePath).writeText(strPem)
        }
        //Generate the Ed25519 key pair, from this key pair you can write the private/public key to Pem file
        //Or use the keys for sign/verify message for Ed25519 Crypto
         fun generateKey() :AsymmetricCipherKeyPair{
             val secureRandom = SecureRandom()
             val generator: AsymmetricCipherKeyPairGenerator = Ed25519KeyPairGenerator();
             generator.init(Ed25519KeyGenerationParameters(secureRandom))
             val kp : AsymmetricCipherKeyPair = generator.generateKeyPair()
             return kp
         }
        //Read private key from pem file, return the pem file as Ed25519PrivateKeyParameters object
        //if the file path exists and the file is in correct private key format, then an Ed25519PrivateKeyParameters object is returned
        //Otherwise IOException is thrown
        @Throws(IOException::class)
        fun readPrivateKeyFromPemFile(filePath:String) : Ed25519PrivateKeyParameters {
            Security.addProvider(BouncyCastleProvider())
            val pemKeyPair = PEMParser(FileReader(filePath)).readObject()
            if(pemKeyPair is PrivateKeyInfo) {
                val privkeyparam = PrivateKeyFactory.createKey(pemKeyPair)
                return privkeyparam as Ed25519PrivateKeyParameters
            } else {
                throw IOException()
            }
        }
        //Read public key from pem file, return the pem file as CLPublicKey object
        //The CLPublicKey object hold the main information of the PublicKey as ByteArray
        //if the file path exists and the file is in correct public key format, then an CLPublicKey object is returned
        //Otherwise IOException is thrown
        @Throws(IOException::class)
        fun readPublicKeyFromPemFile(filePath:String) : Ed25519PublicKeyParameters {
            Security.addProvider(BouncyCastleProvider())
            val converter = JcaPEMKeyConverter().setProvider(BouncyCastleProvider())
            val pemKeyPair = PEMParser(FileReader(filePath)).readObject()
            if(pemKeyPair is SubjectPublicKeyInfo) {
                val pKey = converter.getPublicKey(pemKeyPair)
                if(pKey is BCEdDSAPublicKey) { //Ed25519 public key
                    val bytes:ByteArray = pKey.pointEncoding
                    val ret : Ed25519PublicKeyParameters = Ed25519PublicKeyParameters(bytes)
                    return ret
                } else {
                    throw IOException()
                }
            } else {
                throw IOException()
            }
        }
    }
}