package com.casper.sdk.getdeploy.ExecutionResult.Transform

import com.casper.sdk.common.classes.U512Class
import com.casper.sdk.getdeploy.Deploy
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class DeployInfo {
    var deployHash:String = ""//DeployHash
    var from:String = ""//AccountHash
    var source:String = ""//URef
    var gas:U512Class = U512Class()
    var transfer:MutableList<String> = mutableListOf() //TransferAddr list
    companion object {
        fun fromJsonToDeployInfo(from:JsonObject):DeployInfo {
           var ret: DeployInfo = DeployInfo()
            ret.deployHash = from["deploy_hash"].toString()
            ret.from = from["from"].toString()
            ret.source = from["source"].toString()
            ret.gas = U512Class.fromStringToU512(from["gas"].toString())
            println("Gas of DeployInfo:${ret.gas.itsValue}")
            val transferList = from["transfers"] as JsonArray
            val totalTransfer = transferList.count()
            if(totalTransfer > 0) {
                for(i in 0..totalTransfer-1) {
                    ret.transfer.add(transferList[i].toString())
                }
            }
            return ret
        }
    }
}