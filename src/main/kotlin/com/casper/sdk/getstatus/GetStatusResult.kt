package com.casper.sdk.getstatus

import com.casper.sdk.getpeers.PeerEntry

class GetStatusResult {
    var chainspecName:String = ""
    var apiVersion:String = ""
    var startingStateRootHash:String = ""
    var peers:MutableList<PeerEntry> = mutableListOf()
    lateinit var lastAddedBlockInfo: MinimalBlockInfo
    lateinit var ourPublicSigningKey:String
    lateinit var roundLength:String
    lateinit var nextUpgrade:NextUpgrade
    var buildVersion:String = ""
    var uptime:String = ""
    //function check for optional values exist or not
    fun isRoundLengthInit():Boolean {
        if(this::roundLength.isInitialized) {
            return true
        }
        return false
    }
    fun isNextUpgradeInit():Boolean {
        if(this::nextUpgrade.isInitialized) {
            return true
        }
        return false
    }
    fun isOurPublicSigningKeyInit():Boolean {
        if(this::ourPublicSigningKey.isInitialized) {
            return true
        }
        return false
    }

    fun isLastAddedBlockInfoInit():Boolean {
        if(this::lastAddedBlockInfo.isInitialized) {
            return true
        }
        return false
    }

}