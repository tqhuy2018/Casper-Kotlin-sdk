package com.casper.sdk.getstatus

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

class MinimalBlockInfo {
    var creator: String = ""
    var eraId:  ULong = 0u
    var blockHash: String = ""
    var height: ULong = 0u
    var stateRootHash: String = ""
    var timeStamp: String = ""
    companion object {
        fun  fromJsonToMinimalBlockInfo(from: JsonObject) :  MinimalBlockInfo {
            var ret :  MinimalBlockInfo = MinimalBlockInfo()
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