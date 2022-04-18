package com.casper.sdk.getblock

import com.casper.sdk.BlockIdentifier
import com.casper.sdk.BlockIdentifierType
import com.casper.sdk.ConstValues
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GetBlockRPCTest {

    @Test
    fun getBlock() {
        val getBlock: GetBlockRPC = GetBlockRPC()
        //Test 1:  get block with no parameter,  latest block information is retrieved
        val bi:  BlockIdentifier = BlockIdentifier()
        bi.blockType = BlockIdentifierType.NONE
        val parameter1: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK)
        try {
            val getBlockResult:  GetBlockResult = getBlock.getBlock(parameter1)
            assert(getBlockResult.block.blockHash.length>0)
            assert(getBlockResult.block.header.bodyHash.length>0)
            assert(getBlockResult.block.header.parentHash.length>0)
        } catch (e: IllegalArgumentException) { }
        //Test2:  get block with block identifier - correct block hash
        bi.blockType = BlockIdentifierType.HASH
        bi.blockHash = "fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e"
        val parameter2: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK)
        try {
            val getBlockResult2 = getBlock.getBlock(parameter2)
            assert(getBlockResult2.block.blockHash == "fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e")
            // block header assertion
            val blockHeader: JsonBlockHeader = getBlockResult2.block.header
            assert(blockHeader.parentHash == "df381e8a75ff5d2bf6ec86a032f14fcb20777a97b437ff5c7f9d4b444176c537")
            assert(blockHeader.stateRootHash == "bb3a1f9325c1da6820358f9b4981b84e0c28d924b0ef5776f6bb4cdd1328e261")
            assert(blockHeader.bodyHash == "6e78696e3840343c137ea2d4df8bc32f2807116d020b494dfda1aafca91167bc")
            assert(blockHeader.randomBit == false)
            assert(blockHeader.accumulatedSeed == "cee004428f64b0b5b8bc53c2755d976b6d83199a537cdb1ca433230a5ebd8735")
            assert(blockHeader.isEraEndInit() == false)
            assert(blockHeader.timestamp == "2022-04-07T03: 14: 18.240Z")
            assert(blockHeader.eraId.toString() == "4348")
            assert(blockHeader.height.toString() == "673041")
            assert(blockHeader.protocolVersion == "1.4.5")
            // block body assertion
            val body: JsonBlockBody = getBlockResult2.block.body
            assert(body.proposer=="01a03c687285634a0115c0af1015ab0a53809f4826ee863c94e32ce48bcfdf447d")
            assert(body.transferHashes.count() == 0)
            assert(body.deployHashes.count() == 1)
            assert(body.deployHashes[0]=="db058cbd45f55d07f8b7a54c025bfb808ce748d516e0ebc130d635e3974068cf")
            // proofs assertion
            val proofs: MutableList<JsonProof> = getBlockResult2.block.proofs
            assert(proofs.count() == 100)
            assert(proofs[0].publicKey == "0101f5170c996cc02b581d8200f0d95a737840234f31bf1fa21cca35137f8507b0")
            assert(proofs[0].signature == "011eff8fe46b617dde8ff3c694307fe23584f1f0cfba6f4827e13c7199f35f8525059c3232fddbc693189a1d8e9ebe0f481666c63b8582c662dbc7be9f17ae890d")
        } catch (e: IllegalArgumentException) {}
        //Test2:  get block with block identifier - correct block height
        bi.blockType = BlockIdentifierType.HEIGHT
        bi.blockHeight = 3345u
        val parameter3: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK)
        try {
            val getBlockResult3 = getBlock.getBlock(parameter3)
            assert(getBlockResult3.block.blockHash == "2a1a22b8379a9c8ff1e77d858f5173a68b934e188c663dce3dead0c6faead471")
            // block header assertion
            val blockHeader: JsonBlockHeader = getBlockResult3.block.header
            assert(blockHeader.parentHash == "1943532965c8c963232dc49de3466c7d245c44f7da19e275a046db3e2c171496")
            assert(blockHeader.stateRootHash == "48d641c376e631707ed640a39d69c3b7783481931f3584546246762e16b5bd21")
            assert(blockHeader.bodyHash == "719da26911a6c97fd579d33cac5c7492b1faa77144a14db895d5799e577bec53")
            assert(blockHeader.randomBit == false)
            assert(blockHeader.accumulatedSeed == "f2a59808392ec8cfd094a0d34c1a43d9d72ca2bd4f0d3a65202746ed89efe150")
            assert(blockHeader.isEraEndInit() == true)
            assert(blockHeader.eraEnd.eraReport.equivocators.count() == 0)
            assert(blockHeader.eraEnd.eraReport.rewards.count() == 100)
            assert(blockHeader.eraEnd.eraReport.rewards[0].validator == "010268bb35bd370a499ba775877aaadef1ba87bff64ca527ae55f88cd8af9791de")
            assert(blockHeader.eraEnd.eraReport.rewards[0].amount.toString() == "496041757239")
            assert(blockHeader.eraEnd.eraReport.inactiveValidators.count() == 0)
            assert(blockHeader.eraEnd.nextEraValidatorWeights.count() == 100)
            assert(blockHeader.eraEnd.nextEraValidatorWeights[0].validator == "010268bb35bd370a499ba775877aaadef1ba87bff64ca527ae55f88cd8af9791de")
            assert(blockHeader.eraEnd.nextEraValidatorWeights[0].weight.itsValue == "2084445503223")
            assert(blockHeader.timestamp == "2021-04-12T09: 37: 00.416Z")
            assert(blockHeader.eraId.toString() == "43")
            assert(blockHeader.height.toString() == "3345")
            assert(blockHeader.protocolVersion == "1.0.0")
            // block body assertion
            val body: JsonBlockBody = getBlockResult3.block.body
            assert(body.proposer=="01c642ea7442d6271119cee66f76848525d67932de3f1ec36e236e37b38d97a03b")
            assert(body.transferHashes.count() == 0)
            assert(body.deployHashes.count() == 0)
            // proofs assertion
            val proofs: MutableList<JsonProof> = getBlockResult3.block.proofs
            assert(proofs.count() == 100)
            assert(proofs[0].publicKey == "010268bb35bd370a499ba775877aaadef1ba87bff64ca527ae55f88cd8af9791de")
            assert(proofs[0].signature == "018fda329a99957b73272d2b9597b413c40136a2fe45a254db85c8616d3b39d6897b793ca51478b2a4d0896245a8618176ff11005c6a10eaf67d5d838047ae0e0a")
        } catch (e: IllegalArgumentException) {}
        //Test 4:  Test with existing block hash where the body->deploy_hashes is a list of multiple value
        //Base on the block at this address
        //https: //testnet.cspr.live/block/cadcf10d323ad4dc9b5b11f1e940a8d51287888a9d7322e39302f911181edcd0
        bi.blockType = BlockIdentifierType.HASH
        bi.blockHash = "cadcf10d323ad4dc9b5b11f1e940a8d51287888a9d7322e39302f911181edcd0"
        val parameter4: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK)
        try {
            val getBlockResult4 = getBlock.getBlock(parameter4)
            assert(getBlockResult4.block.blockHash == "cadcf10d323ad4dc9b5b11f1e940a8d51287888a9d7322e39302f911181edcd0")
            // block header assertion
            val blockHeader: JsonBlockHeader = getBlockResult4.block.header
            assert(blockHeader.parentHash == "44d3dd56284eb635289f9947956eb37c2b35e41de790867894fd8728af12b157")
            assert(blockHeader.stateRootHash == "32c36e787fb8a2eac0a726f6d42742300e3c7c96b08a858406cc90968acddbf7")
            assert(blockHeader.bodyHash == "949acecbdc9cfcf7b11c8768083fd85c2958105a0fbdfdaa08e61805ca80bb3e")
            assert(blockHeader.randomBit == true)
            assert(blockHeader.accumulatedSeed == "320bdd706cf5a29f5744e8c7346ba8828f2b2506e47e3cb2369f49f7e2ac6eb5")
            assert(blockHeader.isEraEndInit() == false)
            assert(blockHeader.timestamp == "2022-04-15T08: 45: 23.840Z")
            assert(blockHeader.eraId.toString() == "4447")
            assert(blockHeader.height.toString() == "694679")
            assert(blockHeader.protocolVersion == "1.4.5")
            // block body assertion
            val body: JsonBlockBody = getBlockResult4.block.body
            assert(body.proposer=="01a03c687285634a0115c0af1015ab0a53809f4826ee863c94e32ce48bcfdf447d")
            assert(body.transferHashes.count() == 0)
            assert(body.deployHashes.count() == 5)
            assert(body.deployHashes[0]=="1c5a7be92131e6130ca97ae50a526b0c875bfe45aca300b732fa67731708a522")
            assert(body.deployHashes[1]=="120d335ec367fbe37a245e37e7dc9a31ce0b9537bba8042300af53e5f6aa0cad")
            assert(body.deployHashes[2]=="7064cb1bcf9414da181684452f5b2a47499c9f4582cd85b897fbedaff54a2a04")
            assert(body.deployHashes[3]=="71909e9568ed0d8cbb60f31e25102db485c3cd0d0f5623a0d4e5f89afe4c90c2")
            assert(body.deployHashes[4]=="d88ba9fdceff53b5d4803534a41470b191e6f76eb7a936f85b8fcb4fa42a7390")
            // proofs assertion
            val proofs: MutableList<JsonProof> = getBlockResult4.block.proofs
            assert(proofs.count() == 99)
            assert(proofs[0].publicKey == "0101f5170c996cc02b581d8200f0d95a737840234f31bf1fa21cca35137f8507b0")
            assert(proofs[0].signature == "0150e4cc09528fa642bc725b017cfdd3219f6898612f9c18ec819fa7510c6fe09bb55ccdd8474ef9d94e061b8bc158147ab6e647921e3fa8b8ecaa430513d2210a")
        } catch (e: IllegalArgumentException) {}
        //Negative path
        //Negative path 1:  Test with sending block identifier with wrong block hash,  latest block is retrieved
        bi.blockType = BlockIdentifierType.HASH
        bi.blockHash = "AAA_0101f5170c996cc02b581d8200f0d95a737840234f31bf1fa21cca35137f8507b0"
        val parameter5: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK)
        try {
            val getBlockResult:  GetBlockResult = getBlock.getBlock(parameter5)
            assert(getBlockResult.block.blockHash.length>0)
            assert(getBlockResult.block.header.bodyHash.length>0)
            assert(getBlockResult.block.header.parentHash.length>0)
        } catch (e: IllegalArgumentException) { }
        //Negative path 2:  Test with sending block identifier with wrong block height - example very big block height
        //Error is thrown
        bi.blockType = BlockIdentifierType.HEIGHT
        bi.blockHeight = 11111111111111111u
        val parameter6: String = bi.toJsonStr(ConstValues.RPC_CHAIN_GET_BLOCK)
        try {
            getBlock.getBlock(parameter6)
        } catch (e: IllegalArgumentException) {
            println("Error get block with too big height")
        }
    }
}