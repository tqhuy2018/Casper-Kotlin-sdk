package com.casper.sdk.putdeploy

import com.casper.sdk.ConstValues
import com.casper.sdk.crypto.Secp256k1Handle
import com.casper.sdk.getdeploy.Approval
import com.casper.sdk.getdeploy.Deploy
import com.casper.sdk.getdictionary.GetDictionaryItemResult
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJson
import net.jemzart.jsonkraken.values.JsonObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
class PutDeployRPC {
    companion object {
        var methodURL: String = ConstValues.TESTNET_URL
        var putDeployCounter:Int = 0
        fun putDeploy(deploy: Deploy) :String {
            val deployHash:String = deploy.hash
            val jsonStr:String = fromDeployToJsonString(deploy)
            val url = URL(methodURL)
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection
            con.setRequestMethod("POST")
            con.setRequestProperty("Content-Type", "application/json")
            con.setRequestProperty("Accept", "application/json");
            con.doOutput = true
            con.outputStream.use { os ->
                val input: ByteArray = jsonStr.toByteArray()
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

                if (json.get("error") != null) {
                    val message = json.get("error").get("message")
                    if (message == "invalid deploy: the approval at index 0 is invalid: asymmetric key error: failed to verify secp256k1 signature: signature error") {
                        putDeployCounter++
                        if (putDeployCounter < 10) {
                            deploy.approvals.removeAt(0)
                            val oneA = Approval()
                            oneA.signer = deploy.header.account
                            oneA.signature = "02" + Secp256k1Handle.signMessage(deploy.hash, PutDeployUtils.privateKey)
                            deploy.approvals.add(oneA)
                            putDeploy(deploy)
                        } else {
                            return ConstValues.PUT_DEPLOY_ERROR_MESSAGE
                        }
                    } else {
                        return ConstValues.PUT_DEPLOY_ERROR_MESSAGE
                    }
                } else {
                    val putDeployResult: PutDeployResult =
                        PutDeployResult.fromJsonObjectToGetAuctionInfoResult(json.get("result") as JsonObject)
                    println("Put deploy successfull with deploy hash:" + putDeployResult.deployHash)
                    putDeployCounter = 0
                    return putDeployResult.deployHash
                }
                return deployHash
            }
        }
        fun fromDeployToJsonString(deploy: Deploy):String {
            val headerString:String = "\"header\": {\"account\": \"" + deploy.header.account + "\",\"timestamp\": \"" + deploy.header.timeStamp + "\",\"ttl\":\""+deploy.header.ttl+"\",\"gas_price\":"+deploy.header.gasPrice+",\"body_hash\":\"" + deploy.header.bodyHash + "\",\"dependencies\": [],\"chain_name\": \"" + deploy.header.chainName + "\"}"
            val paymentJsonStr:String = "\"payment\": " + ExecutableDeployItemHelper.toJsonString(deploy.payment)
            val sessionJsonStr:String = "\"session\": " + ExecutableDeployItemHelper.toJsonString(deploy.session)
            val approvalJsonStr: String = "\"approvals\": [{\"signer\": \"" + deploy.approvals.get(0).signer + "\",\"signature\": \"" + deploy.approvals.get(0).signature + "\"}]"
            val hashStr = "\"hash\": \"" + deploy.hash + "\""
            val deployJsonStr: String = "{\"id\": 1,\"method\": \"account_put_deploy\",\"jsonrpc\": \"2.0\",\"params\": [{" + headerString + ","+paymentJsonStr + "," + sessionJsonStr + "," + approvalJsonStr + "," + hashStr + "}]}"
            return deployJsonStr
        }
    }
}