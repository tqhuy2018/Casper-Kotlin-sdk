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
// This class handles account_put_deploy RPC call
class PutDeployRPC {
    companion object {
        var methodURL: String = ConstValues.TESTNET_URL
        var putDeployCounter:Int = 0
        /*
        This function does account_put_deploy RPC call
        This function initiate the process of sending POST request with given parameter in JSON string format like this:
        {"id": 1,"method": "account_put_deploy","jsonrpc": "2.0","params":
        [{"header": {"account": "0152a685e0edd9060da4a0d52e500d65e21789df3cbfcb878c91ffeaea756d1c53",
        "timestamp": "2022-06-28T11:35:19.349Z","ttl":"1h 30m","gas_price":1,
        "body_hash":"798a65dae48dbefb398ba2f0916fa5591950768b7a467ca609a9a631caf13001","dependencies": [],
        "chain_name": "casper-test"},
        "payment": {"ModuleBytes": {"module_bytes": "","args":
        [["amount",{"bytes": "0400ca9a3b","cl_type":"U512","parsed":"1000000000"}]]}},
        "session": {"Transfer": {"args": [["amount",{"bytes": "04005ed0b2","cl_type":"U512","parsed":"3000000000"}],
        ["target",{"bytes": "015f12b5776c66d2782a4408d3910f64485dd4047448040955573aa026256cfa0a","cl_type":"PublicKey","parsed":"015f12b5776c66d2782a4408d3910f64485dd4047448040955573aa026256cfa0a"}],
        ["id",{"bytes": "010000000000000000","cl_type":{"Option": "U64"},"parsed":0}],["spender",{"bytes": "01dde7472639058717a42e22d297d6cf3e07906bb57bc28efceac3677f8a3dc83b","cl_type":"Key","parsed":{"Hash":"hash-dde7472639058717a42e22d297d6cf3e07906bb57bc28efceac3677f8a3dc83b"}}]]}},
        "approvals": [{"signer": "0152a685e0edd9060da4a0d52e500d65e21789df3cbfcb878c91ffeaea756d1c53",
        "signature": "016596f09083d32eaffc50556f1a5d22202e8927d5aa3267639aff4b9d3412b5ae4a3475a5da6c1c1086a9a090b0e1090db5d7e1b7084bb60b2fee3ce9447a2a04"}],
        "hash": "65c6ccdc5aacc9dcd073ca79358bf0b5115061e8d561b3e6f461a34a6c5858f0"}]}
         Then the PutDeployResult is retrieved by parsing JsonObject result taken from the RPC POST request
         */
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
                        PutDeployResult.fromJsonObjectToPutDeployResult(json.get("result") as JsonObject)
                    println("Put deploy successfull with deploy hash:" + putDeployResult.deployHash)
                    putDeployCounter = 0
                    return putDeployResult.deployHash
                }
                return deployHash
            }
        }
        //This function does the work of turing a deploy object to a Json string, used as parameter to send for
        //POST method of the account_put_deploy RPC call.
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