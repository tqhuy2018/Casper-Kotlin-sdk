package com.casper.sdk.getauction

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class AuctionState {
    var stateRootHash:String = ""
    var blockHeight:ULong = 0u
    var eraValidators:MutableList<JsonEraValidators> = mutableListOf()
    var bids:MutableList<JsonBids> = mutableListOf()
    companion object {
        fun fromJsonObjectToAuctionState(from:JsonObject):AuctionState {
            var ret:AuctionState = AuctionState()
            ret.stateRootHash = from["state_root_hash"].toString()
            ret.blockHeight = from["block_height"].toJsonString().toULong()
            // Get EraValidators
            val eraValidators = from["era_validators"]
            if(eraValidators != null) {
                val eraValidatorList = from["era_validators"] as JsonArray
                val totalEV:Int = eraValidatorList.count() - 1
                if(totalEV >= 0) {
                    for(i in 0.. totalEV) {
                        val oneJEV:JsonEraValidators = JsonEraValidators.fromJsonObjectToJsonEraValidators(eraValidatorList[i] as JsonObject)
                        ret.eraValidators.add(oneJEV)
                    }
                }
            }
            // Get Bids
            val bids = from["bids"]
            if(bids != null) {
                val bidsList = from["bids"] as JsonArray
                val totalBid:Int = bidsList.count() - 1
                var counter:Int = 0
                if(totalBid >= 0) {
                    for(i in 0.. totalBid) {
                        counter += 1
                        val oneBids:JsonBids = JsonBids.fromJsonObjectToJsonBids(bidsList[i] as JsonObject)
                        ret.bids.add(oneBids)
                    }
                }
            }
            return ret
        }
    }
}