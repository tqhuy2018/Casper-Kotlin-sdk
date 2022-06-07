package com.casper.sdk.serialization

import com.casper.sdk.getdeploy.DeployHeader
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DeploySerializeHelperTest {
    @Test
    fun testAll() {
        DeploySerializeHelper.serializeForHeader(DeployHeader())
    }
}