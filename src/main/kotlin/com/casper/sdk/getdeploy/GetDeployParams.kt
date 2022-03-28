package com.casper.sdk.getdeploy

class GetDeployParams {
    var deploy_hash:String = "6e74f836d7b10dd5db7430497e106ddf56e30afee993dd29b85a91c1cd903583"
    fun toJsonStr():String {
        return """{"id" : 1,"method" : "info_get_deploy","params" : {"deploy_hash" : """"+this.deploy_hash + """"},"jsonrpc" : "2.0"}"""
    }
}