package com.casper.sdk.getpeers

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.toJsonString

import net.jemzart.jsonkraken.values.JsonArray
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/** Class built for info_get_peers RPC call */
class GetPeersRPC {
    var methodURL: String = ConstValues.TESTNET_URL
    /**
     * This function initiate the process of sending POST request with given parameter in JSON string format
     * The parameter for POST method is:
     * {"params" : [], "id" : 1, "method": "info_get_peers", "jsonrpc" : "2.0"}
     * The GetPeersResult is retrieved by parsing JsonObject result from the POST request
     */
    fun getPeers() : GetPeersResult {
        val parameterStr = """{"id" :  1, "method" :  "${ConstValues.RPC_INFO_GET_PEERS}", "params" :  [], "jsonrpc" :  "2.0"}"""
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create(this.methodURL))
            .POST((HttpRequest.BodyPublishers.ofString(parameterStr)))
            .header("Content-Type",  "application/json")
            .build()
        val response = client.send(request,  HttpResponse.BodyHandlers.ofString())
        val json =response.body().toJson()
        val peerList = json.get("result").get("peers")
        val getPeersResult: GetPeersResult = GetPeersResult()
        getPeersResult.api_version = json.get("result").get("api_version").toString()
        if (peerList is JsonArray) {
            for(peer in peerList) {
                val onePeerEntry: PeerEntry = PeerEntry()
                onePeerEntry.address = peer.get("address").toString()
                onePeerEntry.node_id = peer.get("node_id").toString()
                getPeersResult.peers.add(onePeerEntry)
            }
        }
        return getPeersResult
    }
}