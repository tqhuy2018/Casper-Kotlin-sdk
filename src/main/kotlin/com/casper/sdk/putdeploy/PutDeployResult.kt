package com.casper.sdk.putdeploy
import net.jemzart.jsonkraken.values.JsonObject
//This class hold the information of the account_put_deploy RPC calling method, which includes 2 attributes: api_version and deploy_hash
class PutDeployResult {
    var apiVersion: String = ""
    var deployHash:String = ""
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to PutDeployResult object */
        fun fromJsonObjectToPutDeployResult(from: JsonObject): PutDeployResult {
            val ret = PutDeployResult()
            ret.apiVersion = from["api_version"].toString()
            ret.deployHash = from["deploy_hash"].toString()
            return ret
        }
    }
}