package com.casper.sdk.getdeploy

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLType
import com.casper.sdk.clvalue.CLValue
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem_ModuleBytes
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem_StoredContractByHash
import com.casper.sdk.getdeploy.ExecutableDeployItem.NamedArg
import com.casper.sdk.getdeploy.ExecutionResult.CasperTransform
import com.casper.sdk.getdeploy.ExecutionResult.ExecutionEffect
import com.casper.sdk.getdeploy.ExecutionResult.Transform.Bid
import com.casper.sdk.getdeploy.ExecutionResult.Transform.Delegator
import com.casper.sdk.getdeploy.ExecutionResult.Transform.Transfer
import com.casper.sdk.getdeploy.ExecutionResult.TransformEntry
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
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
        //TEST 3 with ExecutableDeployItem of type StoredContractByName
        //TEST 4 with ExecutableDeployItem of type  StoredVersionedContractByHash
        //TEST 5 with ExecutableDeployItem of type  Transfer
        //Test 6 Transform of type Identity, WriteCLValue,WriteDeployInfo, Write Transfer, Write Bid, AddUInt512
        //https://cspr.live/deploy/32e3d01bc6bb0ef9bec5efa9fbc4c7a15595242edf624de6589cfb6dc52df3bf
        getDeployParams.deploy_hash = "32e3d01bc6bb0ef9bec5efa9fbc4c7a15595242edf624de6589cfb6dc52df3bf"
        var postParameter3 = getDeployParams.generatePostParameterStr()
        getDeployRPC.postURL = ConstValues.MAINNET_URL
        var getDeployResult3 = getDeployRPC.getDeployFromJsonStr(postParameter3)
        assert(getDeployResult3.deploy.hash == "32e3d01bc6bb0ef9bec5efa9fbc4c7a15595242edf624de6589cfb6dc52df3bf")
        assert(getDeployResult3.deploy.header.bodyHash == "0b1c01434b33b7f7ca3a173849e2292333391338ed2af8c1eafd26d6db18ded5")
        assert(getDeployResult3.deploy.header.timeStamp == "2022-01-16T03:42:12.899Z")
        var payment3:ExecutableDeployItem_ModuleBytes = getDeployResult3.deploy.payment.itsValue[0] as ExecutableDeployItem_ModuleBytes
        assert(payment3.args.listNamedArg.count() == 1)
        assert(payment3.args.listNamedArg[0].itsName == "amount")
        assert(payment3.args.listNamedArg[0].clValue.itsBytes == "0400f90295")
        assert(payment3.args.listNamedArg[0].clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_U512)
        assert(payment3.args.listNamedArg[0].clValue.itsParse.itsValueInStr == "2500000000")
        var session3:ExecutableDeployItem_StoredContractByHash = getDeployResult3.deploy.session.itsValue[0] as ExecutableDeployItem_StoredContractByHash
        assert(session3.args.listNamedArg.count() == 3)
        assert(session3.args.listNamedArg[0].itsName == "delegator")
        assert(session3.args.listNamedArg[0].clValue.itsParse.itsValueInStr == "0190579027a528b53a4671984d6667add9c3c93ab3235779d3df5acd3346be62b0")
        assert(session3.args.listNamedArg[0].clValue.itsBytes == "0190579027a528b53a4671984d6667add9c3c93ab3235779d3df5acd3346be62b0")
        assert(session3.args.listNamedArg[0].clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_PUBLIC_KEY)

        //Approvals assertion
        assert(getDeployResult3.deploy.approvals.count() == 1)
        assert(getDeployResult3.deploy.approvals[0].signature == "018f0b894b6fedb563462ce8161dee50d26c28dcb644ceb14738edfa0ebc4c2e593d7eef835db5c4b462f938314188666f7868555c04d815cda4a0af64b307e90e")
        assert(getDeployResult3.deploy.approvals[0].signer == "0190579027a528b53a4671984d6667add9c3c93ab3235779d3df5acd3346be62b0")
        //execution_results assertion
        assert(getDeployResult3.executionResults.count() == 1)
        assert(getDeployResult3.executionResults[0].blockHash == "00b1b35ee5d8bddfc70de6d8d769c49eec81511fee98102e1a6fea3b50524e9e")
        assert(getDeployResult3.executionResults[0].result.itsType == ConstValues.EXECUTION_RESULT_SUCCESS)
        assert(getDeployResult3.executionResults[0].result.cost.itsValue == "2500000000")
        assert(getDeployResult3.executionResults[0].result.transfers.count() == 1)
        assert(getDeployResult3.executionResults[0].result.transfers[0] == "transfer-bc65486f5a9fe687651194d7a8e215ce5a1374975c88ba288b47f9ed824467c9")
        var effect3:ExecutionEffect = getDeployResult3.executionResults[0].result.effect
        assert(effect3.operations.count() == 0)
        assert(effect3.transforms.count() == 25)
        //assertion for type of WriteBid
        var transformEntry3:TransformEntry = effect3.transforms[15]
        assert(transformEntry3.key == "bid-6f827aefd27ba48dbb5e6e00578043350f3ee1dc04c926d2e89eec72c5cf653c")
        assert(transformEntry3.transform.itsType == ConstValues.TRANSFORM_WRITE_BID)
        var bid:Bid = transformEntry3.transform.itsValue[0] as Bid
        assert(bid.inactive == false)
        assert(bid.delegators.count() == 971)
        //assert for first delegator
        var delegator3:Delegator = Delegator()
        delegator3 = bid.delegators[0] as Delegator
        assert(delegator3.itsPublicKey == "010047f47d3af036ab0ad989d1563647d2b26f365360f6ba051c36b4aa67d12dea")
        assert(delegator3.bondingPurse == "uref-8f4d8a7e0763f4209d972945b92ce7e53847646278863455dbbfa77c30b69be8-007")
        assert(delegator3.stakedAmount.itsValue == "31680055784636")
        assert(delegator3.delegatorPublicKey == "010047f47d3af036ab0ad989d1563647d2b26f365360f6ba051c36b4aa67d12dea")
        assert(delegator3.validatorPublicKey == "01c60fe433d3a22ec5e30a8341f4bda978fa81c2b94e5a95f745723f9a019a3c31")
        assert(delegator3.isVestingScheduleExisted == false)
        //assertion for Transform of type WriteTransfer
        val transformWriteTransfer:TransformEntry = effect3.transforms[14]
        assert(transformWriteTransfer.key == "transfer-bc65486f5a9fe687651194d7a8e215ce5a1374975c88ba288b47f9ed824467c9")
        assert(transformWriteTransfer.transform.itsType == ConstValues.TRANSFORM_WRITE_TRANSFER)
        val transfer:Transfer = transformWriteTransfer.transform.itsValue[0] as Transfer
        assert(transfer.isToExisted == true)
        println("transfertois:${transfer.to}")
        assert(transfer.to == "account-hash-6174cf2e6f8fed1715c9a3bace9c50bfe572eecb763b0ed3f644532616452008")
        assert(transfer.isIDExisted == false)
        assert(transfer.gas.itsValue == "0")
        assert(transfer.from == "account-hash-60e4891563366b1262d56fb858a20da673151e11daef4f488e526f79cd2f776a")
        assert(transfer.amount.itsValue == "19950000000000")
        assert(transfer.source == "uref-95be0b30da803d031deb2be4a4dbf2d06c6df95d6445bec5e5476309bef057f4-007")
        assert(transfer.target == "uref-29e6c3444d015a461046438b878978e174d9ccbbbf58d84b00a4786ee7cff6b5-007")
        assert(transfer.deployHash == "32e3d01bc6bb0ef9bec5efa9fbc4c7a15595242edf624de6589cfb6dc52df3bf")
        //assertion for Transform of type AddUInt512
        assert(effect3.transforms[24].key == "balance-c0bef06bbe210620ad842c6769d551f1bce6605b3706596098850fd7fcccdbc9")
        assert(effect3.transforms[24].transform.itsType == ConstValues.TRANSFORM_ADD_UINT512)
        assert(effect3.transforms[24].transform.itsValue[0] == "2500000000")
    //Test 7 Transform of type Withdraw
        // https://cspr.live/deploy/acb4d78cbb900fe91a896ea8a427374c5d600cd9206efae2051863316265f1b1
        //Test 8 Transform of type AddKeys
        //Negative Path:
        //Get Deploy with wrong deploy hash
        //Get Deploy with no parameter - no deploy hash
    }
}