package com.casper.sdk.getdeploy.ExecutionResult

import com.casper.sdk.ConstValues
import com.casper.sdk.common.classes.U512Class

class ExecutionResult {
    //use default as ExecutionResult success
    var itsType:String = ConstValues.EXECUTION_RESULT_SUCCESS
    var cost:U512Class = U512Class()
    var errorMessage:String = ""
    var effect:ExecutionEffect = ExecutionEffect()
    var transfers:MutableList<String> = mutableListOf()
}