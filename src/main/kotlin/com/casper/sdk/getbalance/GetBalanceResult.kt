package com.casper.sdk.getbalance

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.values.JsonObject

class GetBalanceResult {
    var apiVersion:String = ""
    var balanceValue:U512Class = U512Class()
    var merkleProof:String = ""
    companion object {
        fun  fromJsonObjectToGetBalanceResult(from:JsonObject):GetBalanceResult {
            var ret:GetBalanceResult = GetBalanceResult()
            ret.apiVersion = from["api_version"].toString()
            ret.merkleProof = from["merkle_proof"].toString()
            ret.balanceValue = U512Class.fromStringToU512(from["balance_value"].toString())
            return ret
        }
    }
}