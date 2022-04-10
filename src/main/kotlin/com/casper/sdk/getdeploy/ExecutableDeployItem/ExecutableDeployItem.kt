package com.casper.sdk.getdeploy.ExecutableDeployItem

import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class ExecutableDeployItem {
        var itsType:String=  ""
        var itsValue:MutableList<Any> = mutableListOf()
        companion object{
                const val  MODULE_BYTES                                 = "ModuleBytes"
                const val  STORED_CONTRACT_BY_HASH                      = "StoredContractByHash"
                const val  STORED_CONTRACT_BY_NAME                      = "StoredContractByName"
                const val  STORED_VERSIONED_CONTRACT_BY_HASH            = "StoredVersionedContractByHash"
                const val  STORED_VERSIONED_CONTRACT_BY_NAME            = "StoredVersionedContractByName"
                const val  TRANSFER                                     = "Transfer"
                fun fromJsonToExecutableDeployItem(from:JsonObject):ExecutableDeployItem {
                        var ret: ExecutableDeployItem = ExecutableDeployItem()
                        val ediModuleBytes:String = from.get("ModuleBytes").toJsonString()
                        if(ediModuleBytes != "null" ) {
                                ret.itsType = ExecutableDeployItem.MODULE_BYTES
                                println("EDI IS MODULE_BYTES, ret.itsType:${ret.itsType}")
                                var eMB: ExecutableDeployItem_ModuleBytes = ExecutableDeployItem_ModuleBytes()
                                eMB.module_bytes = from.get("ModuleBytes").get("module_bytes").toString()
                                eMB.args = RuntimeArgs.fromJsonArrayToObj(from.get("ModuleBytes").get("args") as JsonArray)
                                ret.itsValue.add(eMB)
                                return ret
                        } else {
                                println("EDI Not module bytes")
                        }
                        return ret
                }
        }
}
