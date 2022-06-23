package com.casper.sdk.crypto

import com.casper.sdk.CasperUtils
import com.casper.sdk.CasperUtils.Companion.toHex
import com.casper.sdk.ConstValues
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
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.bouncycastle.openssl.jcajce.JcaPEMWriter
import org.bouncycastle.util.encoders.Hex
import java.io.*
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.Security
import java.security.spec.ECGenParameterSpec
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
        fun verifyMessage(originalMessage:String,signature:String,publicKey:Ed25519PublicKeyParameters) : Boolean{
            val verifier: Signer = Ed25519Signer()
            verifier.init(false, publicKey)
            verifier.update(CasperUtils.fromStringToHexaBytes(originalMessage), 0, originalMessage.length/2)
            val verified = verifier.verifySignature(CasperUtils.fromStringToHexaBytes2(signature))
            return verified
        }
        fun writePrivateKeyToPemFile(filePath:String, forKeyPair:AsymmetricCipherKeyPair) {
            Security.addProvider( BouncyCastleProvider())
            //val converter =  JcaPEMKeyConverter().setProvider("BC")
            val stringWriter = StringWriter(4096)
            val writer = JcaPEMWriter(stringWriter)
            val publicKeyParameters = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(forKeyPair.public)
            val privateKeyParameters = PrivateKeyInfoFactory.createPrivateKeyInfo(forKeyPair.private)
            writer.writeObject(PEMKeyPair(publicKeyParameters, privateKeyParameters))
        }

         fun generateKey2() : KeyPair {
             Security.addProvider(BouncyCastleProvider())
             //val converter =  JcaPEMKeyConverter().setProvider("BC")
             val keyPairGenerator = KeyPairGenerator.getInstance("Ed25519", BouncyCastleProvider.PROVIDER_NAME)
             val ecGenParameterSpec = ECGenParameterSpec("Ed25519")
             keyPairGenerator.initialize(ecGenParameterSpec, SecureRandom())
             val ret:KeyPair = keyPairGenerator.generateKeyPair()
             val privateKey = ret.private
             val publicKey = ret.public
             println("Public key is:" + publicKey.toString())
             println("Private key is: " + privateKey.toString())
             return ret
         }
         fun generateKey() :AsymmetricCipherKeyPair{
             val secureRandom = SecureRandom()
             val generator: AsymmetricCipherKeyPairGenerator = Ed25519KeyPairGenerator();
             generator.init(Ed25519KeyGenerationParameters(secureRandom))
             val kp : AsymmetricCipherKeyPair = generator.generateKeyPair()
             println("Private:" + Hex.toHexString(kp.private.toString().toByteArray()))
             println("Public key generated is: " + kp.public.toString())
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
        //from scala
        @Throws(IOException::class)
        fun readPrivateKeyFromPemFile(filePath:String) : Ed25519PrivateKeyParameters {
            Security.addProvider(BouncyCastleProvider())
            val converter =  JcaPEMKeyConverter().setProvider("BC")
            val pemKeyPair = PEMParser(FileReader(filePath)).readObject()
            if(pemKeyPair is PrivateKeyInfo) {
               // val privKey = converter.getPrivateKey(pemKeyPair)
                val privkeyparam = PrivateKeyFactory.createKey(pemKeyPair)
                return privkeyparam as Ed25519PrivateKeyParameters
            } else {
                throw IOException()
            }
        }
        //from scala
        fun generateKeyPair() : KeyPair {
            Security.addProvider(BouncyCastleProvider())
            val keyPairGenerator = KeyPairGenerator.getInstance("Ed25519", BouncyCastleProvider.PROVIDER_NAME)
            val ecGenParameterSpec =  ECGenParameterSpec("Ed25519")
            keyPairGenerator.initialize(ecGenParameterSpec,  SecureRandom())
            return keyPairGenerator.generateKeyPair()
        }
        @Throws(IOException::class)
        fun readPublicKeyFromPemFile(filePath:String) : CLPublicKey {
            Security.addProvider(BouncyCastleProvider())
            val converter = JcaPEMKeyConverter().setProvider(BouncyCastleProvider())
            val pemKeyPair = PEMParser(FileReader(filePath)).readObject()
            val ret:CLPublicKey = CLPublicKey()
            if(pemKeyPair is SubjectPublicKeyInfo) {
                val pKey = converter.getPublicKey(pemKeyPair)
                if(pKey is BCEdDSAPublicKey) { //Ed25519 public key
                    val bytes:ByteArray = pKey.pointEncoding
                    ret.bytes = bytes
                    ret.keyAlgorithm = ConstValues.ED25519_PREFIX
                    return ret
                } else if(pKey is BCECPublicKey) { //Secp256k1 public key

                }

            } else {
                throw IOException()
            }
            return ret
        }
    }
}