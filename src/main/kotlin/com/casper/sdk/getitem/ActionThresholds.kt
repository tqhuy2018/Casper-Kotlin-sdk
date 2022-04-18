package com.casper.sdk.getitem
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing ActionThresholds information */
class ActionThresholds {
    var deployment: UByte = 0u
    var keyManagement: UByte = 0u
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the ActionThresholds object */
        fun fromJsonObjectToActionThresholds(from: JsonObject): ActionThresholds {
            val ret = ActionThresholds()
            ret.deployment = from["deployment"].toJsonString().toUByte()
            ret.keyManagement = from["key_management"].toJsonString().toUByte()
            return ret
        }
    }
}