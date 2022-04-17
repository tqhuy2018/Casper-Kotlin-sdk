package com.casper.sdk.era

import com.casper.sdk.BlockIdentifier
import com.casper.sdk.BlockIdentifierType
import com.casper.sdk.ConstValues
import com.casper.sdk.getdeploy.ExecutionResult.Transform.EraInfo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GetEraBySwitchBlockRPCTest {

    @Test
    fun getEraInfo() {
        val getEraRPC:GetEraBySwitchBlockRPC = GetEraBySwitchBlockRPC()
        getEraRPC.methodURL = ConstValues.MAINNET_URL
        //Test 1: get block with no parameter, latest block information is retrieved
        val bi: BlockIdentifier = BlockIdentifier()
        bi.blockType = BlockIdentifierType.NONE;
        val parameter1:String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_ERA)
        try {
            val getEraInfoResult: GetEraInfoResult = getEraRPC.getEraInfo(parameter1)
            assert(getEraInfoResult.apiVersion.length > 0)
            assert(getEraInfoResult.isEraSummaryInit() == false)
        } catch (e:IllegalArgumentException) { }
        //Test2: get block with block identifier - correct block hash
        bi.blockType = BlockIdentifierType.HASH
        bi.blockHash = "fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e"
        val parameter2:String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_ERA)
        try {
            val getEraInfoResult: GetEraInfoResult = getEraRPC.getEraInfo(parameter2)
            assert(getEraInfoResult.apiVersion.length > 0)
            assert(getEraInfoResult.isEraSummaryInit() == false)
        } catch (e:IllegalArgumentException) {}
        //Test 3: test by block height
        bi.blockType = BlockIdentifierType.HEIGHT
        //1 to 4150 nothing, 10075 to 11198 none
        bi.blockHeight = 318u
        val parameter3: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_ERA)
        println("Get era with para3 :${parameter3}")
        try {
            val getEraInfoResult: GetEraInfoResult = getEraRPC.getEraInfo(parameter3)
            assert(getEraInfoResult.isEraSummaryInit() == true)
            assert(getEraInfoResult.eraSummary.blockHash == "f028506fb8add2edd3f9f3713e5acd7441a5d0cd433b863c627ff6542e8c0860")
            assert(getEraInfoResult.eraSummary.eraId.toString() == "2")
            assert(getEraInfoResult.eraSummary.merkleProof.length == 26336)
            assert(getEraInfoResult.eraSummary.storedValue.itsType == ConstValues.STORED_VALUE_ERA_INFO)
            var eraInfo:EraInfo = getEraInfoResult.eraSummary.storedValue.itsValue[0] as EraInfo
            assert(eraInfo.seigniorageAllocations.count() == 199)
            assert(eraInfo.seigniorageAllocations[0].isValidator == false)
            assert(eraInfo.seigniorageAllocations[0].validatorPublicKey == "01026ca707c348ed8012ac6a1f28db031fadd6eb67203501a353b867a08c8b9a80")
            assert(eraInfo.seigniorageAllocations[0].delegatorPublicKey == "01128ddb51119f1df535cf3a763996344ab0cc79038faaee0aaaf098a078031ce6")
            assert(eraInfo.seigniorageAllocations[0].amount.itsValue == "87735183835")

            if (getEraInfoResult.isEraSummaryInit() == true) {

            }
        } catch (e: IllegalArgumentException) {}
    }
}