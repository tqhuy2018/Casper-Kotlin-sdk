package com.casper.sdk.getblocktransfer

import com.casper.sdk.getdeploy.ExecutionResult.Transform.Transfer
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing GetBlockTransfersResult information, taken from chain_get_block_transfers RPC method */

class GetBlockTransfersResult {
    var apiVersion: String = ""
    lateinit var blockHash: String
    lateinit var transfers: MutableList<Transfer>
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to GetBlockTransfersResult object */
        fun fromJsonToGetBlockTransfersResult(from: JsonObject): GetBlockTransfersResult {
            val ret = GetBlockTransfersResult()
            ret.apiVersion = from["api_version"].toString()
            val blockHash = from["block_hash"]
            if(blockHash != null) {
                ret.blockHash = blockHash.toString()
            }
            val transferList = from["transfers"]
            if(transferList!=null) {
                ret.transfers = mutableListOf()
                val transferListJsonObject = transferList as JsonArray
                val totalTransfer = transferListJsonObject.count()
                for(i in 0 .. totalTransfer-1) {
                    val oneTransfer = Transfer.fromJsonToTransfer(transferListJsonObject[i] as JsonObject)
                    ret.transfers.add(oneTransfer)
                }
            }
            return ret
        }
    }
    fun isBlockHashInit(): Boolean {
        if(this:: blockHash.isInitialized) {
            return true
        }
        return false
    }
    fun isTransferInit(): Boolean {
        if (this:: transfers.isInitialized) {
            return  true
        }
        return false
    }
}