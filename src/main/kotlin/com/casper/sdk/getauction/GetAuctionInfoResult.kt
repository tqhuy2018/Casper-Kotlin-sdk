package com.casper.sdk.getauction

import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing GetAuctionInfoResult information,  taken from state_get_auction_info RPC method */
class GetAuctionInfoResult {
    var apiVersion: String = ""
    var auctionState: AuctionState = AuctionState()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to GetAuctionInfoResult object */
        fun fromJsonObjectToGetAuctionInfoResult(from: JsonObject): GetAuctionInfoResult {
            var ret: GetAuctionInfoResult = GetAuctionInfoResult()
            ret.apiVersion = from["api_version"].toString()
            ret.auctionState = AuctionState.fromJsonObjectToAuctionState(from["auction_state"] as JsonObject)
            return ret
        }
    }
}