package com.casper.sdk.getdeploy.ExecutableDeployItem

import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing ExecutableDeployItem enum in general
 * This class has 2 attributes:
 * 1) itsType is for the type of the ExecutableDeployItem enum, which can be a string among these values:
 * ModuleBytes, StoredContractByHash, StoredContractByName, StoredVersionedContractByHash, StoredVersionedContractByName, Transfer
 * 2) itsValue: To hold the real ExecutableDeployItem enum value, which can be 1 among the following class
 * ExecutableDeployItem_ModuleBytes
 * ExecutableDeployItem_StoredContractByHash
 * ExecutableDeployItem_StoredContractByName
 * ExecutableDeployItem_StoredVersionedContractByHash
 * ExecutableDeployItem_StoredVersionedContractByName
 * ExecutableDeployItem_Transfer */
class ExecutableDeployItem {
        var itsType: String=  ""
        var itsValue: MutableList<Any> = mutableListOf()
        companion object{
                // Constant values for the type of the ExecutableDeployItem enum
                const val  MODULE_BYTES                                 = "ModuleBytes"
                const val  STORED_CONTRACT_BY_HASH                      = "StoredContractByHash"
                const val  STORED_CONTRACT_BY_NAME                      = "StoredContractByName"
                const val  STORED_VERSIONED_CONTRACT_BY_HASH            = "StoredVersionedContractByHash"
                const val  STORED_VERSIONED_CONTRACT_BY_NAME            = "StoredVersionedContractByName"
                const val  TRANSFER                                     = "Transfer"
                /** This function parse the JsonArray (taken from server RPC method call) to get the ExecutableDeployItem object */
                fun fromJsonToExecutableDeployItem(from: JsonObject): ExecutableDeployItem {
                        val ret = ExecutableDeployItem()
                        val ediModuleBytes: String = from.get("ModuleBytes").toJsonString()
                        if(ediModuleBytes != "null" ) {
                                ret.itsType = MODULE_BYTES
                                val eMB = ExecutableDeployItem_ModuleBytes()
                                eMB.module_bytes = from.get("ModuleBytes").get("module_bytes").toString()
                                eMB.args = RuntimeArgs.fromJsonArrayToObj(from.get("ModuleBytes").get("args") as JsonArray)
                                ret.itsValue.add(eMB)
                                return ret
                        }
                        val ediStoredContractByHash = from.get("StoredContractByHash").toJsonString()
                        if(ediStoredContractByHash != "null") {
                                ret.itsType = STORED_CONTRACT_BY_HASH
                                val eSCBH = ExecutableDeployItem_StoredContractByHash()
                                eSCBH.itsHash = from.get("StoredContractByHash").get("hash").toString()
                                eSCBH.entryPoint = from.get("StoredContractByHash").get("entry_point").toString()
                                eSCBH.args = RuntimeArgs.fromJsonArrayToObj(from.get("StoredContractByHash").get("args") as JsonArray)
                                ret.itsValue.add(eSCBH)
                                return ret
                        }
                        val ediStoredContractByName = from.get("StoredContractByName").toJsonString()
                        if(ediStoredContractByName != "null") {
                                ret.itsType = STORED_CONTRACT_BY_NAME
                                val eSCBN = ExecutableDeployItem_StoredContractByName()
                                eSCBN.itsName = from.get("StoredContractByName").get("name").toString()
                                eSCBN.entryPoint = from.get("StoredContractByName").get("entry_point").toString()
                                eSCBN.args = RuntimeArgs.fromJsonArrayToObj(from.get("StoredContractByName").get("args") as JsonArray)
                                ret.itsValue.add(eSCBN)
                                return ret
                        }
                        val ediStoredVersionedContractByHash = from.get("StoredVersionedContractByHash").toJsonString()
                        if(ediStoredVersionedContractByHash != "null") {
                                ret.itsType = STORED_VERSIONED_CONTRACT_BY_HASH
                                val eSCBH = ExecutableDeployItem_StoredVersionedContractByHash()
                                eSCBH.itsHash = from.get("StoredVersionedContractByHash").get("hash").toString()
                                eSCBH.entryPoint = from.get("StoredVersionedContractByHash").get("entry_point").toString()
                                eSCBH.args = RuntimeArgs.fromJsonArrayToObj(from.get("StoredVersionedContractByHash").get("args") as JsonArray)
                                val version = from.get("StoredVersionedContractByHash").get("version").toJsonString()
                                if(version == "null") {
                                        eSCBH.isVersionExisted = false
                                } else {
                                       // val contractVersion: ContractVersion = ContractVersion.fromJsonToContractVersion(from.get("StoredVersionedContractByHash").get("version") as JsonObject)
                                        eSCBH.version = from.get("StoredVersionedContractByHash").get("version") as UInt
                                }
                                ret.itsValue.add(eSCBH)
                                return ret
                        }
                        val ediStoredVersionedContractByName = from.get("StoredVersionedContractByName").toJsonString()
                        if(ediStoredVersionedContractByName != "null") {
                                ret.itsType = STORED_VERSIONED_CONTRACT_BY_NAME
                                val eSCBH = ExecutableDeployItem_StoredVersionedContractByName()
                                eSCBH.itsName = from.get("StoredVersionedContractByName").get("name").toString()
                                eSCBH.entryPoint = from.get("StoredVersionedContractByName").get("entry_point").toString()
                                eSCBH.args = RuntimeArgs.fromJsonArrayToObj(from.get("StoredVersionedContractByName").get("args") as JsonArray)
                                val version = from.get("StoredVersionedContractByName").get("version").toJsonString()
                                if(version == "null") {
                                        eSCBH.isVersionExisted = false
                                } else {
                                        //val contractVersion: ContractVersion = ContractVersion.fromJsonToContractVersion(from.get("StoredVersionedContractByHash").get("version") as JsonObject)
                                        eSCBH.version = from.get("StoredVersionedContractByName").get("version") as UInt
                                }
                                ret.itsValue.add(eSCBH)
                                return ret
                        }
                        val ediTransfer = from.get("Transfer").toJsonString()
                        if (ediTransfer != "null") {
                                ret.itsType = TRANSFER
                                val transfer = ExecutableDeployItem_Transfer()
                                transfer.args = RuntimeArgs.fromJsonArrayToObj(from.get("Transfer").get("args") as JsonArray)
                                ret.itsValue.add(transfer)
                                return ret
                        }
                        return ret
                }
        }
}
