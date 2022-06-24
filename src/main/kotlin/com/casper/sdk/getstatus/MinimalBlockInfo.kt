package com.casper.sdk.getstatus

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing MinimalBlockInfo information */
class MinimalBlockInfo {
    var creator: String = ""
    var eraId:  ULong = 0u
    var blockHash: String = ""
    var height: ULong = 0u
    var stateRootHash: String = ""
    var timeStamp: String = ""
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the MinimalBlockInfo object */
        fun  fromJsonToMinimalBlockInfo(from: JsonObject) :  MinimalBlockInfo {
            val ret  = MinimalBlockInfo()
            ret.creator = from["creator"].toString()
            val eraIDStr = from["era_id"].toJsonString()
            ret.eraId = eraIDStr.toULong()
            ret.blockHash = from["hash"].toString()
            val heigthStr = from["height"].toJsonString()
            ret.height = heigthStr.toULong()
            ret.stateRootHash = from["state_root_hash"].toString()
            ret.timeStamp = from["timestamp"].toString()
            return ret
        }
    }
}