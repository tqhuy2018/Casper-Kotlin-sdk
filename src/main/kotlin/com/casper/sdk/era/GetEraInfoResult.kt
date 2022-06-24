package com.casper.sdk.era

import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing GetEraInfoResult information,  taken from chain_get_era_info_by_switch_block RPC method */
class GetEraInfoResult {
    var apiVersion:  String = ""
    lateinit var eraSummary:  EraSummary
    fun isEraSummaryInit():  Boolean {
        if(this:: eraSummary.isInitialized) {
            return true
        }
        return false
    }
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to GetEraInfoResult object */
        fun fromJsonToGetEraInfoResult(from: JsonObject):  GetEraInfoResult {
            val ret = GetEraInfoResult()
            ret.apiVersion = from["api_version"].toString()
            val eraSummary = from["era_summary"]
            if(eraSummary != null) {
                ret.eraSummary = EraSummary.fromJsonToEraSummary(from["era_summary"] as JsonObject)
            }
            return ret
        }
    }
}