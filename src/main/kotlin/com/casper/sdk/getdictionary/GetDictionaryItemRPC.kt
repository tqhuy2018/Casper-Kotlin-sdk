package com.casper.sdk.getdictionary

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.values.JsonObject
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GetDictionaryItemRPC {
    var methodURL: String = ConstValues.TESTNET_URL
    @Throws(IllegalArgumentException:: class)
    fun getDictionaryItem(parameterStr: String):  GetDictionaryItemResult {
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
            throw IllegalArgumentException("Error get dictionary item")
        } else { //If not error then get the dictionary item
            val ret: GetDictionaryItemResult = GetDictionaryItemResult.fromJsonObjectToGetDictionaryItemResult(json.get("result") as JsonObject)
            return ret
        }
    }
}