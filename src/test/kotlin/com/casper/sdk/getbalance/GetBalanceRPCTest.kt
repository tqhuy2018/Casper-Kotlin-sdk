package com.casper.sdk.getbalance

import org.junit.jupiter.api.Test

internal class GetBalanceRPCTest {

    @Test
    fun getBalance() {
        val getBalanceRPC = GetBalanceRPC()
        val getBalanceParams = GetBalanceParams()
        getBalanceParams.purseUref = "uref-be1dc0fd639a3255c1e3e5e2aa699df66171e40fa9450688c5d718b470e057c6-007"
        getBalanceParams.stateRootHash = "8b463b56f2d124f43e7c157e602e31d5d2d5009659de7f1e79afbd238cbaa189"
        val parameterStr: String = getBalanceParams.generateParameter()
        val getBalanceResult: GetBalanceResult = getBalanceRPC.getBalance(parameterStr)
        assert(getBalanceResult.balanceValue.itsValue == "522693296224")
        assert(getBalanceResult.apiVersion.length > 0)
        assert(getBalanceResult.merkleProof.length == 31766)
        //negative path1 - send wrong state root hash
        getBalanceParams.stateRootHash = "AAA"
        val parameterStr2: String = getBalanceParams.generateParameter()
        try {
            getBalanceRPC.getBalance(parameterStr2)
        } catch (e: IllegalArgumentException) {
            println("Error get balance with wrong state root hash")
        }
        //negative path2 - send wrong purse uref
        getBalanceParams.stateRootHash = "8b463b56f2d124f43e7c157e602e31d5d2d5009659de7f1e79afbd238cbaa189"
        getBalanceParams.purseUref = "AAA"
        val parameterStr3: String = getBalanceParams.generateParameter()
        try {
            getBalanceRPC.getBalance(parameterStr3)
        } catch (e: IllegalArgumentException) {
            println("Error get balance with wrong purse uref")
        }
    }
}