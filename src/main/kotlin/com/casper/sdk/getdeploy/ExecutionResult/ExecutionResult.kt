package com.casper.sdk.getdeploy.ExecutionResult

import com.casper.sdk.ConstValues
import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing ExecutionResult information */
class ExecutionResult {
    //use default as ExecutionResult success
    var itsType: String = ConstValues.EXECUTION_RESULT_SUCCESS
    var cost: U512Class = U512Class()
    var errorMessage: String = ""
    var effect: ExecutionEffect = ExecutionEffect()
    var transfers: MutableList<String> = mutableListOf()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the ExecutionResult object */
        fun fromJsonToExecutionResult(from: JsonObject): ExecutionResult {
            val ret = ExecutionResult()
            val successJson = from["Success"].toJsonString()
            if(successJson != "null") {
                ret.itsType = ConstValues.EXECUTION_RESULT_SUCCESS
                val successJsonObject: JsonObject = from["Success"] as JsonObject
                ret.cost = U512Class.fromStringToU512(successJsonObject["cost"].toString())
                val transferArray :  JsonArray = successJsonObject["transfers"] as JsonArray
                ret.effect = ExecutionEffect.fromJsonToExecutionEffect(successJsonObject["effect"] as JsonObject)
                if (transferArray.count() > 0) {
                    val totalTransfer: Int = transferArray.count()-1
                    for(i in 0..totalTransfer) {
                        ret.transfers.add(transferArray[i].toString())
                    }
                }
            } else {
                ret.itsType = ConstValues.EXECUTION_RESULT_FAILURE
                val failureJsonObject: JsonObject = from["Failure"] as JsonObject
                ret.cost = U512Class.fromStringToU512(failureJsonObject["cost"].toString())
                ret.errorMessage = failureJsonObject["error_message"].toString()
                val transferArray :  JsonArray = failureJsonObject["transfers"] as JsonArray
                ret.effect = ExecutionEffect.fromJsonToExecutionEffect(failureJsonObject["effect"] as JsonObject)
                if (transferArray.count() > 0) {
                    val totalTransfer: Int = transferArray.count()-1
                    for(i in 0..totalTransfer) {
                        ret.transfers.add(transferArray[i].toString())
                    }
                }
            }
            return ret
        }
    }
}