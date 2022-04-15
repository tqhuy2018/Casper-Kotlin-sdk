package com.casper.sdk.era

import net.jemzart.jsonkraken.values.JsonObject
import java.time.chrono.Era

class GetEraInfoResult {
    var apiVersion:String = ""
    lateinit var eraSummary:EraSummary
    fun isEraSummaryInit():Boolean {
        if(this::eraSummary.isInitialized) {
            return true
        }
        return false
    }
    companion object {
        fun fromJsonToGetEraInfoResult(from:JsonObject):GetEraInfoResult {
            var ret:GetEraInfoResult = GetEraInfoResult()
            ret.apiVersion = from["api_version"].toString()
            val eraSummary = from["era_summary"]
            if(eraSummary != null) {
                ret.eraSummary = EraSummary.fromJsonToEraSummary(from["era_summary"] as JsonObject)
            }
            return ret
        }
    }
}