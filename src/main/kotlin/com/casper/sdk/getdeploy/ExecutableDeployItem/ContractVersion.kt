package com.casper.sdk.getdeploy.ExecutableDeployItem

import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing ContractVersion information */
class ContractVersion {
    var contractHash: String = ""
    var contractVersion: UInt = 0u
    var protocolVersionMajor: UInt = 0u
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the ContractVersion object */
        fun  fromJsonToContractVersion(from: JsonObject): ContractVersion {
            val ret = ContractVersion()
            ret.contractHash = from.get("contract_hash").toString()
            ret.contractVersion = from.get("contract_version").toString().toUInt()
            ret.protocolVersionMajor = from.get("protocol_version_major").toString().toUInt()
            return ret
        }
    }
}