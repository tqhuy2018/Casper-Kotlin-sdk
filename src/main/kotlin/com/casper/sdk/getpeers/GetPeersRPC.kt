package com.casper.sdk.getpeers

import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.toJsonString

import net.jemzart.jsonkraken.values.JsonArray
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GetPeersRPC {
    var methodName:String = "info_get_peers"
    var casperURLTestNet:String = "https://node-clarity-testnet.make.services/rpc";
    fun getPeers() :GetPeersResult {
        val values = mapOf("id" to "1", "method" to "info_get_peers", "jsonrpc" to "2.0","params" to "[]")
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(this.casperURLTestNet))
            .POST(formData(values))
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        val json =response.body().toJson()
        val peerList = json.get("result").get("peers")
        var getPeersResult:GetPeersResult = GetPeersResult()
        getPeersResult.api_version = json.get("result").get("api_version").toString()
        if (peerList is JsonArray) {
            var counter:Int = 0
            for(peer in peerList) {
                var onePeerEntry:PeerEntry = PeerEntry()
                onePeerEntry.address = peer.get("address").toString()
                onePeerEntry.node_id = peer.get("nod_id").toString()
                getPeersResult.peers.add(onePeerEntry)
               // println("Peer number:" + counter + " node_id is:" + peer.get("node_id").toString() + " address:" + peer.get("address").toString())
                counter ++
            }
        }
        return getPeersResult
    }
    fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")
    fun formData(data: Map<String, String>): HttpRequest.BodyPublisher? {

        var res = data.map {(k, v) -> "\"${k}\":\"${v}\""}
            .joinToString(",")
        res = "{" + res + "}"
        println(res)
        return HttpRequest.BodyPublishers.ofString(res)
    }
}