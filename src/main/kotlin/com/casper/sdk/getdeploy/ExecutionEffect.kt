package com.casper.sdk.getdeploy

data class ExecutionEffect(
    val operations: List<Any>,
    val transforms: List<Transform>
)