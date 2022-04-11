package com.casper.sdk.getdeploy.ExecutionResult

class ExecutionEffect {
    var operations:MutableList<CasperOperation> = mutableListOf()
    var transforms:MutableList<TransformEntry> = mutableListOf()
}