package com.casper.sdk.getauction

import com.casper.sdk.BlockIdentifier
import com.casper.sdk.BlockIdentifierType
import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.get
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
internal class GetAuctionInfoRPCTest {

    @Test
    fun getAuction() {
        val getAuctionInfoRPC: GetAuctionInfoRPC = GetAuctionInfoRPC()
        //Test 1:  get auction with no parameter,  latest auction state information is retrieved
        val bi:  BlockIdentifier = BlockIdentifier()
        bi.blockType = BlockIdentifierType.NONE
        val parameter1: String = bi.toJsonStr(ConstValues.RPC_STATE_GET_AUTION_INFO)
        try {
            val getAuctionInfoResult = getAuctionInfoRPC.getAuctionInfo(parameter1)
            assert(getAuctionInfoResult.apiVersion == "1.4.5")
            assert(getAuctionInfoResult.auctionState.stateRootHash.length > 0)
        } catch (e: IllegalArgumentException) {}
        //Test2:  get auction with block identifier - correct block hash
        bi.blockType = BlockIdentifierType.HASH
        bi.blockHash = "fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e"
        val parameter2: String = bi.toJsonStr(ConstValues.RPC_STATE_GET_AUTION_INFO)
        try {
            val getAuctionInfoResult = getAuctionInfoRPC.getAuctionInfo(parameter2)
            assert(getAuctionInfoResult.apiVersion == "1.4.5")
            assert(getAuctionInfoResult.auctionState.stateRootHash == "bb3a1f9325c1da6820358f9b4981b84e0c28d924b0ef5776f6bb4cdd1328e261")
            assert(getAuctionInfoResult.auctionState.blockHeight.toString() == "673041")
            assert(getAuctionInfoResult.auctionState.eraValidators.count() == 2)
            assert(getAuctionInfoResult.auctionState.eraValidators[0].eraId.toString() == "4348")
            assert(getAuctionInfoResult.auctionState.eraValidators[0].validatorWeights.count() == 100)
            assert(getAuctionInfoResult.auctionState.eraValidators[0].validatorWeights[0].publicKey == "0101f5170c996cc02b581d8200f0d95a737840234f31bf1fa21cca35137f8507b0")
            assert(getAuctionInfoResult.auctionState.eraValidators[0].validatorWeights[0].weight.itsValue == "1285021522858")
            assert(getAuctionInfoResult.auctionState.eraValidators[0].validatorWeights[1].publicKey == "01028e248170a7f328bf7a04696d8f271a1debb54763e05e537eefc1cf24531bc7")
            assert(getAuctionInfoResult.auctionState.eraValidators[0].validatorWeights[1].weight.itsValue == "3488957206790")
            assert(getAuctionInfoResult.auctionState.eraValidators[0].validatorWeights[2].publicKey == "0106618e1493f73ee0bc67ffbad4ba4e3863b995d61786d9b9a68ec7676f697981")
            assert(getAuctionInfoResult.auctionState.eraValidators[0].validatorWeights[2].weight.itsValue == "2297106992905")
            assert(getAuctionInfoResult.auctionState.bids.count() == 1550)
            assert(getAuctionInfoResult.auctionState.bids[922].bid.delegators.count() == 0)
            assert(getAuctionInfoResult.auctionState.bids[937].bid.delegators.count() == 3)
            assert(getAuctionInfoResult.auctionState.bids[937].bid.delegators[0].bondingPurse == "uref-ef690cc92ed92a373d07e8941c7439c2d06baf3959c1e6fc7cbaf1e54a63fc27-007")
            assert(getAuctionInfoResult.auctionState.bids[937].bid.delegators[0].delegatee == "019c8766207d0a8a9ae12f3d7b576895080deac2ed0cfbfd1250097528a7078b4d")
            assert(getAuctionInfoResult.auctionState.bids[937].bid.delegators[0].publicKey == "013002bfaa8c71963db3479a00c595bbe7d59716dddd645c09c25343970bd403a2")
            assert(getAuctionInfoResult.auctionState.bids[937].bid.delegators[0].stakedAmount.itsValue == "26000489431")
            assert(getAuctionInfoResult.auctionState.bids[961].bid.delegators.count() == 1007)
            assert(getAuctionInfoResult.auctionState.bids[961].bid.delegators[0].bondingPurse == "uref-57021e5fd50b5ccfc2a79c963746698b7df79a2c8ea6b709291709fd3d4fa871-007")
            assert(getAuctionInfoResult.auctionState.bids[961].bid.delegators[0].delegatee == "01a03c687285634a0115c0af1015ab0a53809f4826ee863c94e32ce48bcfdf447d")
            assert(getAuctionInfoResult.auctionState.bids[961].bid.delegators[0].publicKey == "0100100b21c886596436f7bbe7dc083f6dc7e9ce700545fa8ddfa20272fc3648bf")
            assert(getAuctionInfoResult.auctionState.bids[961].bid.delegators[0].stakedAmount.itsValue == "1323067801")
        } catch (e: IllegalArgumentException) {}
        //Test3:  get auction with block identifier - correct block height
        bi.blockType = BlockIdentifierType.HEIGHT
        bi.blockHeight = 100u
        val parameter3: String = bi.toJsonStr(ConstValues.RPC_STATE_GET_AUTION_INFO)
        try {
            val getAuctionInfoResult = getAuctionInfoRPC.getAuctionInfo(parameter3)
            assert(getAuctionInfoResult.apiVersion == "1.4.5")
            assert(getAuctionInfoResult.auctionState.stateRootHash == "c50822987f4b0b620825f7b8941c7bd446a426c8b8fa2f19bec432727a32d196")
            assert(getAuctionInfoResult.auctionState.blockHeight.toString() == "100")
            assert(getAuctionInfoResult.auctionState.eraValidators.count() == 2)
            assert(getAuctionInfoResult.auctionState.bids.count() == 57)
            assert(getAuctionInfoResult.auctionState.bids[0].publicKey == "0106ca7c39cd272dbf21a86eeb3b36b7c26e2e9b94af64292419f7862936bca2ca")
            assert(getAuctionInfoResult.auctionState.bids[0].bid.inactive == false)
            assert(getAuctionInfoResult.auctionState.bids[0].bid.bondingPurse == "uref-b71209b79d2e2770995ef7d18de48598187f1a6b3518f324a615d1d1e91bac16-007")
            assert(getAuctionInfoResult.auctionState.bids[0].bid.stakedAmount.itsValue == "1000000000000")
            assert(getAuctionInfoResult.auctionState.bids[0].bid.delegationRate.toString() == "10")
            assert(getAuctionInfoResult.auctionState.bids[0].bid.delegators.count() == 0)
            assert(getAuctionInfoResult.auctionState.bids.count() == 57)
            assert(getAuctionInfoResult.auctionState.bids[10].bid.delegators.count() == 0)
        } catch (e: IllegalArgumentException) {}
        //Negative test
        //Test 4:  get state root hash based on non-existing block height (too big height)
        bi.blockType = BlockIdentifierType.HEIGHT
        bi.blockHeight = 99999999999999u
        val parameter4: String = bi.toJsonStr(ConstValues.RPC_STATE_GET_AUTION_INFO)
        try {
            getAuctionInfoRPC.getAuctionInfo(parameter4)
        } catch (e: IllegalArgumentException) {
            println("Error get auction information with wrong block height")
        }
        //Test 5:  get state root hash based on non-existing block hash
        //Lastest auction info is retrieved
        bi.blockType = BlockIdentifierType.HASH
        bi.blockHash = "AAA"
        val parameter5: String = bi.toJsonStr(ConstValues.RPC_STATE_GET_AUTION_INFO)
        try {
            val getAuctionInfoResult = getAuctionInfoRPC.getAuctionInfo(parameter5)
            assert(getAuctionInfoResult.apiVersion == "1.4.5")
            assert(getAuctionInfoResult.auctionState.stateRootHash.length > 0)
        } catch (e: IllegalArgumentException) { }
    }
}