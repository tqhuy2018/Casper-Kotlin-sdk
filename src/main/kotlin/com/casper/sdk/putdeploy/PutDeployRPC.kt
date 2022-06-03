package com.casper.sdk.putdeploy

import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.crypto.params.ECKeyGenerationParameters
import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.openssl.*
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.jcajce.*
import org.bouncycastle.util.encoders.Base64.*
import org.bouncycastle.util.io.pem.PemReader
import java.io.*
import java.security.SecureRandom


const val keyFilename = "ReadSwiftPrivateKeySecp256k1.pem"

class PutDeployRPC {

    @Throws(Exception::class)
    fun readPrivateKey() {
        val keyText = InputStreamReader(
            FileInputStream("src/main/resources/$keyFilename")
        ).use {
            it.readText()
                //.replace("-----BEGIN EC PRIVATE KEY-----\n", "")
              //  .replace("-----END EC PRIVATE KEY-----", "")
        }
        println("file content:${keyText}")
       // val encoded = org.bouncycastle.util.encoders.Base64.decode(keyText)
      //  println("encoded base64 content:${encoded}")
       val curve = ECNamedCurveTable.getParameterSpec("secp256k1")
        val domainParams =  ECDomainParameters(curve.curve,curve.g,curve.n,curve.h,curve.seed)
        val secureRandom = SecureRandom()
        var keyParams = ECKeyGenerationParameters(domainParams,secureRandom)

        val pr = PemReader(StringReader(keyText))
        val pemp: PEMParser = PEMParser(pr)
        val pemObject = pemp.readObject()
        val pkp:PEMKeyPair =pemp.readObject() as PEMKeyPair
        println(pkp.privateKeyInfo.privateKey.toString())

       // println(pemObject.hashCode())
       // val keyPair = pr.readPemObject() as AsymmetricCipherKeyPair
       // val keyPair = pr.readPemObject() as PemObject

        //val privateKeyParams = keyPair.private as ECPrivateKeyParameters
       // val publicKeyParams = keyPair.public as ECPublicKeyParameters
       // println("Private key:${privateKeyParams.toString()}")
        //return  privateKeyParams
    }
    /* val key = String(Files.readAllBytes(file.toPath()), Charset.defaultCharset())
       val privateKeyPEM = key
           .replace("-----BEGIN PRIVATE KEY-----", "")
           .replace(System.lineSeparator().toRegex(), "")
           .replace("-----END PRIVATE KEY-----", "")
       val encoded: ByteArray = org.bouncycastle.util.encoders.Base64.decode(privateKeyPEM)
       val keyFactory = KeyFactory.getInstance("Secp256k1")
       val keySpec = PKCS8EncodedKeySpec(encoded)
       val ret:SecP256K1Curve = keyFactory.generatePrivate(keySpec) as SecP256K1Curve
       println("key : ${ret.toString()}")
       return ret*/
    /*
    fun getKeyFromClassPath(keyFilename: String): PrivateKey? {
        val loader = Thread.currentThread().contextClassLoader
        val stream = loader.getResourceAsStream("src/main/resources/$keyFilename")
            ?: throw java.security.cert.CertificateException("Could not read private key from classpath:resources/$keyFilename")
        val br = BufferedReader(InputStreamReader(stream))
        return try {
            Security.addProvider(BouncyCastleProvider())
            val pp = PEMParser(br)
            val pemKeyPair: PEMKeyPair = pp.readObject() as PEMKeyPair
            val kp: KeyPair = JcaPEMKeyConverter().getKeyPair(pemKeyPair)
            pp.close()
            kp.private
        } catch (ex: IOException) {
            throw java.security.cert.CertificateException("Could not read private key from classpath", ex)
        }
    }*/
}