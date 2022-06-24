package com.casper.sdk.getauction

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing JsonEraValidators information */
class JsonEraValidators {
    var eraId: ULong = 0u
    var validatorWeights:  MutableList<JsonValidatorWeights> = mutableListOf()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to generate the JsonEraValidators object */
        fun fromJsonObjectToJsonEraValidators(from: JsonObject): JsonEraValidators {
            val ret = JsonEraValidators()
            ret.eraId = from["era_id"].toJsonString().toULong()
            val validatorWeights = from["validator_weights"]
            if(validatorWeights!=null) {
                val validatorWeightList = from["validator_weights"] as JsonArray
                val totalVW: Int = validatorWeightList.count() - 1
                if(totalVW >= 0) {
                    for (i in 0 .. totalVW) {
                        val oneVW: JsonValidatorWeights = JsonValidatorWeights.fromJsonObjectToJsonValidatorWeights(validatorWeightList[i] as JsonObject)
                        ret.validatorWeights.add(oneVW)
                    }
                }
            }
            return ret
        }
    }
}