package com.casper.sdk.getdeploy

import com.casper.sdk.ConstValues
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem_ModuleBytes
import com.casper.sdk.getdeploy.ExecutableDeployItem.RuntimeArgs
import com.casper.sdk.getdeploy.ExecutionResult.ExecutionResult
import com.casper.sdk.getdeploy.ExecutionResult.JsonExecutionResult
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
    @Throws(IllegalArgumentException::class)
    fun getDeployFromJsonStr(str:String):GetDeployResult {
        val getDeployResult:GetDeployResult = GetDeployResult()
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(postURL))
            .POST((HttpRequest.BodyPublishers.ofString(str)))
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        val json =response.body().toJson()
        //check for error getting deploy
        //error can happen if send the wrong deploy hash
        val jsonError = json.get("error").toJsonString()
        if (jsonError != "null") {
            throw IllegalArgumentException("Deploy hash is not valid")
        }
        val jsonResult:JsonObject = json.get("result") as JsonObject
        getDeployResult.api_version = jsonResult.get("api_version").toString()
        getDeployResult.deploy.header = DeployHeader.fromJsonToDeployHeader(jsonResult.get("deploy").get("header") as JsonObject)
        getDeployResult.deploy.hash = jsonResult.get("deploy").get("hash").toString()
        val deployPayment = jsonResult.get("deploy").get("payment") as JsonObject
        getDeployResult.deploy.payment = ExecutableDeployItem.fromJsonToExecutableDeployItem(deployPayment)
        val deploySession :JsonObject = jsonResult.get("deploy").get("session") as JsonObject
        getDeployResult.deploy.session = ExecutableDeployItem.fromJsonToExecutableDeployItem(deploySession)
        //get approvals
        getDeployResult.deploy.approvals = Deploy.fromJsonToListApprovals(jsonResult.get("deploy").get("approvals") as JsonArray)
        //get execution result
        val listER:JsonArray = jsonResult.get("execution_results") as JsonArray
        val totalER:Int = listER.count()
        for(i in 0.. totalER-1) {
            var jer: JsonExecutionResult = JsonExecutionResult()
            val oneItem = listER[i]
            jer.blockHash = oneItem.get("block_hash").toString()
            jer.result = ExecutionResult.fromJsonToExecutionResult(oneItem.get("result") as JsonObject)
            getDeployResult.executionResults.add(jer)
        }
        return getDeployResult
    }
    fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")
}