package com.casper.sdk.getblock

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing JsonBlock information */
class JsonBlock {
    var blockHash: String = ""
    var header: JsonBlockHeader = JsonBlockHeader()
    var body: JsonBlockBody = JsonBlockBody()
    var proofs: MutableList<JsonProof> = mutableListOf()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to JsonBlock object */
        fun fromJsonToJsonBlock(from: JsonObject): JsonBlock {
            val ret = JsonBlock()
            ret.blockHash = from["hash"].toString()
            ret.header = JsonBlockHeader.fromJsonToJsonBlockHeader(from["header"] as JsonObject)
            ret.body = JsonBlockBody.fromJsonToJsonBlockBody(from["body"] as JsonObject)
            ret.proofs = fromJsonArrayToProofs(from["proofs"] as JsonArray)
            return ret
        }
        /** This function parse the JsonArray (taken from server RPC method call) to a Mutable List of  JsonProof object */
        fun fromJsonArrayToProofs(from: JsonArray): MutableList<JsonProof> {
            val ret: MutableList<JsonProof> = mutableListOf()
            val totalProof: Int = from.count() - 1
            if(totalProof>=0) {
                for(i in 0.. totalProof) {
                    val oneProof: JsonProof = JsonProof.fromJsonToJsonProof(from[i] as JsonObject)
                    ret.add(oneProof)
                }
            }
            return ret
        }
    }
}