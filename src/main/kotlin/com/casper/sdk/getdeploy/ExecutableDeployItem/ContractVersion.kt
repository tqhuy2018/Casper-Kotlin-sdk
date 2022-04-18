package com.casper.sdk.getdeploy.ExecutableDeployItem

import net.jemzart.jsonkraken.values.JsonObject

class ContractVersion {
    var contractHash: String = ""
    var contractVersion: UInt = 0u
    var contractVersionMajor: UInt = 0u
    companion object {
        fun  fromJsonToContractVersion(from: JsonObject): ContractVersion {
            var ret: ContractVersion = ContractVersion()
            ret.contractHash = from.get("contract_hash").toString()
            ret.contractVersion = from.get("contract_version").toString().toUInt()
            ret.contractVersionMajor = from.get("protocol_version_major").toString().toUInt()
            return ret
        }
    }
}