package com.casper.sdk.getstatus

import org.junit.jupiter.api.Test

internal class GetStatusRPCTest {
    @Test
    fun getStatusResult() {
        val getStatusRPC = GetStatusRPC()
        val getStatusResult :  GetStatusResult = getStatusRPC.getStatusResult()
        assert(getStatusResult.chainspecName == "casper-test")
        assert(getStatusResult.peers.count()>0)
        assert(getStatusResult.peers[0].node_id.length > 10)
        assert(getStatusResult.peers[0].address.length > 10)
        println("First peer entry node_id:  ${getStatusResult.peers[0].node_id} and address: ${getStatusResult.peers[0].address}")
        assert(getStatusResult.isNextUpgradeInit() == false)
        assert(getStatusResult.uptime.length > 0)
        assert(getStatusResult.buildVersion.length > 0)
    }
}