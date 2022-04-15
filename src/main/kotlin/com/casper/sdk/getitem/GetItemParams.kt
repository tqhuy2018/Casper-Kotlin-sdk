package com.casper.sdk.getitem

import com.casper.sdk.ConstValues

class GetItemParams {
    var stateRootHash:String = ""
    var key:String = ""
    var path:MutableList<String> = mutableListOf()
    fun generateParameterStr():String {
        var pathStr = "[]"
        val totalPath:Int = path.count() - 1
        if (totalPath >= 0) {
            pathStr = "["
            for(i in 0..totalPath) {
                pathStr = pathStr + """${path[i].toString()}"""
                if (i<totalPath) {
                    pathStr = pathStr + ","
                }
            }
            pathStr = pathStr + "]"
        }
        return return """{"id" : 1,"method" : "${ConstValues.RPC_STATE_GET_ITEM}","params": {"state_root_hash":"${this.stateRootHash}","key":"${this.key}","path":"${pathStr}"},"jsonrpc" : "2.0"}"""
    }
}