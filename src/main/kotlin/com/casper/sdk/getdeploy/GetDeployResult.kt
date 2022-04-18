package com.casper.sdk.getdeploy

import com.casper.sdk.getdeploy.ExecutionResult.JsonExecutionResult
/** Class built for storing GetDeployResult information, taken from info_get_deploy RPC method */
class GetDeployResult {
    var api_version: String = ""
    var deploy: Deploy = Deploy()
    var executionResults: MutableList<JsonExecutionResult> = mutableListOf()
}