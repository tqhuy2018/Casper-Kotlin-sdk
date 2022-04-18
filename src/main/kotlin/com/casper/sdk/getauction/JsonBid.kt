package com.casper.sdk.getauction

import com.casper.sdk.common.classes.U512Class
import com.casper.sdk.getdeploy.ExecutionResult.Transform.Delegator
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing JsonBid information */
class JsonBid {
    var bondingPurse: String = ""
    var delegationRate: UByte = 0u
    var delegators: MutableList<JsonDelegator> = mutableListOf()
    var inactive: Boolean = false
    var stakedAmount: U512Class = U512Class()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to generate the JsonBid object */
        fun fromJsonObjectToJsonBid(from: JsonObject): JsonBid {
            var ret: JsonBid = JsonBid()
            ret.bondingPurse = from["bonding_purse"].toString()
            ret.delegationRate = from["delegation_rate"].toJsonString().toUByte()
            ret.inactive = from["inactive"].toString().toBoolean()
            ret.stakedAmount = U512Class.fromStringToU512(from["staked_amount"].toString())
            val delegators = from["delegators"]
            if(delegators != null) {
                val delegatorList = from["delegators"] as JsonArray
                val totalDelegator: Int = delegatorList.count() - 1
                if(totalDelegator>=0) {
                    for(i in 0..totalDelegator) {
                        val oneDelegator: JsonDelegator = JsonDelegator.fromJsonObjectToJsonDelegator(delegatorList[i] as JsonObject)
                        ret.delegators.add(oneDelegator)
                    }
                }
            }
            return ret
        }
    }
}