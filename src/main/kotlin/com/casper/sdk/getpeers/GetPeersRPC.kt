package com.casper.sdk.getpeers

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.values.JsonArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

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
        val url = URL(methodURL)
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        con.setRequestMethod("POST")
        con.setRequestProperty("Content-Type", "application/json")
        con.setRequestProperty("Accept", "application/json");
        con.doOutput = true
        con.outputStream.use { os ->
            val input: ByteArray = parameterStr.toByteArray()
            os.write(input, 0, input.size)
        }
        BufferedReader(
            InputStreamReader(con.inputStream, "utf-8")
        ).use {
            val response = StringBuilder()
            var responseLine: String? = null
            while (it.readLine().also { responseLine = it } != null) {
                response.append(responseLine!!.trim { it <= ' ' })
            }
            val json = response.toString().toJson()
            if(json.get("error") != null) {
                throw IllegalArgumentException("Error get state root hash")
            }
            val peerList = json.get("result").get("peers")
            val getPeersResult = GetPeersResult()
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
        /*val parameterStr = """{"id" :  1, "method" :  "${ConstValues.RPC_INFO_GET_PEERS}", "params" :  [], "jsonrpc" :  "2.0"}"""
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
        return getPeersResult*/
    }
}