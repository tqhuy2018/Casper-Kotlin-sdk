package com.casper.sdk.getitem

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing AssociatedKey information */
class AssociatedKey {
    var accountHash: String = ""
    var weight: UByte = 0u
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the AssociatedKey object */
        fun fromObjectToAssociatedKey(from: JsonObject): AssociatedKey {
            val ret = AssociatedKey()
            ret.accountHash = from["account_hash"].toString()
            ret.weight = from["weight"].toJsonString().toUByte()
            return ret
        }
    }
}