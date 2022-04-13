package com.casper.sdk.getblocktransfer

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.values.JsonObject
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GetBlockTransferRPC {
    var methodName:String = ConstValues.RPC_GET_STATE_ROOT_HASH
    var casperURLTestNet:String = ConstValues.TESTNET_URL

    @Throws(IllegalArgumentException::class)
    fun getBlockTransfer(parameterStr:String):GetBlockTransfersResult {
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(this.casperURLTestNet))
            .POST((HttpRequest.BodyPublishers.ofString(parameterStr)))
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        val json =response.body().toJson()
        //Check for error
        if(json.get("error") != null) {
            throw IllegalArgumentException("Error get state root hash")
        } else { //If not error then get the state root hash
            val ret:GetBlockTransfersResult = GetBlockTransfersResult.fromJsonToGetBlockTransfersResult(json.get("result") as JsonObject)
            return ret
        }
    }
}