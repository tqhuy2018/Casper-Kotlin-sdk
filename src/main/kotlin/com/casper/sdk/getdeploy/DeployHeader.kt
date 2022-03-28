package com.casper.sdk.getdeploy

class DeployHeader {
    var account: String = ""
    var body_hash: String = ""
    var chain_name: String = ""
    var dependencies: MutableList<Any> = mutableListOf()
    var gas_price: Int = 0
    var timestamp: String = ""
    var ttl: String = ""
    constructor() {

    }
}
