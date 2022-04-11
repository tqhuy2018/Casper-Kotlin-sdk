package com.casper.sdk.getdeploy.ExecutionResult.Transform

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class EraInfo {
    var seigniorageAllocations:MutableList<SeigniorageAllocation> = mutableListOf()
    companion object {
        fun fromJsonArrayToEraInfo(from:JsonArray):EraInfo {
            var ret:EraInfo = EraInfo()
            val jsonArray:JsonArray = from["seigniorage_allocations"] as JsonArray
            val totalElement = jsonArray.count()
            if(totalElement>0) {
                for(i in 0..totalElement-1) {
                    val oneItem: SeigniorageAllocation = SeigniorageAllocation.fromJsonToSeigniorageAllocation(jsonArray[i] as JsonObject)
                    ret.seigniorageAllocations.add(oneItem)
                }
            }
            return ret
        }
    }
}