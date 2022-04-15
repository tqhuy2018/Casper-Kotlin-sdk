package com.casper.sdk.getdeploy

import com.casper.sdk.ConstValues

class GetDeployParams {
    var deploy_hash:String = "6e74f836d7b10dd5db7430497e106ddf56e30afee993dd29b85a91c1cd903583"
    fun generatePostParameterStr():String {
        return """{"id" : 1,"method" : ${ConstValues.RPC_INFO_GET_DEPLOY},"params" : {"deploy_hash" : """"+this.deploy_hash + """"},"jsonrpc" : "2.0"}"""
    }
}