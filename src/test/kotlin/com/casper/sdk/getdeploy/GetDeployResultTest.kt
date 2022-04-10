package com.casper.sdk.getdeploy

import com.casper.sdk.ConstValues
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem_ModuleBytes
import org.junit.jupiter.api.Test

internal class GetDeployResultTest {

    @Test
    fun getDeployFromJsonStr() {
        var getDeployRPC: GetDeployRPC = GetDeployRPC()
        var getDeployParams:GetDeployParams = GetDeployParams()
        getDeployRPC.postURL = ConstValues.TESTNET_URL
        //getDeployRPC.postURL = ConstValues.MAINNET_URL
       // getDeployParams.deploy_hash = "ca0b2dc4dd552a638e90c5102c8808d04936c2428fa96bf6cfb26d9148269fdb"
        getDeployParams.deploy_hash = "9ff98d8027795a002e41a709d5b5846e49c2e9f9c8bfbe74e4c857adc26d5571"
        var postParameter = getDeployParams.generatePostParameterStr()
        var getDeployResult = getDeployRPC.getDeployFromJsonStr(postParameter)
        var deploy:Deploy = getDeployResult.deploy
        assert(deploy.hash == "9ff98d8027795a002e41a709d5b5846e49c2e9f9c8bfbe74e4c857adc26d5571")
        println("Deploy payment type:${getDeployResult.deploy.payment.itsType}")
        assert(getDeployResult.deploy.payment.itsType == ExecutableDeployItem.MODULE_BYTES)
        assert(getDeployResult.deploy.session.itsType == ExecutableDeployItem.MODULE_BYTES)
        var payment: ExecutableDeployItem_ModuleBytes = deploy.payment.itsValue[0] as ExecutableDeployItem_ModuleBytes
        assert(payment.args.listNamedArg.count() == 1)
        var session:ExecutableDeployItem_ModuleBytes = deploy.session.itsValue[0] as ExecutableDeployItem_ModuleBytes
        assert(session.args.listNamedArg.count() == 17)
    }
}