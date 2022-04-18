package com.casper.sdk.getbalance
/** Class built for storing GetBalanceParams information */
class GetBalanceParams {
    var stateRootHash: String = ""
    var purseUref: String = ""
    /** This function generate the Json string parameter used for sending the state_get_balance RPC method */
    fun generateParameter(): String {
        return """{"method" :  "state_get_balance", "id" :  1, "params" : {"state_root_hash" :  "${this.stateRootHash}", "purse_uref": "${this.purseUref}"}, "jsonrpc" :  "2.0"}"""
    }
}