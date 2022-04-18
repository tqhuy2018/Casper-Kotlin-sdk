package com.casper.sdk.getdictionary

import com.casper.sdk.storedvalue.StoredValue
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing GetDictionaryItemResult,  which is taken from state_get_dictionary_item RPC call */
class GetDictionaryItemResult {
    var apiVersion: String = ""
    var dictionaryKey: String = ""
    var storedValue: StoredValue = StoredValue()
    var merkleProof: String = ""
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to GetDictionaryItemResult object */
        fun fromJsonObjectToGetDictionaryItemResult(from: JsonObject) :  GetDictionaryItemResult {
            var ret: GetDictionaryItemResult = GetDictionaryItemResult()
            ret.apiVersion = from["api_version"].toString()
            ret.dictionaryKey = from["dictionary_key"].toString()
            ret.merkleProof = from["merkle_proof"].toString()
            ret.storedValue = StoredValue.fromJsonObjectToStoredValue(from["stored_value"] as JsonObject)
            return ret
        }
    }
}