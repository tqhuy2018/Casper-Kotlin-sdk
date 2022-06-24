package com.casper.sdk.getauction

import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing JsonBids information */
class JsonBids {
    var bid: JsonBid = JsonBid()
    var publicKey: String = ""
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to generate the JsonBids object */
        fun fromJsonObjectToJsonBids(from: JsonObject): JsonBids {
            val ret = JsonBids()
            ret.publicKey = from["public_key"].toString()
            ret.bid = JsonBid.fromJsonObjectToJsonBid(from["bid"] as JsonObject)
            return ret
        }
    }
}