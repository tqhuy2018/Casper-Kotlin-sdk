package com.casper.sdk
import com.casper.sdk.getpeers.GetPeersRPC
import com.casper.sdk.getpeers.GetPeersResult
import com.casper.sdk.getstateroothash.GetStateRootHashRPC
class CasperSDK {
    @Throws(IllegalArgumentException:: class)
    fun getStateRootHash(parameterStr:String):String {
        val getStateRootHashRPC:GetStateRootHashRPC = GetStateRootHashRPC()
        try {
            val stateRootHash:String = getStateRootHashRPC.getStateRootHash(parameterStr)
            return stateRootHash
        } catch (e:IllegalArgumentException) {}
        return ""
    }
    fun getPeers():GetPeersResult {
        val getPeersRPC:GetPeersRPC = GetPeersRPC()
        val getPeersResult:GetPeersResult = getPeersRPC.getPeers()
        return  getPeersResult
    }
}