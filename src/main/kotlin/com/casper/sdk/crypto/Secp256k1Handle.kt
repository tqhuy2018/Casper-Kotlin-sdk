package com.casper.sdk.crypto

//import java.security.*
import com.casper.sdk.CasperUtils
import org.bouncycastle.asn1.sec.SECNamedCurves
import org.bouncycastle.crypto.generators.ECKeyPairGenerator
import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.crypto.params.ECKeyGenerationParameters
import org.bouncycastle.crypto.params.ECPrivateKeyParameters
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.spec.ECParameterSpec
import org.bouncycastle.util.encoders.Hex
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Sign
import java.io.IOException
import java.math.BigInteger
import java.security.*
import java.util.*
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.crypto.params.ParametersWithRandom
import org.bouncycastle.crypto.signers.DSADigestSigner
import org.bouncycastle.crypto.signers.ECDSASigner
import org.bouncycastle.crypto.signers.PlainDSAEncoding
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import java.io.FileReader
import java.io.StringReader
import java.security.interfaces.ECPrivateKey

class Secp256k1Handle {

    companion object {

        fun generateKeyPair() {
            val string:String = "a1e3ce382dafebc4ed9a9efc7d771f669745e2a88b33f2b5eb4efa8c47721346"
            val privateKey: ByteArray = CasperUtils.fromStringToHexaBytes(string)
            val ec = ECKeyPair.create(BigInteger(1, privateKey))
//ECKeyPair ec =  ECKeyPair.create(privateKey); // needs web3j utilitiy classes
//ECKeyPair ec =  ECKeyPair.create(privateKey); // needs web3j utilitiy classes
            println("Raw private key to show: " + ec.privateKey.toString(16))
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

        //This function read the private key from the Pem file, with input is the file path
        //The result back is a BCECPrivateKey, used for signing message for Secp256k1 Crypto
        @Throws(IOException::class)
        fun readPrivateKeyFromPemFile(filePath:String) : BCECPrivateKey {
            Security.addProvider(BouncyCastleProvider())
            val converter =  JcaPEMKeyConverter().setProvider("BC")
            val pemKeyPair = PEMParser(FileReader(filePath)).readObject()
            if(pemKeyPair is org.bouncycastle.openssl.PEMKeyPair) {
                val keyPair = converter.getKeyPair(pemKeyPair)
                val pivk = keyPair.private as BCECPrivateKey
                return  pivk
            } else {
                throw IOException()
            }
            throw IOException()
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
            return  "02" + Hex.toHexString(bytesArray)
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