package com.casper.sdk.getstateroothash

import com.casper.sdk.getstateroothash.GetStateRootHash
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GetStateRootHashTest {

    @Test
    fun getStateRootHash() {
        var getStateRootHashTest:GetStateRootHash = GetStateRootHash()
        var state_root_hash = getStateRootHashTest.getStateRootHash()
        assert(state_root_hash.length>0)
    }
}