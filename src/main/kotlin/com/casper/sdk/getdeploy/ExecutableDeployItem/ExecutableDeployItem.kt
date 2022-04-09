package com.casper.sdk.getdeploy.ExecutableDeployItem

import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class ExecutableDeployItem {
        var itsType:String=  ""
        var itsValue:MutableList<Any> = mutableListOf()
        companion object{
                const val MODULE_BYTES                  = "ModuleBytes"
                const val  STORED_CONTRACT_BY_HASH      = "StoredContractByHash"
                fun fromJsonToExecutableDeployItem(from:JsonObject):ExecutableDeployItem {
                        var ret: ExecutableDeployItem = ExecutableDeployItem()
                        val innerPayment:String = from.get("ModuleBytes").toJsonString()
                        if(innerPayment != "null" ) {
                                //println("InnerMB----:" + innerPayment)
                                ret.itsType = ExecutableDeployItem.MODULE_BYTES
                                var eMB: ExecutableDeployItem_ModuleBytes = ExecutableDeployItem_ModuleBytes()
                                eMB.module_bytes = from.get("ModuleBytes").get("module_bytes").toString()
                                eMB.args = RuntimeArgs.fromJsonArrayToObj(from.get("ModuleBytes").get("args") as JsonArray)
                                ret.itsValue.add(eMB)
                        } else {
                                println("Not module bytes")
                        }
                        return ret
                }
        }
}
