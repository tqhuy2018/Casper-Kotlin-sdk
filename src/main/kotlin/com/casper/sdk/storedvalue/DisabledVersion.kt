package com.casper.sdk.storedvalue

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

class DisabledVersion {
    var contractVersion:UInt = 0u
    var protocolVersionMajor:UInt = 0u
    companion object {
        fun fromJsonObjectToDisabledVersion(from:JsonObject):DisabledVersion {
            var ret:DisabledVersion = DisabledVersion()
            ret.contractVersion = from["contract_version"].toJsonString().toUInt()
            ret.protocolVersionMajor = from["protocol_version_major"].toJsonString().toUInt()
            return  ret
        }
    }
}