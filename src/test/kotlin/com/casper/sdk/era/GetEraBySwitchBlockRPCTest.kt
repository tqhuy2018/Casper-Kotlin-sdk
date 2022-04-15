package com.casper.sdk.era

import com.casper.sdk.BlockIdentifier
import com.casper.sdk.BlockIdentifierType
import com.casper.sdk.ConstValues
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GetEraBySwitchBlockRPCTest {

    @Test
    fun getEraInfo() {
        val getEraRPC:GetEraBySwitchBlockRPC = GetEraBySwitchBlockRPC()
        getEraRPC.methodURL = ConstValues.MAINNET_URL
        //Test 1: get block with no parameter, latest block information is retrieved
        val bi: BlockIdentifier = BlockIdentifier()
        //Test 3: test by block height
        bi.blockType = BlockIdentifierType.HEIGHT
        for(i in 1..30000) {
            //1 to 4150 nothing, 10075 to 11198 none
            bi.blockHeight = 10075u + i.toULong()
            val parameter3: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK)
            println("Get era for block height:${bi.blockHeight}")
            println("Get era with para:${parameter3}")
            try {
                val getEraInfoResult: GetEraInfoResult = getEraRPC.getEraInfo(parameter3)
                if (getEraInfoResult.isEraSummaryInit() == true) {
                    println("AAAAAAA_AAAAAA_Get block era summary not nut at block height:${bi.blockHeight}")
                }
            } catch (e: IllegalArgumentException) {
            }
        }
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
        } catch (e:IllegalArgumentException) { }

        }

}