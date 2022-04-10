package com.casper.sdk.getdeploy

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLType
import com.casper.sdk.clvalue.CLValue
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem_ModuleBytes
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem_StoredContractByHash
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
        //assertion for session 4th element with CLValue of CLType Option but NULL parse
        var sessionNA3:NamedArg = session.args.listNamedArg[4] as NamedArg
        assert(sessionNA3.itsName == "starting_price")
        assert(sessionNA3.clValue.itsBytes == "00")
        assert(sessionNA3.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_OPTION)
        assert(sessionNA3.clValue.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_BOOL)
        assert(sessionNA3.clValue.itsParse.itsValueInStr == ConstValues.VALUE_NULL)

        //TEST 2 with ExecutableDeployItem of type StoredContractByHash
        //check for CLValue of some other types: ByteArray, Bool
        getDeployParams.deploy_hash = "1d113022631c587444166e4d1efbc3d475e49b28b90f1414d9cadee6dcddf65f"
        var postParameter2 = getDeployParams.generatePostParameterStr()
        var getDeployResult2 = getDeployRPC.getDeployFromJsonStr(postParameter2)
        assert(getDeployResult2.deploy.hash == "1d113022631c587444166e4d1efbc3d475e49b28b90f1414d9cadee6dcddf65f")
        assert(getDeployResult2.deploy.session.itsType == ExecutableDeployItem.STORED_CONTRACT_BY_HASH)
        var session2Value:ExecutableDeployItem_StoredContractByHash = ExecutableDeployItem_StoredContractByHash()
        session2Value = getDeployResult2.deploy.session.itsValue[0] as ExecutableDeployItem_StoredContractByHash
        assert(session2Value.itsHash == "29710161f8912257718fc2f9a8cf80a55b82f706d22609cb8190c83de01bd690")
        assert(session2Value.entryPoint == "issueDemoVC")
        assert(session2Value.args.listNamedArg.count() == 5)
        var na1:NamedArg = session2Value.args.listNamedArg[0] as NamedArg
        assert(na1.itsName == "merkleRoot")
        assert(na1.clValue.itsBytes == "bde21f5eb8bd073acdfa59ac7d613f865cd101ccaf3d787cb5dc909f0111a9c2")
        assert(na1.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_BYTEARRAY)
        assert(na1.clValue.itsParse.itsValueInStr == "bde21f5eb8bd073acdfa59ac7d613f865cd101ccaf3d787cb5dc909f0111a9c2")
        //430df377ae04726de907f115bb06c52e40f6cd716b4b475a10e4cd9226d1317e List(String)
    }
}