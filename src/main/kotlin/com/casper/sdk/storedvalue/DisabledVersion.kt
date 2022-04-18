package com.casper.sdk.storedvalue

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing DisableVersion information */
class DisabledVersion {
    var contractVersion: UInt = 0u
    var protocolVersionMajor: UInt = 0u
    companion object {

        /** This function parse the JsonObject (taken from server RPC method call) to get the DisabledVersion object */
        fun fromJsonObjectToDisabledVersion(from: JsonObject): DisabledVersion {
            val ret = DisabledVersion()
            ret.contractVersion = from["contract_version"].toJsonString().toUInt()
            ret.protocolVersionMajor = from["protocol_version_major"].toJsonString().toUInt()
            return  ret
        }
    }
}