package com.casper.sdk.getdeploy.JsonExecutionResultPackage

class ExecutionEffect {
    var operations:MutableList<CasperOperation> = mutableListOf()
    var transforms:MutableList<TransformEntry> = mutableListOf()
}