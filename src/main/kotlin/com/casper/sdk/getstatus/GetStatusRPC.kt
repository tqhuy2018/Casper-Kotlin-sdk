package com.casper.sdk.getstatus

import com.casper.sdk.ConstValues
import com.casper.sdk.getpeers.GetPeersResult
import com.casper.sdk.getpeers.PeerEntry
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import netscape.javascript.JSObject
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GetStatusRPC {
    var methodName:String = ConstValues.RPC_INFO_GET_STATUS
    var methodURL:String = ConstValues.TESTNET_URL
    fun getStatusResult() : GetStatusResult {
        val parameterStr = """{"id" : 1,"method" : "info_get_status","params" : [],"jsonrpc" : "2.0"}"""
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(this.methodURL))
            .POST((HttpRequest.BodyPublishers.ofString(parameterStr)))
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        val json =response.body().toJson()
        val resultJson:JsonObject = json.get("result") as JsonObject
        val peerList = resultJson.get("peers")
        var getStatusResult: GetStatusResult = GetStatusResult()
        getStatusResult.apiVersion = resultJson.get("api_version").toString()
        getStatusResult.chainspecName = resultJson.get("chainspec_name").toString()
        getStatusResult.startingStateRootHash = resultJson.get("starting_state_root_hash").toString()
        if (peerList is JsonArray) {
            for(peer in peerList) {
                var onePeerEntry: PeerEntry = PeerEntry()
                onePeerEntry.address = peer.get("address").toString()
                onePeerEntry.node_id = peer.get("node_id").toString()
                getStatusResult.peers.add(onePeerEntry)
            }
        }
        val lastABI = resultJson.get("last_added_block_info").toJsonString()
        if(lastABI != "null") {
            println("ABI not null")
            getStatusResult.lastAddedBlockInfo = MinimalBlockInfo.fromJsonToMinimalBlockInfo(resultJson.get("last_added_block_info") as JsonObject)
        }
        val ourPublicSigningKey = resultJson.get("our_public_signing_key")
        if(ourPublicSigningKey!=null) {
            getStatusResult.ourPublicSigningKey = ourPublicSigningKey.toString()
        }
        val roundLength = resultJson.get("round_length")
        if(roundLength != null) {
            getStatusResult.roundLength = roundLength.toString()
        }
        val nextUpgrade = resultJson.get("next_upgrade")
        if(nextUpgrade != null) {
            getStatusResult.nextUpgrade = NextUpgrade.fromJsonToNextUpgrade(nextUpgrade as JsonObject)
        }
        getStatusResult.buildVersion = resultJson.get("build_version").toString()
        getStatusResult.uptime = resultJson.get("uptime").toString()
        return getStatusResult
    }
}