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
        fun compressPubKey(pubKey: BigInteger): String? {
            val pubKeyYPrefix = if (pubKey.testBit(0)) "03" else "02"
            val pubKeyHex = pubKey.toString(16)
            val pubKeyX = pubKeyHex.substring(0, 64)
            return pubKeyYPrefix + pubKeyX
        }
        fun signScala(message: String) : String {
            var ret:String = ""
            val signer =  DSADigestSigner( ECDSASigner(),  SHA256Digest(), PlainDSAEncoding.INSTANCE)
            val privKey:ECPrivateKey = readPrivateKeyFromPemFile2("")
            return ret
        }
        fun signMessage3(message:String) :String{
            val privKey:ECPrivateKey = readPrivateKeyFromPemFile2("")
            //val privKey = BigInteger("97ddae0f3a25b92268175400149d65d6887b9cefaf28ea2c078e05cdc15a3c0a", 16)
            val pubKey = Sign.publicKeyFromPrivate(privKey.s)
            val keyPair = ECKeyPair(privKey.s, pubKey)
            println("Private key: " + privKey.s.toString(16))
            println("Public key: " + pubKey.toString(16))
            System.out.println("Public key (compressed): " + compressPubKey(pubKey))
            //val msg = "Message for signing"
            //val msgHash: ByteArray = message.toByteArray()
            val msgHash: ByteArray = CasperUtils.fromStringToHexaBytes(message)
            val signature =  Sign.signMessage(msgHash, keyPair, false)
            //println("Msg: $msg")
            println("Msg hash: " + Hex.toHexString(msgHash))
            System.out.printf("Signature: [ r = %s, s = %s]\n",
                Hex.toHexString(signature.r),
                Hex.toHexString(signature.s))
            println("sign message is:" + "02" + Hex.toHexString(signature.r) + Hex.toHexString(signature.s))
            return  "02" + Hex.toHexString(signature.r) + Hex.toHexString(signature.s)
        }
        fun GenerateKey2() {
            //val privKey:ECPrivateKey = readPrivateKeyFromPemFile2("")
            val privKey = BigInteger("97ddae0f3a25b92268175400149d65d6887b9cefaf28ea2c078e05cdc15a3c0a", 16)
            val pubKey = Sign.publicKeyFromPrivate(privKey)
            val keyPair = ECKeyPair(privKey, pubKey)
            println("Private key: " + privKey.toString(16))
            println("Public key: " + pubKey.toString(16))
            System.out.println("Public key (compressed): " + compressPubKey(pubKey))
            val msg = "Message for signing"
            val msgHash: ByteArray = msg.toByteArray()
            val signature = Sign.signMessage(msgHash, keyPair, false)
            println("Msg: $msg")
            println("Msg hash: " + Hex.toHexString(msgHash))
            System.out.printf("Signature: [ r = %s, s = %s]\n",
                Hex.toHexString(signature.r),
                Hex.toHexString(signature.s))
        }
        @Throws(NoSuchAlgorithmException::class,
            NoSuchProviderException::class,
            InvalidAlgorithmParameterException::class)
        fun GenerateKeys(): KeyPair? {
            val ecSpec: ECParameterSpec = ECNamedCurveTable.getParameterSpec("B-571")
            val g = KeyPairGenerator.getInstance("ECDSA")
            g.initialize(ecSpec, SecureRandom())
            return g.generateKeyPair()
        }
        fun signMessage(messageToSign:String,privateKey:String):String {
           /* val domain: ECDomainParameters = ECDomainParameters()
            val signer = ECDSASigner()
            signer.init(true, ECPrivateKeyParameters(BigInteger(1, getPrivateKey()), domain))
            val components = signer.generateSignature(CasperUtils.fromStringToHexaBytes(messageToSign))
            val signature = ECSignature(components[0], components[1])*/
            val pair: KeyPair = GenerateKeys() as KeyPair
            return ""
            val ecdsaSign: Signature = Signature.getInstance("SHA256withECDSA", "BC")
            ecdsaSign.initSign(pair.private)
            //val plainText : String = "Hello World"
            ecdsaSign.update(messageToSign.toByteArray())
            val signature: ByteArray = ecdsaSign.sign()
            println("Signagure secp256k1: " + signature)
            return signature.toString()
        }
        @Throws(IOException::class)
        fun readPrivateKeyFromPemFile2(pemFile:String):ECPrivateKey{
            val privateKeyString:String = "-----BEGIN EC PRIVATE KEY-----\n" +
            "MHQCAQEEICCr27ihEOEpJ8Z+w7VS1TfJfE9mMhBmfunA5sQF/N/MoAcGBSuBBAAK\n" +
            "oUQDQgAE096IZWexKB6qVoeoXhS08pIuGbiaPxAUx5MvRCydljXYoTIHKB0bdqWt\n" +
            "3E0YoQC43Jyh2NlidryRe7OtTZ8TCA==\n" +
            "-----END EC PRIVATE KEY-----\n"
            val pemKey = PEMParser(StringReader(privateKeyString)).readObject() as PEMKeyPair
            val ecKey = JcaPEMKeyConverter().getPrivateKey(pemKey.privateKeyInfo) as ECPrivateKey
            return  ecKey
        }
        //From scala version
        fun loadPemFile(filePath:String,message: String) :String{
            val CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1")
            val CURVE =  ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(), CURVE_PARAMS.getH())
            val HALF_CURVE_ORDER = CURVE_PARAMS.getN().shiftRight(1)
            Security.addProvider(BouncyCastleProvider())
            val converter =  JcaPEMKeyConverter().setProvider("BC")
            val pemKeyPair = PEMParser(FileReader(filePath)).readObject()
            if(pemKeyPair is org.bouncycastle.openssl.PEMKeyPair) {
                println("Pem key is of type PEMKEYPAIR")
                val keyPair = converter.getKeyPair(pemKeyPair)
                val pivk = keyPair.private as BCECPrivateKey
                val privateKeyD = pivk.d
                val param =  ParametersWithRandom( ECPrivateKeyParameters(privateKeyD, CURVE), SecureRandom())
                val signer =  DSADigestSigner( ECDSASigner(),  SHA256Digest(), PlainDSAEncoding.INSTANCE)
                signer.init(true,param)
                signer.update(CasperUtils.fromStringToHexaBytes(message),0,message.length/2)
                val bytesArray: ByteArray = signer.generateSignature()
                println("Sinature is: " + Hex.toHexString(bytesArray))
                return  "02" + Hex.toHexString(bytesArray)
            } else {
                println("PEM KEY IS OF DIF KIND")
            }
            return  ""
           // return BCECPrivateKey()
        }
    }
}