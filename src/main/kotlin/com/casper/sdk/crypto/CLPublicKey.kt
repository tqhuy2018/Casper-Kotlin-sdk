package com.casper.sdk.crypto
class CLPublicKey {
    lateinit var bytes:ByteArray
    var keyAlgorithm:String = "01" //01 for Ed25519, 02 for Secp256k1
}