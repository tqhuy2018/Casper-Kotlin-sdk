package com.casper.sdk.getitem

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

class ActionThresholds {
    var deployment:UByte = 0u
    var keyManagement:UByte = 0u
    companion object {
        fun fromJsonObjectToActionThresholds(from:JsonObject):ActionThresholds {
            var ret:ActionThresholds = ActionThresholds()
            ret.deployment = from["deployment"].toJsonString().toUByte()
            ret.keyManagement = from["key_management"].toJsonString().toUByte()
            return ret
        }
    }
}