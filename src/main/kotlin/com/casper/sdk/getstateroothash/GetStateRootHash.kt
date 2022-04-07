package com.casper.sdk.getstateroothash
import com.casper.sdk.BlockIdentifier
import com.casper.sdk.BlockIdentifierType
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.toJsonString
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import com.casper.sdk.ConstValues

class GetStateRootHash {
    var methodName:String = "chain_get_state_root_hash"
    var casperURLTestNet:String = "https://node-clarity-testnet.make.services/rpc";
    fun getStateRootHash(parameterStr:String) :String {
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(this.casperURLTestNet))
            .POST((HttpRequest.BodyPublishers.ofString(parameterStr)))
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        val json =response.body().toJson()
        println(json.toJsonString())
        //Check for error
        if(json.get("error") != null) {
            print("Error")
            return "Error"
            //throw
        } else {
            //If not error then get the state root hash
            println(json.get("result").get("state_root_hash"))
            val state_root_hash = json.get("result").get("state_root_hash").toString()
            return state_root_hash
        }
    }
}