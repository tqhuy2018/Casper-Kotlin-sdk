package com.casper.sdk.getdeploy

data class StoredContractByHash(
    val args: List<List<Any>>,
    val entry_point: String,
    val hash: String
)