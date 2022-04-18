package com.casper.sdk.getitem

import com.casper.sdk.storedvalue.StoredValue
import net.jemzart.jsonkraken.values.JsonObject

class GetItemResult {
    var apiVersion: String = ""
    var storedValue:  StoredValue = StoredValue()
    var merkleProof: String = ""
    companion object {
        fun fromJsonObjectToGetItemResult(from: JsonObject): GetItemResult {
            var ret: GetItemResult = GetItemResult()
            ret.apiVersion = from["api_version"].toString()
            ret.merkleProof = from["merkle_proof"].toString()
            ret.storedValue = StoredValue.fromJsonObjectToStoredValue(from["stored_value"] as JsonObject)
            return ret
        }
    }
}