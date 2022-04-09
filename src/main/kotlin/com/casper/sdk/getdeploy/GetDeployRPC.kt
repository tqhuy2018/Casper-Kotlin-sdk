package com.casper.sdk.getdeploy

import com.casper.sdk.ConstValues
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem_ModuleBytes
import com.casper.sdk.getdeploy.ExecutableDeployItem.RuntimeArgs
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GetDeployRPC {

    fun getDeployFromJsonStr(str:String):GetDeployResult {
        println("param:" + str)
        var getDeployResult:GetDeployResult = GetDeployResult()
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(ConstValues.TESTNET_URL))
            .POST((HttpRequest.BodyPublishers.ofString(str)))
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        val json =response.body().toJson()
        println(json.toJsonString())
        getDeployResult.api_version = json.get("result").get("api_version").toString()
        val deployPayment:String = json.get("result").get("deploy").get("payment").toJsonString()
        println("Deploy payment is:" + deployPayment)
        var deploy:Deploy = Deploy()
        deploy.hash = json.get("result").get("deploy").get("hash").toString()
        var executableDeployItem:ExecutableDeployItem = ExecutableDeployItem()
        val deployPaymentMB = json.get("result").get("deploy").get("payment")
        if (deployPaymentMB is JsonObject) {
            val innerPayment:String = deployPaymentMB.get("ModuleBytes").toJsonString()
            if(innerPayment != "null" ) {
                println("InnerMB----:" + innerPayment)
                executableDeployItem.itsType = ExecutableDeployItem.MODULE_BYTES
                var eMB: ExecutableDeployItem_ModuleBytes = ExecutableDeployItem_ModuleBytes()
                //eMB.module_bytes = "aaaa"
                eMB.module_bytes = deployPaymentMB.get("ModuleBytes").get("module_bytes").toString()
                executableDeployItem.itsValue.add(eMB)
                deploy.payment = executableDeployItem
                eMB.args = RuntimeArgs.fromJsonArrayToObj(deployPaymentMB.get("ModuleBytes").get("args") as JsonArray)
                /*var obj =  deploy.payment.itsValue.component1()
                if (obj is ExecutableDeployItem_ModuleBytes) {
                    println("First of deploy payment is module bytes")
                } else {
                    println("First of deploy payment is NOT module bytes")
                }*/
            } else {
                println("Not module bytes")
            }
        }
        return getDeployResult
    }
    fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")
}