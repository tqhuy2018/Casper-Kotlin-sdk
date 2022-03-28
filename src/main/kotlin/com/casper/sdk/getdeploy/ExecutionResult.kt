package com.casper.sdk.getdeploy

class ExecutionResult(
    var cost: String,
    var effect: ExecutionEffect,
    var error_message: String,
    var transfers: List<String>,//in type of TransferAddr
    var itsType:Boolean
)