package com.casper.sdk.getstatus

import com.casper.sdk.ConstValues
import com.casper.sdk.getpeers.PeerEntry
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
/** Class built for info_get_status RPC call */
class GetStatusRPC {
    var methodURL: String = ConstValues.TESTNET_URL
    /**
     * This function initiate the process of sending POST request with given parameter in JSON string format
     * The parameter for POST method is:
     * {"method" :  "state_get_item", "id" :  1, "params" : {"state_root_hash" :  "d360e2755f7cee816cce3f0eeb2000dfa03113769743ae5481816f3983d5f228", "key": "withdraw-df067278a61946b1b1f784d16e28336ae79f48cf692b13f6e40af9c7eadb2fb1", "path": []}, "jsonrpc" :  "2.0"}
     * The GetStatusResult is retrieved by parsing JsonObject result from the POST request
     */
    fun getStatusResult() :  GetStatusResult {
        val parameterStr = """{"id" :  1, "method" :  "info_get_status", "params" :  [], "jsonrpc" :  "2.0"}"""
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
            var responseLine: String?
            while (it.readLine().also { responseLine = it } != null) {
                response.append(responseLine!!.trim { it <= ' ' })
            }
            val json = response.toString().toJson()
            if(json.get("error") != null) {
                throw IllegalArgumentException("Error get state root hash")
            }
            val resultJson: JsonObject = json.get("result") as JsonObject
            val peerList = resultJson.get("peers")
            val getStatusResult = GetStatusResult()
            getStatusResult.apiVersion = resultJson.get("api_version").toString()
            getStatusResult.chainspecName = resultJson.get("chainspec_name").toString()
            getStatusResult.startingStateRootHash = resultJson.get("starting_state_root_hash").toString()
            if (peerList is JsonArray) {
                for(peer in peerList) {
                    val onePeerEntry = PeerEntry()
                    onePeerEntry.address = peer.get("address").toString()
                    onePeerEntry.node_id = peer.get("node_id").toString()
                    getStatusResult.peers.add(onePeerEntry)
                }
            }
            val lastABI = resultJson.get("last_added_block_info").toJsonString()
            if(lastABI != "null") {
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
}