package com.casper.sdk.getdeploy

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class DeployHeader {
    var account: String = ""
    var bodyHash: String = ""
    var chainName: String = ""
    //List of DeployHash, can be empty
    var dependencies: MutableList<String> = mutableListOf()
    var gasPrice: ULong = 0u
    var timeStamp: String = ""
    var ttl: String = ""
    companion object {
        fun fromJsonToDeployHeader(from:JsonObject):DeployHeader {
            var ret:DeployHeader = DeployHeader()
            ret.account = from["account"].toString()
            ret.bodyHash = from["body_hash"].toString()
            ret.chainName = from["chain_name"].toString()
            var gasPrice = from["gas_price"].toJsonString()
            ret.gasPrice = gasPrice.toULong()
            ret.timeStamp = from["timestamp"].toString()
            ret.ttl = from["ttl"].toString()
            val dependencyJsonArray:JsonArray = from["dependencies"] as JsonArray
            val totalDependency = dependencyJsonArray.count()
            if (totalDependency>0) {
                for(i in 0 .. totalDependency-1) {
                    ret.dependencies.add(dependencyJsonArray[i].toString())
                }
            }
            return ret
        }
    }
}
