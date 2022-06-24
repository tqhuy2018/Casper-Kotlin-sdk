package com.casper.sdk.getblock

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing Reward information */
class Reward {
    var validator: String = ""
    var amount: ULong = 0u
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to Reward object */
        fun fromJsonToReward(from: JsonObject): Reward {
            val ret = Reward()
            ret.validator = from["validator"].toString()
            ret.amount = from["amount"].toJsonString().toULong()
            return ret
        }
    }
}