package com.casper.sdk.getdeploy

data class Result(
    val api_version: String,
    val deploy: Deploy,
    val execution_results: List<ExecutionResult>
)