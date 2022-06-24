package com.casper.sdk.getdeploy

import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing Approval information */
class Approval {
    var signature:  String = ""
    var signer:  String = ""
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the Approval object */
        fun  fromJsonToApproval(from: JsonObject):  Approval {
            val ret = Approval()
            ret.signature = from["signature"].toString()
            ret.signer = from["signer"].toString()
            return ret
        }
    }
}