package com.casper.sdk.getstateroothash

import com.casper.sdk.BlockIdentifier
import com.casper.sdk.BlockIdentifierType
import com.casper.sdk.ConstValues
import org.junit.jupiter.api.Test

public class GetStateRootHashTest {

    @Test
    fun getStateRootHash() {
        val getStateRootHashTest = GetStateRootHashRPC()
        //Call 1:  Get state root hash with non parameter
        val bi = BlockIdentifier()
        bi.blockType = BlockIdentifierType.NONE
        val str: String = bi.toJsonStr(ConstValues.RPC_GET_STATE_ROOT_HASH)
        try {
           val stateRootHash1 = getStateRootHashTest.getStateRootHash(str)
           assert(stateRootHash1.length>0)
        } catch (e: IllegalArgumentException) {}
        //Call 2:  Get state root hash with BlockIdentifier of type Block Hash with correct Block Hash
        bi.blockType = BlockIdentifierType.HASH
        bi.blockHash = "fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e"
        val str2: String = bi.toJsonStr(ConstValues.RPC_GET_STATE_ROOT_HASH)
        try {
            val stateRootHash2 = getStateRootHashTest.getStateRootHash(str2)
            assert(stateRootHash2 == "bb3a1f9325c1da6820358f9b4981b84e0c28d924b0ef5776f6bb4cdd1328e261")
        } catch (e: IllegalArgumentException){}
        //Call3:  Get state root hash with BlockIdentifier of type Block Height with correct Block Height
        bi.blockType = BlockIdentifierType.HEIGHT
        bi.blockHeight = 673033u
        val str3: String = bi.toJsonStr(ConstValues.RPC_GET_STATE_ROOT_HASH)
        try {
            val stateRootHash3 = getStateRootHashTest.getStateRootHash(str3)
            assert(stateRootHash3 == "a3443a125cc55fe723d542b6d456fd42be7e412c948de924f575fb903cf2e6c5")
        } catch (e: IllegalArgumentException){}

        //Negative test

        //Call 4:  Get state root hash with BlockIdentifier of type Block Hash with incorrect Block Hash.
        // Expected result:  latest state root hash
        bi.blockType = BlockIdentifierType.HASH
        bi.blockHash = "aaa_fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e"
        val str4: String = bi.toJsonStr(ConstValues.RPC_GET_STATE_ROOT_HASH)
        try {
            val stateRootHash4 = getStateRootHashTest.getStateRootHash(str4)
            assert(stateRootHash4.length>0)
        } catch (e: IllegalArgumentException){}
        //Call5:  Get state root hash with BlockIdentifier of type Block Height with incorrect Block Height,  expected result:  Error
        bi.blockType = BlockIdentifierType.HEIGHT
        bi.blockHeight = 667303389999u
        try {
            val str5:  String = bi.toJsonStr(ConstValues.RPC_GET_STATE_ROOT_HASH)
            getStateRootHashTest.getStateRootHash(str5)
        } catch (e: IllegalArgumentException) {
            println("Error Get State Root Hash,  invalid parameter")
        }
    }
}