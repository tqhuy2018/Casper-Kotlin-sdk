package com.casper.sdk.storedvalue

import com.casper.sdk.getdeploy.ExecutionResult.Transform.NamedKey
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class CasperContract {
    var contractPackageHash: String = ""
    var contractWasmHash: String = ""
    var entryPoints: MutableList<EntryPoint> = mutableListOf()
    var namedKeys: MutableList<NamedKey> = mutableListOf()
    var protocolVersion: String = ""
    companion object {
        fun fromJsonObjectToCasperContract(from: JsonObject): CasperContract {
            var ret: CasperContract = CasperContract()
            ret.contractPackageHash = from["contract_package_hash"].toString()
            ret.contractWasmHash = from["contract_wasm_hash"].toString()
            ret.protocolVersion = from["protocol_version"].toString()
            // Get entryPoints
            val entryPoints = from["entry_points"]
            if(entryPoints != null) {
                val entryPointList = from["entry_points"] as JsonArray
                val totalEntryPoint: Int = entryPointList.count() - 1
                if(totalEntryPoint >= 0) {
                    for(i in 0..totalEntryPoint) {
                        val oneEntryPoint: EntryPoint = EntryPoint.fromJsonObjectToEntryPoint(entryPointList[i] as JsonObject)
                        ret.entryPoints.add(oneEntryPoint)
                    }
                }
            }
            // Get namedKeys
            val namedKeys = from["named_keys"]
            if(namedKeys != null) {
                val nameKeyList = from["named_keys"] as JsonArray
                val totalNamedKey: Int = nameKeyList.count() - 1
                if (totalNamedKey >= 0) {
                    for(i in 0.. totalNamedKey) {
                        val oneNamedKey: NamedKey = NamedKey.fromJsonObjectToNamedKey(nameKeyList[i] as JsonObject)
                        ret.namedKeys.add(oneNamedKey)
                    }
                }
            }
            return ret
        }
    }
}