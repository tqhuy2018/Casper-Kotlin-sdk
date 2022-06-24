package com.casper.sdk.crypto

//import java.security.*
import com.casper.sdk.CasperUtils
import com.casper.sdk.ConstValues
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.crypto.params.*
import org.bouncycastle.crypto.signers.DSADigestSigner
import org.bouncycastle.crypto.signers.ECDSASigner
import org.bouncycastle.crypto.signers.PlainDSAEncoding
import org.bouncycastle.crypto.util.PublicKeyFactory
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECPublicKeySpec
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.bouncycastle.openssl.jcajce.JcaPEMWriter
import org.bouncycastle.util.encoders.Hex
import java.io.*
import java.security.*
import java.security.spec.ECGenParameterSpec
import java.util.*

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
        fun generateKey() : KeyPair{
            Security.addProvider( BouncyCastleProvider())
            val keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", BouncyCastleProvider.PROVIDER_NAME)
            val ecGenParameterSpec = ECGenParameterSpec("secp256k1")
            keyPairGenerator.initialize(ecGenParameterSpec,  SecureRandom())
            val keyPair = keyPairGenerator.generateKeyPair()
            val privateKey : BCECPrivateKey = keyPair.private as BCECPrivateKey
            println("Private key in secp256k1 is:"  + privateKey.toString())
            println("Public key in secp256k1 is:" + keyPair.public.toString())
            return keyPair
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
                val pivk = keyPair.private as BCECPrivateKey
                val pub = keyPair.public as BCECPublicKey
                return  pivk
            } else {
                throw IOException()
            }
            throw IOException()
        }

        //This function read the public key from the Pem file, with input is the file path
        //The result back is a BCECPrivateKey, used for signing message for Secp256k1 Crypto
        //if the file path exists and the file is in correct private key format, then an BCECPrivateKey object is returned
        //Otherwise IOException is thrown
        @Throws(IOException::class)
        fun readPublicKeyFromPemFile(filePath:String) : BCECPublicKey {
            println("In read Public key, Pem file path:" + filePath)
            Security.addProvider(BouncyCastleProvider())
            //val converter =  JcaPEMKeyConverter().setProvider("BC")
            val converter = JcaPEMKeyConverter().setProvider(BouncyCastleProvider())
            val pemKeyPair = PEMParser(FileReader(filePath)).readObject()
            println(" public key type is: " + pemKeyPair)
            if(pemKeyPair is SubjectPublicKeyInfo) {
                val pKey = converter.getPublicKey(pemKeyPair)
                if(pKey is BCECPublicKey) { //Secp256k1 public key
                    println("PUblic key is BCECPublicKey")
                    return pKey
                } else {
                    throw IOException()
                }
            } else if(pemKeyPair is PEMKeyPair) {
                println("Public key is Pem key pair:" + pemKeyPair.publicKeyInfo)
                val keyFactory = KeyFactory.getInstance("ECDSA", BouncyCastleProvider.PROVIDER_NAME)
                val ecSpec = ECNamedCurveTable.getParameterSpec("secp256k1")
                val point = ecSpec.getCurve().decodePoint(pemKeyPair.publicKeyInfo.publicKeyData.bytes)
                val pubSpec = ECPublicKeySpec(point, ecSpec)
                val publicKey = keyFactory.generatePublic(pubSpec) as BCECPublicKey
                println("Public key is: " + publicKey)
                throw  IOException()
            } else {
                throw IOException()
            }
        }
        //This function sign the message in form of string with the given private key
        //The message will be change to Hexa bytes then sign with the private key
        //The result is the signature of hexa string with length 128 and with "02" prefix added
        fun signMessage(message: String, privateKey:BCECPrivateKey) : String {
            val CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1")
            val CURVE =  ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(), CURVE_PARAMS.getH())
            Security.addProvider(BouncyCastleProvider())
            val privateKeyD = privateKey.d
            val param =  ParametersWithRandom( ECPrivateKeyParameters(privateKeyD, CURVE), SecureRandom())
            val signer =  DSADigestSigner( ECDSASigner(),  SHA256Digest(), PlainDSAEncoding.INSTANCE)
            signer.init(true,param)
            signer.update(CasperUtils.fromStringToHexaBytes(message),0,message.length/2)
            val bytesArray: ByteArray = signer.generateSignature()
            return  Hex.toHexString(bytesArray)
        }
        //This function is from scala, both read private key then sign the message
        /*fun loadPemFile(filePath:String,message: String) :String{
            val CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1")
            val CURVE =  ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(), CURVE_PARAMS.getH())
            //val HALF_CURVE_ORDER = CURVE_PARAMS.getN().shiftRight(1)
            Security.addProvider(BouncyCastleProvider())
            val converter =  JcaPEMKeyConverter().setProvider("BC")
            val pemKeyPair = PEMParser(FileReader(filePath)).readObject()
            if(pemKeyPair is org.bouncycastle.openssl.PEMKeyPair) {
                //println("Pem key is of type PEMKEYPAIR")
                val keyPair = converter.getKeyPair(pemKeyPair)
                val pivk = keyPair.private as BCECPrivateKey
                val privateKeyD = pivk.d
                val param =  ParametersWithRandom( ECPrivateKeyParameters(privateKeyD, CURVE), SecureRandom())
                val signer =  DSADigestSigner( ECDSASigner(),  SHA256Digest(), PlainDSAEncoding.INSTANCE)
                signer.init(true,param)
                signer.update(CasperUtils.fromStringToHexaBytes(message),0,message.length/2)
                val bytesArray: ByteArray = signer.generateSignature()
                //println("Sinature is: " + Hex.toHexString(bytesArray))
                return  "02" + Hex.toHexString(bytesArray)
            } else {
                println("PEM KEY IS OF DIF KIND")
            }
            return  ""
           // return BCECPrivateKey()
        }*/
    }
}