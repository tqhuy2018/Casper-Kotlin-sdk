package com.casper.sdk.getblock

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing JsonEraEnd information */
class JsonEraEnd {
    var eraReport: JsonEraReport = JsonEraReport()
    var nextEraValidatorWeights: MutableList<ValidatorWeight> = mutableListOf()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to JsonEraEnd object */
        fun fromJsonObjToJsonEraEnd(from: JsonObject): JsonEraEnd {
            var ret: JsonEraEnd = JsonEraEnd()
            ret.eraReport = JsonEraReport.fromJsonToJsonEraReport(from["era_report"] as JsonObject)
            val nextEraValidatorWeights :  JsonArray = from["next_era_validator_weights"] as JsonArray
            var totalNEVW: Int = nextEraValidatorWeights.count() - 1
            if(totalNEVW >= 0) {
                for(i in 0..totalNEVW) {
                    var oneVW: ValidatorWeight = ValidatorWeight.fromJsonToValidatorWeight(nextEraValidatorWeights[i] as JsonObject)
                    ret.nextEraValidatorWeights.add(oneVW)
                }
            }
            return ret
        }
    }
}