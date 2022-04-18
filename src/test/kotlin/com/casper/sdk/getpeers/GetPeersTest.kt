package com.casper.sdk.getpeers

import org.junit.jupiter.api.Test

internal class GetPeersTest {

    @Test
    fun getPeers() {
        val getPeers = GetPeersRPC()
        val getPeersResult = getPeers.getPeers()
        assert(getPeersResult.api_version.length>0)
        assert(getPeersResult.peers.size>0)
        val onePeerEntry: PeerEntry = getPeersResult.peers[0]
        println("First peer entry nodeId: ${onePeerEntry.node_id} and address: ${onePeerEntry.address}")
    }
}