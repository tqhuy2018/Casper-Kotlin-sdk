package com.casper.sdk.getdeploy

class DeployHeader {
    var account: String = ""
    var bodyHash: String = ""
    var chainName: String = ""
    var dependencies: MutableList<String> = mutableListOf()
    var gasPrice: ULong = 0u
    var timeStamp: String = ""
    var ttl: String = ""
    constructor() {

    }
}
