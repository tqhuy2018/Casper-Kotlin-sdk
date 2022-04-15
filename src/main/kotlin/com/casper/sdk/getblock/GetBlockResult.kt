package com.casper.sdk.getblock

import net.jemzart.jsonkraken.values.JsonObject

class GetBlockResult {
    var apiVersion:String = ""
    lateinit var block:JsonBlock
    fun isBlockInit():Boolean{
        if(this::block.isInitialized) {
            return true
        }
        return false
    }

    companion object {
        fun fromJsonObjectToGetBlockResult(from:JsonObject):GetBlockResult {
            var ret:GetBlockResult = GetBlockResult()
            ret.apiVersion = from["api_version"].toString()
            val block = from["block"]
            if (block != null) {
                ret.block = JsonBlock.fromJsonToJsonBlock(block as JsonObject)
            }
            return ret
        }
    }
}