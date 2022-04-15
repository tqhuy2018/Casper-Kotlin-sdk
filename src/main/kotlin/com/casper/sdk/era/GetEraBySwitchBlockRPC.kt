package com.casper.sdk.era

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.values.JsonObject
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GetEraBySwitchBlockRPC {
    var methodURL:String = ConstValues.TESTNET_URL
    @Throws(IllegalArgumentException::class)
    fun getEraInfo(parameterStr:String): GetEraInfoResult {
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(this.methodURL))
            .POST((HttpRequest.BodyPublishers.ofString(parameterStr)))
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        val json =response.body().toJson()
        //Check for error
        if(json.get("error") != null) {
            throw IllegalArgumentException("Error get block")
        } else { //If not error then get the state root hash
            val ret: GetEraInfoResult = GetEraInfoResult.fromJsonToGetEraInfoResult(json.get("result") as JsonObject)
            return ret
        }
    }
}