package com.casper.sdk.getdeploy.ExecutionResult.Transform

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing VestingSchedule information */
class VestingSchedule {
    var initialReleaseTimestampMillis: ULong = 0u
    var lockedAmounts: MutableList<U512Class> = mutableListOf()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the VestingSchedule object */
        fun fromJsonToVestingSchedule(from:  JsonObject): VestingSchedule {
            val ret = VestingSchedule()
            val valueInStr = from["initial_release_timestamp_millis"].toJsonString()
            ret.initialReleaseTimestampMillis = valueInStr.toULong()
            val listLAJA = from["locked_amounts"] as JsonArray
            val totalLA = listLAJA.count()
            if(totalLA > 0) {
                for(i in 0..totalLA-1) {
                    val oneItem: U512Class = U512Class.fromStringToU512(listLAJA[i].toString())
                    ret.lockedAmounts.add(oneItem)
                }
            }
            return ret
        }
    }
}