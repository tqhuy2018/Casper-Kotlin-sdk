package com.casper.sdk.getblock

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing JsonBlockBody information */
class JsonBlockBody {
    var deployHashes: MutableList<String> = mutableListOf()
    var proposer: String = ""
    var transferHashes: MutableList<String> = mutableListOf()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to JsonBlockBody object */
        fun fromJsonToJsonBlockBody(from: JsonObject): JsonBlockBody {
            val ret = JsonBlockBody()
            ret.proposer = from["proposer"].toString()
            val deployHashes: JsonArray = from["deploy_hashes"] as JsonArray
            val transferHashes: JsonArray = from["transfer_hashes"] as JsonArray
            val totalDeployHash: Int = deployHashes.count()
            val totalTransfer: Int = transferHashes.count()
            if(totalDeployHash>0){
                for(i in 0..totalDeployHash-1) {
                    ret.deployHashes.add(deployHashes[i] as String)
                }
            }
            if(totalTransfer>0) {
                for(i in 0..totalTransfer-1) {
                    ret.transferHashes.add(transferHashes[i] as String)
                }
            }
            return  ret
        }
    }
}