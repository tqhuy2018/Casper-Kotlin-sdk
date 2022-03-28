package com.casper.sdk.getdeploy

import com.casper.sdk.getpeers.GetPeersResult
import com.casper.sdk.getpeers.PeerEntry
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import netscape.javascript.JSObject
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GetDeployResult {
    var api_version:String = ""
    var methodName:String = "info_get_peers"
    var casperURLTestNet:String = "https://node-clarity-testnet.make.services/rpc";
    lateinit var deploy:Deploy
    var execution_results:MutableList<JsonExecutionResult> = mutableListOf()
    fun getDeployFromJsonStr(str:String):GetDeployResult {
        println("param:" + str)
        var getDeployResult:GetDeployResult = GetDeployResult()
       // val deployParams = mapOf("deploy_hash" to "6e74f836d7b10dd5db7430497e106ddf56e30afee993dd29b85a91c1cd903583")
      //  val values = mapOf("id" to "1", "method" to "info_get_peers", "jsonrpc" to "2.0","params" to deployParams)
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(this.casperURLTestNet))
            //.POST((HttpRequest.BodyPublishers.ofString("""{"id" : 1,"method" : "info_get_deploy","params" : {"deploy_hash" : "6e74f836d7b10dd5db7430497e106ddf56e30afee993dd29b85a91c1cd903583"},"jsonrpc" : "2.0"}""")))
            .POST((HttpRequest.BodyPublishers.ofString(str)))
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //println(response.body())
        val json =response.body().toJson()
        println(json.toJsonString())
        //val peerList = json.get("result").get("peers")
        getDeployResult.api_version = json.get("result").get("api_version").toString()
        val deployPayment:String = json.get("result").get("deploy").get("payment").toJsonString()
        println("Deploy payment is:" + deployPayment)
        val deployPaymentMB = json.get("result").get("deploy").get("payment")
        if (deployPaymentMB is JsonObject) {
            val innerMB:String = deployPaymentMB.get("ModuleBytes").toJsonString()
            if(innerMB != "null" ) {
                println("InnerMB----:" + innerMB)
            } else {
                println("Not module bytes")
            }
        }
      //  val dpm:String = deployPayment.get("ModuleBytes").toString()
      //  print("Deploy payment modulebytes:" + dpm)

        return getDeployResult

    }
    fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")

    /*companion object {
        fun getDeployFromJsonStr():GetDeployResult {
            var getDeployResult:GetDeployResult = GetDeployResult()
            return getDeployResult
        }
    }*/
}