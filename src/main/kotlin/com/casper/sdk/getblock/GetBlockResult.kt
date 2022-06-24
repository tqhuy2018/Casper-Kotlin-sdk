package com.casper.sdk.getblock
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing GetBlockResult information, taken from chain_get_block RPC method */
class GetBlockResult {
    var apiVersion: String = ""
    lateinit var block: JsonBlock
    /*fun isBlockInit(): Boolean{
        if(this:: block.isInitialized) {
            return true
        }
        return false
    }
*/
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to GetBlockResult object */
        fun fromJsonObjectToGetBlockResult(from: JsonObject): GetBlockResult {
            val ret = GetBlockResult()
            ret.apiVersion = from["api_version"].toString()
            val block = from["block"]
            if (block != null) {
                ret.block = JsonBlock.fromJsonToJsonBlock(block as JsonObject)
            }
            return ret
        }
    }
}