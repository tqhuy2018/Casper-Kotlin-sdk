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
                                var eMB: ExecutableDeployItem_ModuleBytes = ExecutableDeployItem_ModuleBytes()
                                eMB.module_bytes = from.get("ModuleBytes").get("module_bytes").toString()
                                eMB.args = RuntimeArgs.fromJsonArrayToObj(from.get("ModuleBytes").get("args") as JsonArray)
                                ret.itsValue.add(eMB)
                                return ret
                        }
                        val ediStoredContractByHash = from.get("StoredContractByHash").toJsonString()
                        if(ediStoredContractByHash != "null") {
                                ret.itsType = ExecutableDeployItem.STORED_CONTRACT_BY_HASH
                                var eSCBH:ExecutableDeployItem_StoredContractByHash = ExecutableDeployItem_StoredContractByHash()
                                eSCBH.itsHash = from.get("StoredContractByHash").get("hash").toString()
                                eSCBH.entryPoint = from.get("StoredContractByHash").get("entry_point").toString()
                                eSCBH.args = RuntimeArgs.fromJsonArrayToObj(from.get("StoredContractByHash").get("args") as JsonArray)
                                ret.itsValue.add(eSCBH)
                                return ret
                        }
                        val ediStoredContractByName = from.get("StoredContractByName").toJsonString()
                        if(ediStoredContractByName != "null") {
                                ret.itsType = ExecutableDeployItem.STORED_CONTRACT_BY_NAME
                                var eSCBN:ExecutableDeployItem_StoredContractByName = ExecutableDeployItem_StoredContractByName()
                                eSCBN.itsName = from.get("StoredContractByName").get("name").toString()
                                eSCBN.entryPoint = from.get("StoredContractByName").get("entry_point").toString()
                                eSCBN.args = RuntimeArgs.fromJsonArrayToObj(from.get("StoredContractByName").get("args") as JsonArray)
                                ret.itsValue.add(eSCBN)
                                return ret
                        }
                        val ediStoredVersionedContractByHash = from.get("StoredVersionedContractByHash").toJsonString()
                        if(ediStoredVersionedContractByHash != "null") {
                                ret.itsType = ExecutableDeployItem.STORED_VERSIONED_CONTRACT_BY_HASH
                                var eSCBH:ExecutableDeployItem_StoredVersionedContractByHash = ExecutableDeployItem_StoredVersionedContractByHash()
                                eSCBH.itsHash = from.get("StoredVersionedContractByHash").get("hash").toString()
                                eSCBH.entryPoint = from.get("StoredVersionedContractByHash").get("entry_point").toString()
                                eSCBH.args = RuntimeArgs.fromJsonArrayToObj(from.get("StoredVersionedContractByHash").get("args") as JsonArray)
                                val version = from.get("StoredVersionedContractByHash").get("version").toJsonString()
                                if(version == "null") {
                                        eSCBH.isVersionExisted = false
                                } else {
                                        var contractVersion:ContractVersion = ContractVersion.fromJsonToContractVersion(from.get("StoredVersionedContractByHash").get("version") as JsonObject)
                                        eSCBH.version = contractVersion
                                }
                                ret.itsValue.add(eSCBH)
                                return ret
                        }
                        val ediStoredVersionedContractByName = from.get("StoredVersionedContractByName").toJsonString()
                        if(ediStoredVersionedContractByName != "null") {
                                ret.itsType = ExecutableDeployItem.STORED_VERSIONED_CONTRACT_BY_NAME
                                var eSCBH:ExecutableDeployItem_StoredVersionedContractByName = ExecutableDeployItem_StoredVersionedContractByName()
                                eSCBH.itsName = from.get("StoredVersionedContractByName").get("name").toString()
                                eSCBH.entryPoint = from.get("StoredVersionedContractByName").get("entry_point").toString()
                                eSCBH.args = RuntimeArgs.fromJsonArrayToObj(from.get("StoredVersionedContractByName").get("args") as JsonArray)
                                val version = from.get("StoredVersionedContractByName").get("version").toJsonString()
                                if(version == "null") {
                                        eSCBH.isVersionExisted = false
                                } else {
                                        var contractVersion:ContractVersion = ContractVersion.fromJsonToContractVersion(from.get("StoredVersionedContractByHash").get("version") as JsonObject)
                                        eSCBH.version = contractVersion
                                }
                                ret.itsValue.add(eSCBH)
                                return ret
                        }
                        val ediTransfer = from.get("Transfer").toJsonString()
                        if (ediTransfer != "null") {
                                ret.itsType = ExecutableDeployItem.TRANSFER
                                var transfer:ExecutableDeployItem_Transfer = ExecutableDeployItem_Transfer()
                                transfer.args = RuntimeArgs.fromJsonArrayToObj(from.get("Transfer").get("args") as JsonArray)
                                ret.itsValue.add(transfer)
                                return ret
                        }
                        return ret
                }
        }
}
