package com.casper.sdk.era

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.values.JsonObject
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
/** Class built for chain_get_era_info_by_switch_block RPC call */
class GetEraBySwitchBlockRPC {
    var methodURL: String = ConstValues.TESTNET_URL
    /**
     * This function initiate the process of sending POST request with given parameter in JSON string format
     * The input parameterStr is used to send to server as parameter of the POST request to get the result back.
     * The input parameterStr is somehow like this: 
     * {"params" :  [], "id" :  1, "method": "chain_get_era_info_by_switch_block", "jsonrpc" :  "2.0"}
     * if you wish to send without any param along with the RPC call
     * or: 
     * {"method" :  "chain_get_era_info_by_switch_block", "id" :  1, "params" :  {"block_identifier" :  {"Hash" : "d16cb633eea197fec519aee2cfe050fe9a3b7e390642ccae8366455cc91c822e"}}, "jsonrpc" :  "2.0"}
     * if you wish to send the block hash along with the POST method in the RPC call
     * or: 
     * {"method" :  "chain_get_era_info_by_switch_block", "id" :  1, "params" :  {"block_identifier" :  {"Height" : 100}}, "jsonrpc" :  "2.0"}
     * if you wish to send the block height along with the POST method in the RPC call
     * The parameterStr is generated by the BlockIdentifier class, declared in file BlockIdentifier.kotlin
     * Then the GetEraInfoResult is retrieved by parsing JsonObject result
     * If the result is error,  then an exception is thrown
     * Else the GetEraInfoResult is taken by parsing the  retrieving JsonObject
     */
    @Throws(IllegalArgumentException:: class)
    fun getEraInfo(parameterStr: String): GetEraInfoResult {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create(this.methodURL))
            .POST((HttpRequest.BodyPublishers.ofString(parameterStr)))
            .header("Content-Type",  "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val json =response.body().toJson()
        //Check for error
        if(json.get("error") != null) {
            throw IllegalArgumentException("Error get block")
        } else { //If not error then get the state root hash
            return GetEraInfoResult.fromJsonToGetEraInfoResult(json.get("result") as JsonObject)
        }
    }
}