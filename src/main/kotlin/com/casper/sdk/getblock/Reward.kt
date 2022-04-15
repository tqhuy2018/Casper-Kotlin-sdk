package com.casper.sdk.getblock

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

class Reward {
    var validator:String = ""
    var amount:ULong = 0u
    companion object {
        fun fromJsonToReward(from:JsonObject):Reward {
            var ret:Reward = Reward()
            ret.validator = from["validator"].toString()
            ret.amount = from["amount"].toJsonString().toULong()
            return ret
        }
    }
}