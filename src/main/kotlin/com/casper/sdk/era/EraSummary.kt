package com.casper.sdk.era

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject
import kotlin.math.E

class EraSummary {
    var blockHash:String = ""
    var eraId:ULong = 0u
    //The stored value, which can be 1 among 10 possible value in this enum, defined at this address
    //https://docs.rs/casper-node/latest/casper_node/types/json_compatibility/enum.StoredValue.html
    var storedValue:MutableList<Any> = mutableListOf()
    var storedValueType:String = ""
    var stateRootHash:String = ""
    var merkleProof:String = ""
    companion object {
        fun fromJsonToEraSummary(from:JsonObject) : EraSummary {
            var ret:EraSummary = EraSummary()
            ret.blockHash = from["block_hash"].toString()
            ret.eraId = from["era_id"].toJsonString().toULong()
            ret.stateRootHash = from["state_root_hash"].toString()
            ret.merkleProof = from["merkle_proof"].toString()
            return ret
        }
    }
}