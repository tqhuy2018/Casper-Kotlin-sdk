package com.casper.sdk.getitem
import com.casper.sdk.ConstValues

/** Class built for storing GetItemParams, used to generate the parameter for state_get_item RPC call */
class GetItemParams {
    var stateRootHash: String = ""
    var key: String = ""
    var path: MutableList<String> = mutableListOf()
/** This function generate the parameter for the post method of the state_get_item RPC call */
fun generateParameterStr(): String {
        var pathStr = "[]"
        val totalPath: Int = path.count() - 1
        if (totalPath >= 0) {
            pathStr = "["
            for(i in 0..totalPath) {
                pathStr = pathStr + """${path[i].toString()}"""
                if (i<totalPath) {
                    pathStr = pathStr + ", "
                }
            }
            pathStr = pathStr + "]"
         }
        return """{"id": 1, "method": "${ConstValues.RPC_STATE_GET_ITEM}", "params": {"state_root_hash": "${this.stateRootHash}", "key": "${this.key}", "path": ${pathStr}}, "jsonrpc": "2.0"}"""
    }
}