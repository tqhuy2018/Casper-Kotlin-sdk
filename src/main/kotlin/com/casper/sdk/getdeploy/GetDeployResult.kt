package com.casper.sdk.getdeploy

class GetDeployResult {
    var api_version:String = ""
    lateinit var deploy:Deploy
    var execution_results:MutableList<JsonExecutionResult> = mutableListOf()
}