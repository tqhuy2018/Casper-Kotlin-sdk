package com.casper.sdk.getblocktransfer

import com.casper.sdk.BlockIdentifier
import com.casper.sdk.BlockIdentifierType
import com.casper.sdk.ConstValues
import com.casper.sdk.getdeploy.ExecutionResult.Transform.Transfer
import org.junit.jupiter.api.Test



internal class GetBlockTransferRPCTest {

    @Test
    fun getBlockTransfer() {
        val getBlockTransferRPC = GetBlockTransferRPC()
        val bi = BlockIdentifier()
        bi.blockType = BlockIdentifierType.NONE
        val parameter1: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK_TRANSFER)
        try {
            val getBlockTransfersResult = getBlockTransferRPC.getBlockTransfer(parameter1)
            assert(getBlockTransfersResult.apiVersion.length > 0)
            assert(getBlockTransfersResult.blockHash.length>0)
        } catch (e: IllegalArgumentException) {}
        //Call 2:  Get block transfer with BlockIdentifier of type Block Hash with correct Block Hash
        bi.blockType = BlockIdentifierType.HASH
        bi.blockHash = "fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e"
        val parameter2: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK_TRANSFER)
        try {
            val getBlockTransfersResult = getBlockTransferRPC.getBlockTransfer(parameter2)
            assert(getBlockTransfersResult.blockHash=="fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e")
        } catch (e: IllegalArgumentException) {}
        //Call3:  Get state root hash with BlockIdentifier of type Block Height with correct Block Height
        bi.blockType = BlockIdentifierType.HEIGHT
        bi.blockHeight = 108u
        val parameter3: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK_TRANSFER)
        try {
            val getBlockTransfersResult = getBlockTransferRPC.getBlockTransfer(parameter3)
            assert(getBlockTransfersResult.blockHash=="b4da7924b335c6ae00141726ac42513edee521ab0ee3fccb1e20827cf96de84c")
            assert(getBlockTransfersResult.isTransferInit()==true)
            assert(getBlockTransfersResult.transfers.count() == 1)
            val transfer: Transfer = getBlockTransfersResult.transfers[0]
            assert(transfer.deployHash == "a1ac505b016d44a6d3ef5433a2660f549513e07c6d56adbd453494a74b065edd")
            assert(transfer.from == "account-hash-b383c7cc23d18bc1b42406a1b2d29fc8dba86425197b6f553d7fd61375b5e446")
            assert(transfer.to == "account-hash-94047221837cd1f747a567133fc44aea7a1a8da693a70bb6426ad514fbc54744")
            assert(transfer.source == "uref-b06a1ab0cfb52b5d4f9a08b68a5dbe78e999de0b0484c03e64f5c03897cf637b-007")
            assert(transfer.target == "uref-6f828fd4fa5d59da04ceb441fcfea17d43e09d5bcca03b1a27c225d6275c1e2b-004")
            assert(transfer.amount.itsValue == "1000000000000")
            assert(transfer.gas.itsValue == "0")
            assert(transfer.isIDExisted == false)
        } catch (e: IllegalArgumentException) {}
        //Negative path
        //Negative path 1:  Test with sending block identifier with wrong block hash,  latest block is retrieved
        bi.blockType = BlockIdentifierType.HASH
        bi.blockHash = "AAA_0101f5170c996cc02b581d8200f0d95a737840234f31bf1fa21cca35137f8507b0"
        val parameter5: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK_TRANSFER)
        try {
            val getBlockTransfersResult = getBlockTransferRPC.getBlockTransfer(parameter5)
            assert(getBlockTransfersResult.blockHash.length > 0)
            assert(getBlockTransfersResult.apiVersion.length > 0)
        } catch (e: IllegalArgumentException) {}
        //Negative path 2:  Test with sending block identifier with wrong block height - example very big block height
        //Error is thrown
        bi.blockType = BlockIdentifierType.HEIGHT
        bi.blockHeight = 11111111111111111u
        val parameter6: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK)
        try {
            getBlockTransferRPC.getBlockTransfer(parameter6)
        } catch (e: IllegalArgumentException) {
            println("Error get block with too big height")
        }
    }
}