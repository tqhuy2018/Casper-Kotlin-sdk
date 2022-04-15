package com.casper.sdk.getblock

import net.jemzart.jsonkraken.values.JsonObject

class JsonProof {
    var publicKey:String = ""
    var signature:String = ""
    companion object {
        fun fromJsonToJsonProof(from:JsonObject) : JsonProof {
            var ret:JsonProof = JsonProof()
            ret.publicKey = from["public_key"].toString()
            ret.signature = from["signature"].toString()
            return ret
        }
    }
}