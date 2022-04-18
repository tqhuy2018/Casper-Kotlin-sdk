package com.casper.sdk.getpeers

/** Class built for storing GetPeersResult information */
class GetPeersResult {
    var api_version: String = ""
    var peers: MutableList<PeerEntry> = mutableListOf()
}