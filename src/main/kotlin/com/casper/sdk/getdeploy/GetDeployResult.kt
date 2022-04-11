package com.casper.sdk.getdeploy

import com.casper.sdk.getdeploy.ExecutionResult.JsonExecutionResult

class GetDeployResult {
    var api_version:String = ""
    var deploy:Deploy = Deploy()
    var executionResults:MutableList<JsonExecutionResult> = mutableListOf()
}