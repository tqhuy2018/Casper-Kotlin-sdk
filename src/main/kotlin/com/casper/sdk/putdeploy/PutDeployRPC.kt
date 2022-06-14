package com.casper.sdk.putdeploy

import com.casper.sdk.ConstValues
import com.casper.sdk.getauction.GetAuctionInfoResult
import com.casper.sdk.getdeploy.Deploy
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.values.JsonObject
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class PutDeployRPC {
    companion object {
        var methodURL: String = ConstValues.TESTNET_URL
        @Throws(IllegalArgumentException:: class)
        fun putDeploy(deploy: Deploy): PutDeployResult {
            val jsonStr:String = PutDeployRPC.fromDeployToJsonString(deploy)
            val client = HttpClient.newBuilder().build()
            val request = HttpRequest.newBuilder()
                .uri(URI.create(this.methodURL))
                .POST((HttpRequest.BodyPublishers.ofString(jsonStr)))
                .header("Content-Type",  "application/json")
                .build()
            val response = client.send(request,  HttpResponse.BodyHandlers.ofString())
            val json =response.body().toJson()
            //Check for error
            if(json.get("error") != null) {
                throw IllegalArgumentException("Error get auction")
            } else {
               val putDeployResult:PutDeployResult = PutDeployResult.fromJsonObjectToGetAuctionInfoResult(json.get("result") as JsonObject)
                println("Put deploy successfull with deploy hash:" + putDeployResult.deployHash)
                return  putDeployResult
            }
        }
        fun fromDeployToJsonString(deploy: Deploy):String {
            val headerString:String = "\"header\": {\"account\": \"" + deploy.header.account + "\",\"timestamp\": \"" + deploy.header.timeStamp + "\",\"ttl\":\""+deploy.header.ttl+"\",\"gas_price\":"+deploy.header.gasPrice+",\"body_hash\":\"" + deploy.header.bodyHash + "\",\"dependencies\": [],\"chain_name\": \"" + deploy.header.chainName + "\"}"
            val paymentJsonStr:String = "\"payment\": " + ExecutableDeployItemHelper.toJsonString(deploy.payment)
            val sessionJsonStr:String = "\"session\": " + ExecutableDeployItemHelper.toJsonString(deploy.session)
            val approvalJsonStr: String = "\"approvals\": [{\"signer\": \"" + deploy.approvals.get(0).signer + "\",\"signature\": \"" + deploy.approvals.get(0).signature + "\"}]"
            val hashStr = "\"hash\": \"" + deploy.hash + "\""
            val deployJsonStr: String = "{\"id\": 1,\"method\": \"account_put_deploy\",\"jsonrpc\": \"2.0\",\"params\": [{" + headerString + ","+paymentJsonStr + "," + sessionJsonStr + "," + approvalJsonStr + "," + hashStr + "}]}"
            return deployJsonStr
        }

        @Throws(IllegalArgumentException:: class)
        fun getAuctionInfo(parameterStr: String): GetAuctionInfoResult {
            val client = HttpClient.newBuilder().build()
            val request = HttpRequest.newBuilder()
                .uri(URI.create(this.methodURL))
                .POST((HttpRequest.BodyPublishers.ofString(parameterStr)))
                .header("Content-Type",  "application/json")
                .build()
            val response = client.send(request,  HttpResponse.BodyHandlers.ofString())
            val json =response.body().toJson()
            //Check for error
            if(json.get("error") != null) {
                throw IllegalArgumentException("Error get auction")
            } else { //If not error then get the state root hash
                val ret: GetAuctionInfoResult = GetAuctionInfoResult.fromJsonObjectToGetAuctionInfoResult(json.get("result") as JsonObject)
                return ret
            }
        }

    }
}