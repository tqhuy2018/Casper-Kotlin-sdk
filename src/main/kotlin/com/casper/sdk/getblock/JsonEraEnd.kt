package com.casper.sdk.getblock

import net.jemzart.jsonkraken.values.JsonObject

class JsonEraEnd {
    var eraReport:JsonEraReport = JsonEraReport()
    var nextEraValidatorWeights:MutableList<ValidatorWeight> = mutableListOf()
    companion object {
        fun fromJsonObjToJsonEraEnd(from:JsonObject):JsonEraEnd {
            var ret:JsonEraEnd = JsonEraEnd()

            return ret
        }
    }
}