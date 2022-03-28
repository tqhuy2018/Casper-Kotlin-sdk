package com.casper.sdk.getpeers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GetPeersTest {

    @Test
    fun getPeers() {
        var getPeers = GetPeers()
        var getPeersResult = getPeers.getPeers()
        assert(getPeersResult.api_version.length>0)
        assert(getPeersResult.peers.size>0)
    }
}