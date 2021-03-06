package com.casper.sdk.era

import com.casper.sdk.storedvalue.StoredValue
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing EraSummary information */
class EraSummary {
    var blockHash:  String = ""
    var eraId:  ULong = 0u
    //The stored value,  which can be 1 among 10 possible value in this enum,  defined at this address
    //https: //docs.rs/casper-node/latest/casper_node/types/json_compatibility/enum.StoredValue.html
    var storedValue:  StoredValue = StoredValue()
    var storedValueType:  String = ""
    var stateRootHash:  String = ""
    var merkleProof:  String = ""
    /** This function parse the JsonObject (taken from server RPC method call) to generate the EraSummary object */
    companion object {
        fun fromJsonToEraSummary(from:  JsonObject) :  EraSummary {
            val ret = EraSummary()
            ret.blockHash = from["block_hash"].toString()
            ret.eraId = from["era_id"].toJsonString().toULong()
            ret.stateRootHash = from["state_root_hash"].toString()
            ret.merkleProof = from["merkle_proof"].toString()
            ret.storedValue = StoredValue.fromJsonObjectToStoredValue(from["stored_value"] as JsonObject)
            return ret
        }
    }
}