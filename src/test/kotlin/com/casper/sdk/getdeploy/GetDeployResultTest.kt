package com.casper.sdk.getdeploy

import org.junit.jupiter.api.Test

internal class GetDeployResultTest {

    @Test
    fun getDeployFromJsonStr() {
        var getDeployResult: GetDeployRPC = GetDeployRPC()
        var getDeployParams:GetDeployParams = GetDeployParams()
        getDeployParams.deploy_hash = "ca0b2dc4dd552a638e90c5102c8808d04936c2428fa96bf6cfb26d9148269fdb"
        var str = getDeployParams.toJsonStr()
        getDeployResult.getDeployFromJsonStr(str)
        //getDeployResult.getDeployFromJsonStr()
        //getDeployResult = GetDeployRPC.
    }
}