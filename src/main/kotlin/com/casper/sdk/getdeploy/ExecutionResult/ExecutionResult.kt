package com.casper.sdk.getdeploy.ExecutionResult

import com.casper.sdk.ConstValues
import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

class ExecutionResult {
    //use default as ExecutionResult success
    var itsType:String = ConstValues.EXECUTION_RESULT_SUCCESS
    var cost:U512Class = U512Class()
    var errorMessage:String = ""
    var effect:ExecutionEffect = ExecutionEffect()
    var transfers:MutableList<String> = mutableListOf()
    companion object {
        fun fromJsonToExecutionResult(from:JsonObject):ExecutionResult {
            var ret:ExecutionResult = ExecutionResult()
            val successJson = from["Success"].toJsonString()
            if(successJson != "null") {
                ret.itsType = ConstValues.EXECUTION_RESULT_SUCCESS
            } else {
                ret.itsType = ConstValues.EXECUTION_RESULT_FAILURE
            }
            return ret
        }
    }
}