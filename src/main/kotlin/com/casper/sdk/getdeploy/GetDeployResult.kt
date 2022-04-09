package com.casper.sdk.getdeploy

class GetDeployResult {
    var api_version:String = ""
    var deploy:Deploy = Deploy()
    var execution_results:MutableList<JsonExecutionResult> = mutableListOf()
}