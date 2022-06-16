package com.casper.sdk.crypto

import com.casper.sdk.CasperUtils
import com.casper.sdk.CasperUtils.Companion.toHex
import com.casper.sdk.ConstValues
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
import org.bouncycastle.crypto.Signer
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.crypto.params.AsymmetricKeyParameter
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import org.bouncycastle.crypto.signers.Ed25519Signer
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey
import java.io.*
import java.security.KeyPair
import java.security.SecureRandom
import java.util.*


const val ed25519PrivateKeyPemFile = "KotlinEd25519PrivateKey.pem"
const val secp256k1PrivateKeyPemFile = "KotlinSecp256k1PrivateKey.pem"
//Ed25519 For this account:0152a685e0edd9060da4a0d52e500d65e21789df3cbfcb878c91ffeaea756d1c53
//Secp256k1 : 0202d3de886567b1281eaa5687a85e14b4f2922e19b89a3f1014c7932f442c9d9635
class Ed25519Handle {
    companion object {
        fun signMessage(messageToSign:String,privateKey:Ed25519PrivateKeyParameters):String {
             //val message10:String = messageToSign
            val signer: Signer = Ed25519Signer()
            signer.init(true, privateKey)
            signer.update(CasperUtils.fromStringToHexaBytes(messageToSign), 0, messageToSign.length/2)
             val signature: ByteArray = signer.generateSignature()
            val signatureHexa : String = signature.toHex()
            return  signatureHexa
        }

        fun generateKey() :AsymmetricCipherKeyPair{
            val secureRandom = SecureRandom()
            val generator: AsymmetricCipherKeyPairGenerator = Ed25519KeyPairGenerator();
            generator.init(Ed25519KeyGenerationParameters(secureRandom))
            val kp : AsymmetricCipherKeyPair = generator.generateKeyPair()
            println("Private:" + kp.private.toString())
            /*val signer: Signer = Ed25519Signer()
            signer.init(true, kp.private)
            val msg = "0173c68fe0f2ffce805fc7a7856ef4d2ec774291159006c0c3dce1b60ed71c8785";
            signer.update(msg.toByteArray(), 0, msg.length)
            val signature: ByteArray = signer.generateSignature()
            val signatureHexa : String = signature.toHex()
            println("singatureHexa:" + signatureHexa)
            //val keyPair:KeyPair =  KeyPair( BCEdDSAPublicKey(kp.getPublic()), new BCEdDSAPrivateKey(kp.getPrivate()));*/
            return kp
        }
        @Throws(IOException::class)
        fun readPrivateKeyFromPemFile(fileName:String) : Ed25519PrivateKeyParameters {
           val classLoader = javaClass.classLoader
           val inputStream = classLoader.getResourceAsStream(fileName)
           try {
               var data: String? = CasperUtils.readFromInputStream(inputStream)
               if(data.isNullOrEmpty()) {
                   throw IOException()
               } else {
                   data = data.replace("-----BEGIN PRIVATE KEY-----","")
                   data = data.replace("-----END PRIVATE KEY-----","")
                   val base64Bytes = Base64.getDecoder().decode(data)
                   val base64BytesReal = base64Bytes.copyOfRange(16,base64Bytes.size)
                   var privateKeyBytes: ByteArray = ByteArray(32)
                   var counter:Int = 0
                   for (bytes in base64BytesReal.toUByteArray()) {
                       privateKeyBytes.set(counter,bytes.toByte())
                       counter ++
                   }
                   val privateKey = Ed25519PrivateKeyParameters(privateKeyBytes,0)
                   return privateKey
               }
           } catch (e:java.lang.Exception){
               println("Error catch")
           } finally {
               println("Error catch finally")
           }
            throw IOException()
        }
    }
}