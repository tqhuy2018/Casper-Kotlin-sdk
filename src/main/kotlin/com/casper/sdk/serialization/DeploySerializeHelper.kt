package com.casper.sdk.serialization

import com.casper.sdk.CasperUtils
import com.casper.sdk.getdeploy.DeployHeader

class DeploySerializeHelper {

    companion object {
        /**
        Serialization for the Deploy Header
        Rule for serialization:
        Returned result = deployHeader.account + U64.serialize(deployHeader.timeStampMiliSecondFrom1970InU64) + U64.serialize(deployHeader.ttlMilisecondsFrom1980InU64) + U64.serialize(gas_price) + deployHeader.bodyHash
         */
        fun serializeForHeader(header:DeployHeader):String {
            val timeStamp64:ULong = CasperUtils.fromTimeStampToU64Str("2020-11-17T00:39:24.072Z")
            val ttlMili:ULong = CasperUtils.ttlToMilisecond("3days")
            println("Milisecond:" + ttlMili)
            var ret:String = ""
            return ret
        }
    }
}