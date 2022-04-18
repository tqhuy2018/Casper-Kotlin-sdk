package com.casper.sdk.getstatus

import com.casper.sdk.getpeers.PeerEntry

/** Class built for storing GetStatusResult information, taken from info_get_status RPC method */
class GetStatusResult {
    var chainspecName: String = ""
    var apiVersion: String = ""
    var startingStateRootHash: String = ""
    var peers: MutableList<PeerEntry> = mutableListOf()
    lateinit var lastAddedBlockInfo:  MinimalBlockInfo
    lateinit var ourPublicSigningKey: String
    lateinit var roundLength: String
    lateinit var nextUpgrade: NextUpgrade
    var buildVersion: String = ""
    var uptime: String = ""
    // functions to check for optional values exist or not
    // function to check if round length exists
    fun isRoundLengthInit(): Boolean {
        if(this:: roundLength.isInitialized) {
            return true
        }
        return false
    }

    // function to check if nextUpgrade exists
    fun isNextUpgradeInit(): Boolean {
        if(this:: nextUpgrade.isInitialized) {
            return true
        }
        return false
    }

    // function to check if ourPublicSigningKey exists
    fun isOurPublicSigningKeyInit(): Boolean {
        if(this:: ourPublicSigningKey.isInitialized) {
            return true
        }
        return false
    }

    // function to check if lastAddedBlockInfo exists
    fun isLastAddedBlockInfoInit(): Boolean {
        if(this:: lastAddedBlockInfo.isInitialized) {
            return true
        }
        return false
    }

}