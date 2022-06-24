package com.casper.sdk.getblock

import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing JsonProof information */
class JsonProof {
    var publicKey: String = ""
    var signature: String = ""
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to JsonProof object */
        fun fromJsonToJsonProof(from: JsonObject) : JsonProof {
            val ret = JsonProof()
            ret.publicKey = from["public_key"].toString()
            ret.signature = from["signature"].toString()
            return ret
        }
    }
}