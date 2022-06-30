package com.casper.sdk.crypto

import com.casper.sdk.CasperUtils
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.crypto.params.*
import org.bouncycastle.crypto.signers.DSADigestSigner
import org.bouncycastle.crypto.signers.ECDSASigner
import org.bouncycastle.crypto.signers.PlainDSAEncoding
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.bouncycastle.openssl.jcajce.JcaPEMWriter
import org.bouncycastle.util.encoders.Hex
import java.io.*
import java.security.*
import java.security.spec.ECGenParameterSpec
//Class for handling Secp256k1 Crypto tasks
//The following tasks are being implemented:
//Sign message
//Verify message
//Generate key pair
//Read private/public key from Pem file
//Write private/public key from Pem file
class Secp256k1Handle {

    companion object {

        //Write private key to a pem file, base on the private key as BCECPrivateKey
        //If the folder for the file is correct, the pem file is written
        //Otherwise IOException is thrown
        @Throws(IOException::class)
        fun writePrivateKeyToPemFile(filePath:String, forPrivateKey: BCECPrivateKey) {
            Security.addProvider( BouncyCastleProvider())
            val stringWriter = StringWriter(4096)
            val writer = JcaPEMWriter(stringWriter)
            writer.writeObject(forPrivateKey)
            writer.flush()
            val strPem = stringWriter.toString()
            try {
                File(filePath).writeText(strPem)
            } catch (e:IOException) {
                throw IOException()
            }
        }
        //Write public key to a pem file, base on the private key as BCECPublicKey
        //If the folder for the file is correct, the pem file is written
        //Otherwise IOException is thrown
        @Throws(IOException::class)
        fun writePublicKeyToPemFile(filePath:String, forPublicKey: BCECPublicKey) {
            Security.addProvider( BouncyCastleProvider())
            val stringWriter = StringWriter(4096)
            val writer = JcaPEMWriter(stringWriter)
            writer.writeObject(forPublicKey)
            writer.flush()
            val strPem = stringWriter.toString()
            try {
                File(filePath).writeText(strPem)
            } catch (e:IOException) {
                throw IOException()
            }
        }

        //Generate the Secp256k1 key pair, from this key pair you can write the private/public key to Pem file
        //Or use the keys for sign/verify message for Secp256k1 Crypto
        //The private,public key pair is of type (BCECPrivateKey,BCECPublicKey)
        fun generateKey(): KeyPair {
            Security.addProvider(BouncyCastleProvider())
            val keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", BouncyCastleProvider.PROVIDER_NAME)
            val ecGenParameterSpec = ECGenParameterSpec("secp256k1")
            keyPairGenerator.initialize(ecGenParameterSpec, SecureRandom())
            return keyPairGenerator.generateKeyPair()
        }
        //This function read the private key from the Pem file, with input is the file path
        //The result back is a BCECPrivateKey, used for signing message for Secp256k1 Crypto
        //if the file path exists and the file is in correct private key format, then an BCECPrivateKey object is returned
        //Otherwise IOException is thrown
        @Throws(IOException::class)
        fun readPrivateKeyFromPemFile(filePath:String) : BCECPrivateKey {
            Security.addProvider(BouncyCastleProvider())
            val converter =  JcaPEMKeyConverter().setProvider("BC")
            val pemKeyPair = PEMParser(FileReader(filePath)).readObject()
            if(pemKeyPair is org.bouncycastle.openssl.PEMKeyPair) {
                val keyPair = converter.getKeyPair(pemKeyPair)
                return keyPair.private as BCECPrivateKey
            } else {
                throw IOException()
            }
        }

        //This function read the public key from the Pem file, with input is the file path
        //The result back is a BCECPrivateKey, used for signing message for Secp256k1 Crypto
        //if the file path exists and the file is in correct private key format, then an BCECPrivateKey object is returned
        //Otherwise IOException is thrown
        @Throws(IOException::class)
        fun readPublicKeyFromPemFile(filePath:String) : BCECPublicKey {
            Security.addProvider(BouncyCastleProvider())
            val converter = JcaPEMKeyConverter().setProvider(BouncyCastleProvider())
            val pemKeyPair = PEMParser(FileReader(filePath)).readObject()
            if(pemKeyPair is SubjectPublicKeyInfo) {
                val pKey = converter.getPublicKey(pemKeyPair)
                if(pKey is BCECPublicKey) { //Secp256k1 public key
                    return pKey
                } else {
                    throw IOException()
                }
            }  else {
                throw IOException()
            }
        }
        //This function sign the message in form of string with the given private key
        //The message will be change to Hexa bytes then sign with the private key
        //The result is the signature of hexa string with length 128 and with "02" prefix added
        fun signMessage(message: String, privateKey:BCECPrivateKey) : String {
            val CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1")
            val CURVE =  ECDomainParameters(CURVE_PARAMS.curve, CURVE_PARAMS.g, CURVE_PARAMS.n, CURVE_PARAMS.h)
            Security.addProvider(BouncyCastleProvider())
            val privateKeyD = privateKey.d
            val param =  ParametersWithRandom( ECPrivateKeyParameters(privateKeyD, CURVE), SecureRandom())
            val signer =  DSADigestSigner( ECDSASigner(),  SHA256Digest(), PlainDSAEncoding.INSTANCE)
            signer.init(true,param)
            signer.update(CasperUtils.fromStringToHexaBytes(message),0,message.length/2)
            val bytesArray: ByteArray = signer.generateSignature()
            return  Hex.toHexString(bytesArray)
        }
        /*
     This function verifies the message in form of String, and signature in form of hexa string, with Secp256k1 public key in form of BCECPublicKey object
     If the signature is generated from the private key and the verification use the corresponding public key
     of the private key for the signed message, then the true value is returned, otherwise false value returned
      */
        fun verifyMessage(message: String,publicKey: BCECPublicKey,signature:String) : Boolean {
            val CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1")
            val CURVE = ECDomainParameters(CURVE_PARAMS.getCurve(),CURVE_PARAMS.g, CURVE_PARAMS.n, CURVE_PARAMS.h)
            val publicKeyBytes = publicKey.getQ().getEncoded(true)
            val ecPoint = CURVE.getCurve().decodePoint(publicKeyBytes)
            val ecPkparam =  ECPublicKeyParameters(ecPoint, CURVE)
            val signer =  DSADigestSigner( ECDSASigner(),  SHA256Digest(), PlainDSAEncoding.INSTANCE)
            signer.init(false, ecPkparam)
            signer.update(CasperUtils.fromStringToHexaBytes(message), 0, message.length/2)
            val result = signer.verifySignature(CasperUtils.fromStringToHexaBytes2(signature))
            return result
        }
    }
}