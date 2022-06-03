package com.casper.sdk.putdeploy

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PutDeployRPCTest {

    @Test
    fun readPrivateKey() {
        val putDeployRPC:PutDeployRPC = PutDeployRPC()
        putDeployRPC.readPrivateKey()
    }
}