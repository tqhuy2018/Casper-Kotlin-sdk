package com.casper.sdk.putdeploy
import net.jemzart.jsonkraken.values.JsonObject

class PutDeployResult {
    var apiVersion: String = ""
    var deployHash:String = ""
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to GetAuctionInfoResult object */
        fun fromJsonObjectToGetAuctionInfoResult(from: JsonObject): PutDeployResult {
            var ret: PutDeployResult = PutDeployResult()
            ret.apiVersion = from["api_version"].toString()
            ret.deployHash = from["deploy_hash"].toString()
            return ret
        }
    }
}