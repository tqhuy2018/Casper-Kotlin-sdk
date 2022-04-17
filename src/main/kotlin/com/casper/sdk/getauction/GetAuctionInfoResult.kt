package com.casper.sdk.getauction

import net.jemzart.jsonkraken.values.JsonObject

class GetAuctionInfoResult {
    var apiVersion:String = ""
    var auctionState:AuctionState = AuctionState()
    companion object {
        fun fromJsonObjectToGetAuctionInfoResult(from:JsonObject):GetAuctionInfoResult {
            var ret:GetAuctionInfoResult = GetAuctionInfoResult()
            ret.apiVersion = from["api_version"].toString()
            ret.auctionState = AuctionState.fromJsonObjectToAuctionState(from["auction_state"] as JsonObject)
            return ret
        }
    }
}