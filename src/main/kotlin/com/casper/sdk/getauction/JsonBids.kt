package com.casper.sdk.getauction

import net.jemzart.jsonkraken.values.JsonObject

class JsonBids {
    var bid:JsonBid = JsonBid()
    var publicKey:String = ""
    companion object {
        fun fromJsonObjectToJsonBids(from:JsonObject):JsonBids {
            var ret:JsonBids = JsonBids()
            ret.publicKey = from["public_key"].toString()
            ret.bid = JsonBid.fromJsonObjectToJsonBid(from["bid"] as JsonObject)
            return ret
        }
    }
}