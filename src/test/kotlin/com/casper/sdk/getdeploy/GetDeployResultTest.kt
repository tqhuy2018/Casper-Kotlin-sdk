package com.casper.sdk.getdeploy

import org.junit.jupiter.api.Test

internal class GetDeployResultTest {

    @Test
    fun getDeployFromJsonStr() {
        var getDeployResult: GetDeployRPC = GetDeployRPC()
        var getDeployParams:GetDeployParams = GetDeployParams()
        getDeployParams.deploy_hash = "f26a173a4c3d72905d9c83008d980df574bf6f6ec2d69a1a591f8b2f21203b3b"
        var str = getDeployParams.toJsonStr()
        getDeployResult.getDeployFromJsonStr(str)
        //getDeployResult.getDeployFromJsonStr()
        //getDeployResult = GetDeployRPC.
    }
}