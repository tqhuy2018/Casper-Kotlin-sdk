package com.casper.sdk.getbalance

class GetBalanceParams {
    var stateRootHash:String = ""
    var purseUref:String = ""
    fun generateParameter():String {
        return """{"method" : "state_get_balance","id" : 1,"params" :{"state_root_hash" : "${this.stateRootHash}","purse_uref":"${this.purseUref}"},"jsonrpc" : "2.0"}"""
    }
}