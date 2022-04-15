package com.casper.sdk.getitem

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

class AssociatedKey {
    var accountHash:String = ""
    var weight:UByte = 0u
    companion object {
        fun fromObjectToAssociatedKey(from:JsonObject):AssociatedKey {
            var ret:AssociatedKey = AssociatedKey()
            ret.accountHash = from["account_hash"].toString()
            ret.weight = from["weight"].toJsonString().toUByte()
            return ret
        }
    }
}