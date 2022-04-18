package com.casper.sdk.getdeploy.ExecutionResult.Transform

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing DeployInfo information */
class DeployInfo {
    var deployHash: String = ""//DeployHash
    var from: String = ""//AccountHash
    var source: String = ""//URef
    var gas: U512Class = U512Class()
    var transfers: MutableList<String> = mutableListOf() //TransferAddr list
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the DeployInfo object */
        fun fromJsonToDeployInfo(from: JsonObject): DeployInfo {
           val ret:  DeployInfo = DeployInfo()
            ret.deployHash = from["deploy_hash"].toString()
            ret.from = from["from"].toString()
            ret.source = from["source"].toString()
            ret.gas = U512Class.fromStringToU512(from["gas"].toString())
            val transferList = from["transfers"] as JsonArray
            val totalTransfer = transferList.count()
            if(totalTransfer > 0) {
                for(i in 0..totalTransfer-1) {
                    ret.transfers.add(transferList[i].toString())
                }
            }
            return ret
        }
    }
}