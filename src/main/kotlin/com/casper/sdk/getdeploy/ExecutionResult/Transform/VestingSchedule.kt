package com.casper.sdk.getdeploy.ExecutionResult.Transform

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class VestingSchedule {
    var initialReleaseTimestampMillis:ULong = 0u
    var lockedAmounts:MutableList<U512Class> = mutableListOf()
    companion object {
        fun fromJsonToVestingSchedule(from: JsonObject):VestingSchedule {
            var ret:VestingSchedule = VestingSchedule()
            val valueInStr = from["initial_release_timestamp_millis"].toString()
            ret.initialReleaseTimestampMillis = valueInStr.toULong()
            val listLAJA = from["locked_amounts"] as JsonArray
            val totalLA = listLAJA.count()
            if(totalLA > 0) {
                for(i in 0..totalLA-1) {
                    var oneItem:U512Class = U512Class.fromStringToU512(listLAJA[i].toJsonString())
                    ret.lockedAmounts.add(oneItem)
                }
            }
            return ret
        }
    }
}