package com.casper.sdk.getblock

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

class JsonBlockHeader {
    var parentHash:String = ""
    var stateRootHash:String = ""
    var bodyHash:String = ""
    var randomBit:Boolean = false
    var accumulatedSeed:String = ""
    lateinit var eraEnd:JsonEraEnd
    var timestamp:String = ""
    var eraId:ULong = 0u
    var height:ULong = 0u
    var protocolVersion:String = ""
    fun isEraEndInit():Boolean {
        if (this::eraEnd.isInitialized) {
            return true
        }
        return false
    }
    companion object {
        fun fromJsonToJsonBlockHeader(from:JsonObject):JsonBlockHeader {
            var ret:JsonBlockHeader = JsonBlockHeader()
            ret.parentHash = from["parent_hash"].toString()
            ret.stateRootHash = from["state_root_hash"].toString()
            ret.bodyHash = from["body_hash"].toString()
            ret.randomBit = from["random_bit"].toString().toBoolean()
            ret.accumulatedSeed = from["accumulated_seed"].toString()
            val eraEnd = from["era_end"]
            if (eraEnd != null) {
                ret.eraEnd = JsonEraEnd.fromJsonObjToJsonEraEnd(from["era_end"] as JsonObject)
            }
            ret.timestamp = from["timestamp"].toString()
            val eraId:String = from["era_id"].toJsonString()
            ret.eraId = eraId.toULong()
            val heightStr:String = from["height"].toJsonString()
            ret.height = heightStr.toULong()
            ret.protocolVersion = from["protocol_version"].toString()
            return ret
        }
    }
}