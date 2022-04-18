package com.casper.sdk.getdeploy

import net.jemzart.jsonkraken.values.JsonObject

class Approval {
    var signature:  String = ""
    var signer:  String = ""
    companion object {
        fun  fromJsonToApproval(from: JsonObject):  Approval {
            var ret: Approval = Approval()
            ret.signature = from["signature"].toString()
            ret.signer = from["signer"].toString()
            return ret
        }
    }
}