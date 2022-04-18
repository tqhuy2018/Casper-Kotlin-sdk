package com.casper.sdk.getstatus

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

class NextUpgrade {
    var protocolVersion: String = ""
    //ActivationPoint can be either EraId of type ULong or timestamp of type String
    var activationPointEraId: ULong = 0u
    lateinit var activationPointTimestamp: String
    fun isActivationPointTimestamp(): Boolean {
        if(this:: activationPointTimestamp.isInitialized) {
            return true
        }
        return  false
    }
    companion object {
        fun  fromJsonToNextUpgrade(from: JsonObject): NextUpgrade {
            var ret: NextUpgrade = NextUpgrade()
            ret.protocolVersion = from["protocol_version"].toString()
            val ap = from["activation_point"].toString()
            if (ap.toULong() > 0u) {
                ret.activationPointEraId = ap.toULong()
            } else {
                ret.activationPointTimestamp = ap
            }
            return ret
        }
    }
}