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
    var postURL:String = ""
    fun getDeployFromJsonStr(str:String):GetDeployResult {
        var getDeployResult:GetDeployResult = GetDeployResult()
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(postURL))
            .POST((HttpRequest.BodyPublishers.ofString(str)))
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        val json =response.body().toJson()
        getDeployResult.api_version = json.get("result").get("api_version").toString()
        var deploy:Deploy = Deploy()
        deploy.hash = json.get("result").get("deploy").get("hash").toString()
        var executableDeployItem:ExecutableDeployItem = ExecutableDeployItem()
        val deployPaymentMB = json.get("result").get("deploy").get("payment") as JsonObject
        println("------------------------------------Get PAYMENT!---------------------------------------------")
        getDeployResult.deploy.payment = ExecutableDeployItem.fromJsonToExecutableDeployItem(deployPaymentMB)
        //Get session
        println("------------------------------------GET SESSIOIN!---------------------------------------------")
        val deploySessionMB :JsonObject = json.get("result").get("deploy").get("session") as JsonObject
        getDeployResult.deploy.session = ExecutableDeployItem.fromJsonToExecutableDeployItem(deploySessionMB)
        return getDeployResult
    }
    fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")
}