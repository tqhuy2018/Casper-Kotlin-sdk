package com.casper.sdk.crypto

import com.casper.sdk.CasperUtils.Companion.toHex
import com.casper.sdk.ConstValues
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
import org.bouncycastle.crypto.Signer
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import org.bouncycastle.crypto.signers.Ed25519Signer
import java.io.*
import java.security.SecureRandom
import java.util.*


const val ed25519PrivateKeyPemFile = "KotlinEd25519PrivateKey.pem"
const val secp256k1PrivateKeyPemFile = "KotlinSecp256k1PrivateKey.pem"
//For this account:0152a685e0edd9060da4a0d52e500d65e21789df3cbfcb878c91ffeaea756d1c53
//Secp256k1 : 0202d3de886567b1281eaa5687a85e14b4f2922e19b89a3f1014c7932f442c9d9635
class Ed25519Handle {

    companion object {
        fun signMessage(messageToSign:String,privateKey:Ed25519PrivateKeyParameters):String {
            //return ECKeyPair()
            //nWGxne_9WmC6hEr0kuwsxERJxWl7MmkZcDusAxyuf2A
            //JVLgaWSg+xbz3GGFI+fiy8c37Yuu/VGUu9qJG1vzGXy
            //1FYcKDdoD1gAa0WDOYYk5v4edNsanwxSOYFAZJV+BQo= - correct
            //NRWHCg3aA9YAGtFgzmGJOb+HnTbGp8MUjmBQGSVfgUK
            println("Generate key called")
            // val privateKeyBytes: ByteArray = Base64.getUrlDecoder().decode("nWGxne_9WmC6hEr0kuwsxERJxWl7MmkZcDusAxyuf2A")
            val privateKeyBytes: ByteArray = Base64.getDecoder().decode("1FYcKDdoD1gAa0WDOYYk5v4edNsanwxSOYFAZJV+BQo=")
            val publicKeyBytes: ByteArray = Base64.getUrlDecoder().decode("11qYAYKxCrfVS_7TyWQHOg7hcvPapiMlrwIaaPcHURo")
            val privateKey = Ed25519PrivateKeyParameters(privateKeyBytes, 0)
            val publicKey = Ed25519PublicKeyParameters(publicKeyBytes, 0)
            println("privateKey:" + privateKey.toString())
            val signer: Signer = Ed25519Signer()
            signer.init(true, privateKey)
            val msg = "0173c68fe0f2ffce805fc7a7856ef4d2ec774291159006c0c3dce1b60ed71c8785";
            signer.update(msg.toByteArray(), 0, msg.length)
            val signature: ByteArray = signer.generateSignature()
            val signatureHexa : String = signature.toHex()
            println("singatureHexa:" + signatureHexa)
            return  ""
        }
        fun generateKey() {
            val secureRandom = SecureRandom()
            val generator: AsymmetricCipherKeyPairGenerator = Ed25519KeyPairGenerator();
            generator.init(Ed25519KeyGenerationParameters(secureRandom))
        }
        fun readPrivateKeyFromPemFile(fileName:String) : String {
           val classLoader = javaClass.classLoader
           val inputStream = classLoader.getResourceAsStream(fileName)
           try {
               val data: String? = readFromInputStream(inputStream)
               if(data.isNullOrEmpty()) {
                   return  ConstValues.INVALID_VALUE
               } else {
                   return data
               }
           } catch (e:java.lang.Exception){
               println("Error catch")
           } finally {
               println("Error catch finally")
           }
            return ConstValues.INVALID_VALUE
        }
        fun readFileLineByLineUsingForEachLine(fileName: String)
                = File(fileName).forEachLine { println(it) }
        @Throws(IOException::class)
        fun readFromInputStream(inputStream: InputStream): String? {
            val resultStringBuilder = StringBuilder()
            BufferedReader(InputStreamReader(inputStream)).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    resultStringBuilder.append(line).append("\n")
                    println(line)
                }
            }
            return resultStringBuilder.toString()
        }
    }
}
/*
fun readPrivateKey(fromPath:String) {
    val keyText = InputStreamReader(
        FileInputStream(fromPath)
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
    val pkp: PEMKeyPair =pemp.readObject() as PEMKeyPair
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