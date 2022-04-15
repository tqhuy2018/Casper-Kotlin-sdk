package com.casper.sdk.getblock

import com.casper.sdk.BlockIdentifier
import com.casper.sdk.BlockIdentifierType
import com.casper.sdk.ConstValues
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GetBlockRPCTest {

    @Test
    fun getBlock() {
        val getBlock:GetBlockRPC = GetBlockRPC()
        //Test 1: get block with no parameter, latest block information is retrieved
        val bi: BlockIdentifier = BlockIdentifier()
        bi.blockType = BlockIdentifierType.NONE;
        val parameter1:String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK)
        try {
            val getBlockResult: GetBlockResult = getBlock.getBlock(parameter1)
            assert(getBlockResult.block.blockHash.length>0)
            assert(getBlockResult.block.header.bodyHash.length>0)
            assert(getBlockResult.block.header.parentHash.length>0)
        } catch (e:IllegalArgumentException) { }
        //Test2: get block with block identifier - correct block hash
        bi.blockType = BlockIdentifierType.HASH
        bi.blockHash = "fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e"
        val parameter2:String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK)
        try {
            val getBlockResult2 = getBlock.getBlock(parameter2)
            assert(getBlockResult2.block.blockHash == "fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e")
            // block header assertion
            val blockHeader:JsonBlockHeader = getBlockResult2.block.header
            assert(blockHeader.parentHash == "df381e8a75ff5d2bf6ec86a032f14fcb20777a97b437ff5c7f9d4b444176c537")
            assert(blockHeader.stateRootHash == "bb3a1f9325c1da6820358f9b4981b84e0c28d924b0ef5776f6bb4cdd1328e261")
            assert(blockHeader.bodyHash == "6e78696e3840343c137ea2d4df8bc32f2807116d020b494dfda1aafca91167bc")
            assert(blockHeader.randomBit == false)
            assert(blockHeader.accumulatedSeed == "cee004428f64b0b5b8bc53c2755d976b6d83199a537cdb1ca433230a5ebd8735")
            assert(blockHeader.isEraEndInit() == false)
            assert(blockHeader.timestamp == "2022-04-07T03:14:18.240Z")
            assert(blockHeader.eraId.toString() == "4348")
            assert(blockHeader.height.toString() == "673041")
            assert(blockHeader.protocolVersion == "1.4.5")
            // block body assertion
            val body:JsonBlockBody = getBlockResult2.block.body
            assert(body.proposer=="01a03c687285634a0115c0af1015ab0a53809f4826ee863c94e32ce48bcfdf447d")
            assert(body.transferHashes.count() == 0)
            assert(body.deployHashes.count() == 1)
            assert(body.deployHashes[0]=="db058cbd45f55d07f8b7a54c025bfb808ce748d516e0ebc130d635e3974068cf")
            // proofs assertion
            val proofs:MutableList<JsonProof> = getBlockResult2.block.proofs
            assert(proofs.count() == 100)
            assert(proofs[0].publicKey == "0101f5170c996cc02b581d8200f0d95a737840234f31bf1fa21cca35137f8507b0")
            assert(proofs[0].signature == "011eff8fe46b617dde8ff3c694307fe23584f1f0cfba6f4827e13c7199f35f8525059c3232fddbc693189a1d8e9ebe0f481666c63b8582c662dbc7be9f17ae890d")
        } catch (e:IllegalArgumentException) {}

        //Test2: get block with block identifier - correct block height
        bi.blockType = BlockIdentifierType.HEIGHT
        bi.blockHeight = 3345u
        val parameter3:String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK)
        println(parameter3)
        try {
            val getBlockResult3 = getBlock.getBlock(parameter3)
            assert(getBlockResult3.block.blockHash == "2a1a22b8379a9c8ff1e77d858f5173a68b934e188c663dce3dead0c6faead471")
            // block header assertion
            val blockHeader:JsonBlockHeader = getBlockResult3.block.header
            assert(blockHeader.parentHash == "1943532965c8c963232dc49de3466c7d245c44f7da19e275a046db3e2c171496")
            assert(blockHeader.stateRootHash == "48d641c376e631707ed640a39d69c3b7783481931f3584546246762e16b5bd21")
            assert(blockHeader.bodyHash == "719da26911a6c97fd579d33cac5c7492b1faa77144a14db895d5799e577bec53")
            assert(blockHeader.randomBit == false)
            assert(blockHeader.accumulatedSeed == "f2a59808392ec8cfd094a0d34c1a43d9d72ca2bd4f0d3a65202746ed89efe150")
            assert(blockHeader.isEraEndInit() == true)
            assert(blockHeader.eraEnd.eraReport)
            assert(blockHeader.timestamp == "2021-04-12T09:37:00.416Z")
            assert(blockHeader.eraId.toString() == "43")
            assert(blockHeader.height.toString() == "3345")
            assert(blockHeader.protocolVersion == "1.0.0")
            // block body assertion
            val body:JsonBlockBody = getBlockResult3.block.body
            assert(body.proposer=="01a03c687285634a0115c0af1015ab0a53809f4826ee863c94e32ce48bcfdf447d")
            assert(body.transferHashes.count() == 0)
            assert(body.deployHashes.count() == 1)
            assert(body.deployHashes[0]=="db058cbd45f55d07f8b7a54c025bfb808ce748d516e0ebc130d635e3974068cf")
            // proofs assertion
            val proofs:MutableList<JsonProof> = getBlockResult3.block.proofs
            assert(proofs.count() == 100)
            assert(proofs[0].publicKey == "0101f5170c996cc02b581d8200f0d95a737840234f31bf1fa21cca35137f8507b0")
            assert(proofs[0].signature == "011eff8fe46b617dde8ff3c694307fe23584f1f0cfba6f4827e13c7199f35f8525059c3232fddbc693189a1d8e9ebe0f481666c63b8582c662dbc7be9f17ae890d")
        } catch (e:IllegalArgumentException) {}
    }
}