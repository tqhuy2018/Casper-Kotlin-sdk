package com.casper.sdk.getdeploy

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLType
import com.casper.sdk.clvalue.CLValue
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem_ModuleBytes
import com.casper.sdk.getdeploy.ExecutableDeployItem.NamedArg
import org.junit.jupiter.api.Test
import kotlin.test.assertSame

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
        assert(getDeployResult.deploy.payment.itsType == ExecutableDeployItem.MODULE_BYTES)
        assert(getDeployResult.deploy.session.itsType == ExecutableDeployItem.MODULE_BYTES)
        var payment: ExecutableDeployItem_ModuleBytes = deploy.payment.itsValue[0] as ExecutableDeployItem_ModuleBytes
        assert(payment.args.listNamedArg.count() == 1)
        var session:ExecutableDeployItem_ModuleBytes = deploy.session.itsValue[0] as ExecutableDeployItem_ModuleBytes
        assert(session.args.listNamedArg.count() == 17)
        //assert for payment first arg
        var paymentNA:NamedArg = payment.args.listNamedArg[0] as NamedArg
        assert(paymentNA.itsName == "amount")
        assert(paymentNA.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_U512)
        assert(paymentNA.clValue.itsBytes == "0500d0ed902e")
        assert(paymentNA.clValue.itsParse.itsValueInStr == "200000000000")
        //asssert for session first arg
        var sessionNA:NamedArg = session.args.listNamedArg[0] as NamedArg
        assert(sessionNA.itsName == "token_contract_hash")
        assert(sessionNA.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_KEY)
        assert(sessionNA.clValue.itsBytes == "01f79455dd5fed17672e18f7c38f654ce2e2dc659f20d9ddab22a1097ebe3ba281")
        assert(sessionNA.clValue.itsParse.itsValueInStr == "hash-f79455dd5fed17672e18f7c38f654ce2e2dc659f20d9ddab22a1097ebe3ba281")
        //assert for session second arg of type List(String)
        var sessionNA2:NamedArg = session.args.listNamedArg[1] as NamedArg
        assert(sessionNA2.itsName == "token_ids")
        assert(sessionNA2.clValue.itsBytes == "0100000018000000363165666635353636663535383630633936643531643965")
        //assertion for CLType of List(String)
        assert(sessionNA2.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_LIST)
        assert(sessionNA2.clValue.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_STRING)
        //assertion for CLParse with only 1 item and the very first item value is correct
        assert(sessionNA2.clValue.itsParse.itsValue[0].itsValueInStr == "61eff5566f55860c96d51d9e")
    }
}