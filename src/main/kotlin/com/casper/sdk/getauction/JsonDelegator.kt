package com.casper.sdk.getauction

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing JsonDelegator information */
class JsonDelegator {
    var bondingPurse: String = ""
    var delegatee: String = ""
    var publicKey: String = ""
    var stakedAmount: U512Class = U512Class()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to generate the JsonDelegator object */
        fun fromJsonObjectToJsonDelegator(from: JsonObject): JsonDelegator {
            val ret = JsonDelegator()
            ret.bondingPurse = from["bonding_purse"].toString()
            ret.delegatee = from["delegatee"].toString()
            ret.publicKey = from["public_key"].toString()
            ret.stakedAmount = U512Class.fromStringToU512(from["staked_amount"].toString())
            return ret
        }
    }
}