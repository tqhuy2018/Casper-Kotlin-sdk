package com.casper.sdk.getdeploy

data class Header(
    val account: String,
    val body_hash: String,
    val chain_name: String,
    val dependencies: List<Any>,
    val gas_price: Int,
    val timestamp: String,
    val ttl: String
)