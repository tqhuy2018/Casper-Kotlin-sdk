package com.casper.sdk.getdeploy

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLValue
import com.casper.sdk.getdeploy.ExecutableDeployItem.*
import com.casper.sdk.getdeploy.ExecutionResult.ExecutionEffect
import com.casper.sdk.getdeploy.ExecutionResult.Transform.*
import com.casper.sdk.getdeploy.ExecutionResult.TransformEntry
import org.junit.jupiter.api.Test

internal class GetDeployRPCTest {
    @Test
    fun getDeployTest() {
        val getDeployRPC:  GetDeployRPC = GetDeployRPC()
        val getDeployParams = GetDeployParams()
        getDeployRPC.methodURL = ConstValues.TESTNET_URL
        getDeployParams.deploy_hash = "9ff98d8027795a002e41a709d5b5846e49c2e9f9c8bfbe74e4c857adc26d5571"
        val postParameter = getDeployParams.generatePostParameterStr()
        try {
            val getDeployResult = getDeployRPC.getDeployFromJsonStr(postParameter)
            val deploy:  Deploy = getDeployResult.deploy
            assert(deploy.hash == "9ff98d8027795a002e41a709d5b5846e49c2e9f9c8bfbe74e4c857adc26d5571")
            assert(getDeployResult.deploy.payment.itsType == ExecutableDeployItem.MODULE_BYTES)
            assert(getDeployResult.deploy.session.itsType == ExecutableDeployItem.MODULE_BYTES)
            val payment:  ExecutableDeployItem_ModuleBytes =
                deploy.payment.itsValue[0] as ExecutableDeployItem_ModuleBytes
            assert(payment.args.listNamedArg.count() == 1)
            val session:  ExecutableDeployItem_ModuleBytes =
                deploy.session.itsValue[0] as ExecutableDeployItem_ModuleBytes
            assert(session.args.listNamedArg.count() == 17)
            //assert for payment first arg - CLType_U512
            val paymentNA:  NamedArg = payment.args.listNamedArg[0]
            assert(paymentNA.itsName == "amount")
            assert(paymentNA.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_U512)
            assert(paymentNA.clValue.itsBytes == "0500d0ed902e")
            assert(paymentNA.clValue.itsParse.itsValueInStr == "200000000000")
            //asssert for session first arg - CLType_Key - hash
            val sessionNA:  NamedArg = session.args.listNamedArg[0]
            assert(sessionNA.itsName == "token_contract_hash")
            assert(sessionNA.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_KEY)
            assert(sessionNA.clValue.itsBytes == "01f79455dd5fed17672e18f7c38f654ce2e2dc659f20d9ddab22a1097ebe3ba281")
            assert(sessionNA.clValue.itsParse.itsValueInStr == "hash-f79455dd5fed17672e18f7c38f654ce2e2dc659f20d9ddab22a1097ebe3ba281")
            //assert for session second arg of CLTYPE List(String)
            val sessionNA2:  NamedArg = session.args.listNamedArg[1]
            assert(sessionNA2.itsName == "token_ids")
            assert(sessionNA2.clValue.itsBytes == "0100000018000000363165666635353636663535383630633936643531643965")
            //assertion for CLType of List(String)
            assert(sessionNA2.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_LIST)
            assert(sessionNA2.clValue.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_STRING)
            //assertion for CLParse with only 1 item and the very first item value is correct
            assert(sessionNA2.clValue.itsParse.itsArrayValue[0].itsValueInStr == "61eff5566f55860c96d51d9e")
            //assertion for session 3th element with CLValue of CLType Key - account
            val sessionNAKey:  NamedArg = session.args.listNamedArg[3]
            assert(sessionNAKey.itsName == "kyc_package_hash")
            assert(sessionNAKey.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_KEY)
            assert(sessionNAKey.clValue.itsBytes == "01edc852b98c633f9dbed27f7f872a9dc5d3671d820269f796f825c7c4d2311b91")
            assert(sessionNAKey.clValue.itsParse.itsValueInStr == "hash-edc852b98c633f9dbed27f7f872a9dc5d3671d820269f796f825c7c4d2311b91")
            //assertion for session 4th element with CLValue of CLType Option but NULL parse
            val sessionNA3:  NamedArg = session.args.listNamedArg[4]
            assert(sessionNA3.itsName == "starting_price")
            assert(sessionNA3.clValue.itsBytes == "00")
            assert(sessionNA3.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_OPTION)
            assert(sessionNA3.clValue.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_BOOL)
            assert(sessionNA3.clValue.itsParse.itsValueInStr == ConstValues.VALUE_NULL)
            //assertion for session 6th element with CLValue of CLType String
            val sessionNAString:  NamedArg = session.args.listNamedArg[6]
            assert(sessionNAString.itsName == "token_id")
            assert(sessionNAString.clValue.itsBytes == "18000000363165666635353636663535383630633936643531643965")
            assert(sessionNAString.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_STRING)
            assert(sessionNAString.clValue.itsParse.itsValueInStr == "61eff5566f55860c96d51d9e")
            //assertion for session 7th element with CLValue of CLType U64
            val sessionNAU64:  NamedArg = session.args.listNamedArg[7]
            assert(sessionNAU64.itsName == "start_time")
            assert(sessionNAU64.clValue.itsBytes == "0832a9927e010000")
            assert(sessionNAU64.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_U64)
            assert(sessionNAU64.clValue.itsParse.itsValueInStr == "1643138069000")
            //assertion for session 13th item with CLValue of CLType Option(U64)
            val sessionNAOption:  NamedArg = session.args.listNamedArg[13]
            assert(sessionNAOption.itsName == "auction_timer_extension")
            assert(sessionNAOption.clValue.itsBytes == "00")
            assert(sessionNAOption.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_OPTION)
            assert(sessionNAOption.clValue.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_U64)
            assert(sessionNAOption.clValue.itsParse.itsValueInStr == ConstValues.VALUE_NULL)
            //assertion for session 13th item with CLValue of CLType Option(U64)
            val sessionNAOption2:  NamedArg = session.args.listNamedArg[14]
            assert(sessionNAOption2.itsName == "minimum_bid_step")
            assert(sessionNAOption2.clValue.itsBytes == "010105")
            assert(sessionNAOption2.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_OPTION)
            assert(sessionNAOption2.clValue.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_U512)
            println("Value of OptionU512: ${sessionNAOption2.clValue.itsParse.innerParsed1.itsValueInStr}")
            assert(sessionNAOption2.clValue.itsParse.innerParsed1.itsValueInStr == "5")
            //assertion for session 16th element with CLValue of CLType U32
            val sessionNAU32:  NamedArg = session.args.listNamedArg[16]
            assert(sessionNAU32.itsName == "marketplace_commission")
            assert(sessionNAU32.clValue.itsBytes == "64000000")
            assert(sessionNAU32.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_U32)
            assert(sessionNAU32.clValue.itsParse.itsValueInStr == "100")
            //assertion for ExecutionResult of Failure
            assert(getDeployResult.executionResults.count() == 1)
            assert(getDeployResult.executionResults[0].blockHash == "aea4ee6a46ab60337557fa5526667796888b84ee9c70d5b74cf72e2a8c7f9816")
            assert(getDeployResult.executionResults[0].result.itsType == ConstValues.EXECUTION_RESULT_FAILURE)
            assert(getDeployResult.executionResults[0].result.cost.itsValue == "92233020")
            assert(getDeployResult.executionResults[0].result.errorMessage == "ApiError: : InvalidArgument [3]")
            assert(getDeployResult.executionResults[0].result.transfers.count() == 0)
            assert(getDeployResult.executionResults[0].result.effect.operations.count() == 0)
            assert(getDeployResult.executionResults[0].result.effect.transforms.count() == 16)
        } catch (e:  IllegalArgumentException) {
        }
        try {
            //TEST 2 with ExecutableDeployItem of type StoredContractByHash
            getDeployParams.deploy_hash = "1d113022631c587444166e4d1efbc3d475e49b28b90f1414d9cadee6dcddf65f"
            val postParameter2 = getDeployParams.generatePostParameterStr()
            val getDeployResult2 = getDeployRPC.getDeployFromJsonStr(postParameter2)
            assert(getDeployResult2.deploy.hash == "1d113022631c587444166e4d1efbc3d475e49b28b90f1414d9cadee6dcddf65f")
            assert(getDeployResult2.deploy.session.itsType == ExecutableDeployItem.STORED_CONTRACT_BY_HASH)
            val session2Value = getDeployResult2.deploy.session.itsValue[0] as ExecutableDeployItem_StoredContractByHash
            assert(session2Value.itsHash == "29710161f8912257718fc2f9a8cf80a55b82f706d22609cb8190c83de01bd690")
            assert(session2Value.entryPoint == "issueDemoVC")
            assert(session2Value.args.listNamedArg.count() == 5)
            val na1:  NamedArg = session2Value.args.listNamedArg[0]
            assert(na1.itsName == "merkleRoot")
            assert(na1.clValue.itsBytes == "bde21f5eb8bd073acdfa59ac7d613f865cd101ccaf3d787cb5dc909f0111a9c2")
            assert(na1.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_BYTEARRAY)
            assert(na1.clValue.itsParse.itsValueInStr == "bde21f5eb8bd073acdfa59ac7d613f865cd101ccaf3d787cb5dc909f0111a9c2")
            //assertion for CLValue of CLType Bool
            val naBool:  NamedArg = session2Value.args.listNamedArg[4]
            assert(naBool.itsName == "revocationFlag")
            assert(naBool.clValue.itsBytes == "01")
            assert(naBool.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_BOOL)
            assert(naBool.clValue.itsParse.itsValueInStr == "true")
            //execution_results assertion
            assert(getDeployResult2.executionResults.count() == 1)
            assert(getDeployResult2.executionResults[0].blockHash == "966b9cbc817b01974847b0ae536902c6dc90af9c4ff47ec4bc43ee9b095a4359")
            assert(getDeployResult2.executionResults[0].result.itsType == ConstValues.EXECUTION_RESULT_SUCCESS)
            assert(getDeployResult2.executionResults[0].result.cost.itsValue == "721839840")
            assert(getDeployResult2.executionResults[0].result.transfers.count() == 0)
            val effect2:  ExecutionEffect = getDeployResult2.executionResults[0].result.effect
            assert(effect2.operations.count() == 0)
            assert(effect2.transforms.count() == 32)
            //assertion for type of WriteCLValue - ByteArray
            val transformEntry2:  TransformEntry = effect2.transforms[9]
            assert(transformEntry2.key == "uref-80b4d672233751de4ffb8e23f70fd23c30a06ca08b67e79d3bc0bd3f3af3bd6b-000")
            assert(transformEntry2.transform.itsType == ConstValues.TRANSFORM_WRITE_CLVALUE)
            val clValue:  CLValue = transformEntry2.transform.itsValue[0] as CLValue
            assert(clValue.itsBytes == "bde21f5eb8bd073acdfa59ac7d613f865cd101ccaf3d787cb5dc909f0111a9c2")
            assert(clValue.itsParse.itsValueInStr == "bde21f5eb8bd073acdfa59ac7d613f865cd101ccaf3d787cb5dc909f0111a9c2")
            assert(clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_BYTEARRAY)
        } catch (e: IllegalArgumentException) {
        }
        try {
            //Test 6 Transform of type Identity,  WriteCLValue, WriteDeployInfo,  Write Transfer,  Write Bid,  AddUInt512
            //https: //cspr.live/deploy/32e3d01bc6bb0ef9bec5efa9fbc4c7a15595242edf624de6589cfb6dc52df3bf
            getDeployParams.deploy_hash = "32e3d01bc6bb0ef9bec5efa9fbc4c7a15595242edf624de6589cfb6dc52df3bf"
            val postParameter3 = getDeployParams.generatePostParameterStr()
            getDeployRPC.methodURL = ConstValues.MAINNET_URL
            val getDeployResult3 = getDeployRPC.getDeployFromJsonStr(postParameter3)
            assert(getDeployResult3.deploy.hash == "32e3d01bc6bb0ef9bec5efa9fbc4c7a15595242edf624de6589cfb6dc52df3bf")
            assert(getDeployResult3.deploy.header.bodyHash == "0b1c01434b33b7f7ca3a173849e2292333391338ed2af8c1eafd26d6db18ded5")
            assert(getDeployResult3.deploy.header.timeStamp == "2022-01-16T03: 42: 12.899Z")
            val payment3:  ExecutableDeployItem_ModuleBytes =
                getDeployResult3.deploy.payment.itsValue[0] as ExecutableDeployItem_ModuleBytes
            assert(payment3.args.listNamedArg.count() == 1)
            assert(payment3.args.listNamedArg[0].itsName == "amount")
            assert(payment3.args.listNamedArg[0].clValue.itsBytes == "0400f90295")
            assert(payment3.args.listNamedArg[0].clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_U512)
            assert(payment3.args.listNamedArg[0].clValue.itsParse.itsValueInStr == "2500000000")
            val session3:  ExecutableDeployItem_StoredContractByHash =
                getDeployResult3.deploy.session.itsValue[0] as ExecutableDeployItem_StoredContractByHash
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
            val effect3:  ExecutionEffect = getDeployResult3.executionResults[0].result.effect
            assert(effect3.operations.count() == 0)
            assert(effect3.transforms.count() == 25)
            //assertion for type of WriteBid
            val transformEntry3:  TransformEntry = effect3.transforms[15]
            assert(transformEntry3.key == "bid-6f827aefd27ba48dbb5e6e00578043350f3ee1dc04c926d2e89eec72c5cf653c")
            assert(transformEntry3.transform.itsType == ConstValues.TRANSFORM_WRITE_BID)
            val bid:  Bid = transformEntry3.transform.itsValue[0] as Bid
            assert(bid.inactive == false)
            assert(bid.delegators.count() == 971)
            //assert for first delegator
            val delegator3 = bid.delegators[0]
            assert(delegator3.itsPublicKey == "010047f47d3af036ab0ad989d1563647d2b26f365360f6ba051c36b4aa67d12dea")
            assert(delegator3.bondingPurse == "uref-8f4d8a7e0763f4209d972945b92ce7e53847646278863455dbbfa77c30b69be8-007")
            assert(delegator3.stakedAmount.itsValue == "31680055784636")
            assert(delegator3.delegatorPublicKey == "010047f47d3af036ab0ad989d1563647d2b26f365360f6ba051c36b4aa67d12dea")
            assert(delegator3.validatorPublicKey == "01c60fe433d3a22ec5e30a8341f4bda978fa81c2b94e5a95f745723f9a019a3c31")
            assert(delegator3.isVestingScheduleExisted == false)
            //assertion for Transform of type WriteTransfer
            val transformWriteTransfer:  TransformEntry = effect3.transforms[14]
            assert(transformWriteTransfer.key == "transfer-bc65486f5a9fe687651194d7a8e215ce5a1374975c88ba288b47f9ed824467c9")
            assert(transformWriteTransfer.transform.itsType == ConstValues.TRANSFORM_WRITE_TRANSFER)
            val transfer:  Transfer = transformWriteTransfer.transform.itsValue[0] as Transfer
            assert(transfer.isToExisted == true)
            println("transfertois: ${transfer.to}")
            assert(transfer.to == "account-hash-6174cf2e6f8fed1715c9a3bace9c50bfe572eecb763b0ed3f644532616452008")
            assert(transfer.isIDExisted == false)
            assert(transfer.gas.itsValue == "0")
            assert(transfer.from == "account-hash-60e4891563366b1262d56fb858a20da673151e11daef4f488e526f79cd2f776a")
            assert(transfer.amount.itsValue == "19950000000000")
            assert(transfer.source == "uref-95be0b30da803d031deb2be4a4dbf2d06c6df95d6445bec5e5476309bef057f4-007")
            assert(transfer.target == "uref-29e6c3444d015a461046438b878978e174d9ccbbbf58d84b00a4786ee7cff6b5-007")
            assert(transfer.deployHash == "32e3d01bc6bb0ef9bec5efa9fbc4c7a15595242edf624de6589cfb6dc52df3bf")
            //assertion for Transform of type Identity
            assert(effect3.transforms[0].key == "hash-d2469afeb99130f0be7c9ce230a84149e6d756e306ef8cf5b8a49d5182e41676")
            assert(effect3.transforms[0].transform.itsType == ConstValues.TRANSFORM_IDENTITY)
            //assertion for Transform of type AddUInt512
            assert(effect3.transforms[24].key == "balance-c0bef06bbe210620ad842c6769d551f1bce6605b3706596098850fd7fcccdbc9")
            assert(effect3.transforms[24].transform.itsType == ConstValues.TRANSFORM_ADD_UINT512)
            assert(effect3.transforms[24].transform.itsValue[0] == "2500000000")
            //assertion for Transform of type WriteCLValue
            val transformWriteCLValue3:  TransformEntry = effect3.transforms[6]
            assert(transformWriteCLValue3.key == "balance-95be0b30da803d031deb2be4a4dbf2d06c6df95d6445bec5e5476309bef057f4")
            assert(transformWriteCLValue3.transform.itsType == ConstValues.TRANSFORM_WRITE_CLVALUE)
            val clValue3:  CLValue = transformWriteCLValue3.transform.itsValue[0] as CLValue
            assert(clValue3.itsBytes == "06005fef963012")
            assert(clValue3.itsCLType.itsTypeStr == ConstValues.CLTYPE_U512)
            assert(clValue3.itsParse.itsValueInStr == "19999900000000")
        } catch (e: IllegalArgumentException) {
        }
        try {
            //Test 7 Transform of type WriteWithdraw and WriteBid - from MAINNET
            // https://cspr.live/deploy/acb4d78cbb900fe91a896ea8a427374c5d600cd9206efae2051863316265f1b1
            getDeployParams.deploy_hash = "acb4d78cbb900fe91a896ea8a427374c5d600cd9206efae2051863316265f1b1"
            val postParameter4 = getDeployParams.generatePostParameterStr()
            getDeployRPC.methodURL = ConstValues.MAINNET_URL
            val getDeployResult4 = getDeployRPC.getDeployFromJsonStr(postParameter4)
            assert(getDeployResult4.deploy.hash == "acb4d78cbb900fe91a896ea8a427374c5d600cd9206efae2051863316265f1b1")
            val effect4:  ExecutionEffect = getDeployResult4.executionResults[0].result.effect
            val transformWriteWithdraw:  TransformEntry = effect4.transforms[12]
            assert(transformWriteWithdraw.key == "withdraw-24b6d5aabb8f0ac17d272763a405e9ceca9166b75b745cf200695e172857c2dd")
            assert(transformWriteWithdraw.transform.itsType == ConstValues.TRANSFORM_WRITE_WITHDRAW)
            val withdraw:  Withdraw = transformWriteWithdraw.transform.itsValue[0] as Withdraw
            assert(withdraw.listUnbondingPurse.count() == 7)
            //assertion for first UnbondingPurse
            val oneUnbondingPurse:  UnbondingPurse = withdraw.listUnbondingPurse[0]
            assert(oneUnbondingPurse.amount.itsValue == "100131800754797")
            assert(oneUnbondingPurse.bondingPurse == "uref-fd78a335a6b3e86b0a0161ac7640a69ec858c32f0d549dc5c3ba820a5bd13616-007")
            assert(oneUnbondingPurse.eraOfCreation.toString() == "3475")
            assert(oneUnbondingPurse.unbonderPublicKey == "02033444b5a5584d8bb3427fc8fdbd306e8a9a7cf66570acb461a80a186fe1566fdd")
            assert(oneUnbondingPurse.validatorPublicKey == "012bac1d0ff9240ff0b7b06d555815640497861619ca12583ddef434885416e69b")
            //assertion for Transform of type WriteBid
            val transformWriteBid:  TransformEntry = effect4.transforms[14]
            assert(transformWriteBid.key == "bid-24b6d5aabb8f0ac17d272763a405e9ceca9166b75b745cf200695e172857c2dd")
            assert(transformWriteBid.transform.itsType == ConstValues.TRANSFORM_WRITE_BID)
            val bid4:  Bid = transformWriteBid.transform.itsValue[0] as Bid
            assert(bid4.inactive == false)
            assert(bid4.bondingPurse == "uref-9ef6b11bd095c1733956e3b7e5bb47630f5fa59ad9a89c87fa671a1177e0c025-007")
            assert(bid4.stakedAmount.itsValue == "369103604862659")
            assert(bid4.delegationRate.toString() == "10")
            assert(bid4.validatorPublicKey == "012bac1d0ff9240ff0b7b06d555815640497861619ca12583ddef434885416e69b")
            assert(bid4.delegators.count() == 3181)
            //assertion for Delegator with VestingSchedule - item 43,  95,  310,  429, 547, 648 ...,  get 95 to check
            val delegator4:  Delegator = bid4.delegators[95]
            assert(delegator4.itsPublicKey == "010a6eb8216afcaa59f9202d8bb12e30caf0c46f6c0da08ced34471d80cdfde650")
            assert(delegator4.bondingPurse == "uref-bac31707e7a39743d653c93a1b664b0be3546a336387cee282183be50239ec42-007")
            assert(delegator4.stakedAmount.itsValue == "9000875653986624")
            assert(delegator4.delegatorPublicKey == "010a6eb8216afcaa59f9202d8bb12e30caf0c46f6c0da08ced34471d80cdfde650")
            assert(delegator4.validatorPublicKey == "012bac1d0ff9240ff0b7b06d555815640497861619ca12583ddef434885416e69b")
            assert(delegator4.isVestingScheduleExisted == true)
            assert(delegator4.vestingSchedule.initialReleaseTimestampMillis.toString() == "1624978800000")
            assert(delegator4.vestingSchedule.lockedAmounts.count() == 14)
            assert(delegator4.vestingSchedule.lockedAmounts[0].itsValue == "7895222924551040")
            assert(bid4.isVestingScheduleExisted == true)
            assert(bid4.vestingSchedule.initialReleaseTimestampMillis.equals(1624978800000u))
            assert(bid4.vestingSchedule.lockedAmounts.count() == 14)
            assert(bid4.vestingSchedule.lockedAmounts[0].itsValue == "1359796682549793")
            assert(bid4.vestingSchedule.lockedAmounts[1].itsValue == "1255196937738271")
            //assertion for Transform of type WriteDeployInfo
            val transformWriteDeployInfo:  TransformEntry = effect4.transforms[15]
            assert(transformWriteDeployInfo.key == "deploy-acb4d78cbb900fe91a896ea8a427374c5d600cd9206efae2051863316265f1b1")
            assert(transformWriteDeployInfo.transform.itsType == ConstValues.TRANSFORM_WRITE_DEPLOY_INFO)
            val deployInfo:  DeployInfo = transformWriteDeployInfo.transform.itsValue[0] as DeployInfo
            assert(deployInfo.gas.itsValue == "10000")
            assert(deployInfo.from == "account-hash-82eb5ff298d727606b0de8514154a798a6db75904fdd4ab2dcafebae612198a2")
            assert(deployInfo.source == "uref-6ad873d6b184d25b307fb13069cfd25a14cf7aa4266592221d6c67a295edc59b-007")
            assert(deployInfo.transfers.count() == 0)
        } catch (e: IllegalArgumentException) {

        }
        try {
            //Test Transform of type WriteAccount for ExecutionResult item number 13 and CLValue of type List(ByteArray) for session ModuleBytes item number 3
            //https: //testnet.cspr.live/deploy/ac654d996f17720e780fe4eae9a7d57d57cdadb069998666a369a0833aebb7f8
            getDeployParams.deploy_hash = "ac654d996f17720e780fe4eae9a7d57d57cdadb069998666a369a0833aebb7f8"
            val postParameter5 = getDeployParams.generatePostParameterStr()
            getDeployRPC.methodURL = ConstValues.TESTNET_URL
            val getDeployResult5 = getDeployRPC.getDeployFromJsonStr(postParameter5)
            assert(getDeployResult5.deploy.hash == "ac654d996f17720e780fe4eae9a7d57d57cdadb069998666a369a0833aebb7f8")
            assert(getDeployResult5.deploy.session.itsType == ExecutableDeployItem.MODULE_BYTES)
            val session5Value = getDeployResult5.deploy.session.itsValue[0] as ExecutableDeployItem_ModuleBytes
            assert(session5Value.args.listNamedArg.count() == 9)
            //assertion for CLValue of type U8
            val sessionNAU8:  NamedArg = session5Value.args.listNamedArg[1]
            assert(sessionNAU8.itsName == "deployment_thereshold")
            val clValueSessionU8:  CLValue = sessionNAU8.clValue
            assert(clValueSessionU8.itsBytes == "02")
            assert(clValueSessionU8.itsCLType.itsTypeStr == ConstValues.CLTYPE_U8)
            assert(clValueSessionU8.itsParse.itsValueInStr == "2")
            //assertion for CLValue of type List(ByteArray)
            val sessionNA5:  NamedArg = session5Value.args.listNamedArg[3]
            assert(sessionNA5.itsName == "accounts")
            val clValueSession5:  CLValue = sessionNA5.clValue
            assert(clValueSession5.itsBytes == "04000000cc77e0fef426adc63f5380d13e20ab62832f70afae299bef6fcf3f985eb6e5937aebb6de622b00ced00fc0ed16562b5c0d7ee3a3a894fc42001eb7fb2da4d102713910b4d6f4fed1ab28b06e93c2562f845c570ac6861b93bee6a67f4aeedb035c6c49477ffa1dabc642d4cc894a21b248e721ee66128c59588393acea1ff196")
            assert(clValueSession5.itsCLType.itsTypeStr == ConstValues.CLTYPE_LIST)
            assert(clValueSession5.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_BYTEARRAY)
            assert(clValueSession5.itsParse.itsArrayValue[0].itsValueInStr == "cc77e0fef426adc63f5380d13e20ab62832f70afae299bef6fcf3f985eb6e593")
            assert(clValueSession5.itsParse.itsArrayValue[1].itsValueInStr == "7aebb6de622b00ced00fc0ed16562b5c0d7ee3a3a894fc42001eb7fb2da4d102")
            assert(clValueSession5.itsParse.itsArrayValue[2].itsValueInStr == "713910b4d6f4fed1ab28b06e93c2562f845c570ac6861b93bee6a67f4aeedb03")
            assert(clValueSession5.itsParse.itsArrayValue[3].itsValueInStr == "5c6c49477ffa1dabc642d4cc894a21b248e721ee66128c59588393acea1ff196")
            //asssertion for CLValue of type List(U8)
            val sessionNA51:  NamedArg = session5Value.args.listNamedArg[4]
            assert(sessionNA51.itsName == "weights")
            assert(sessionNA51.clValue.itsBytes == "0400000001010100")
            assert(sessionNA51.clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_LIST)
            assert(sessionNA51.clValue.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_U8)
            assert(sessionNA51.clValue.itsParse.itsArrayValue[0].itsValueInStr == "1")
            assert(sessionNA51.clValue.itsParse.itsArrayValue[3].itsValueInStr == "0")
            //assertion for Transform of type WriteAccount for ExecutionResult item 16,  18
            val effect5:  ExecutionEffect = getDeployResult5.executionResults[0].result.effect
            val transformWriteAccount1:  TransformEntry = effect5.transforms[16]
            assert(transformWriteAccount1.key == "account-hash-5c6c49477ffa1dabc642d4cc894a21b248e721ee66128c59588393acea1ff196")
            assert(transformWriteAccount1.transform.itsType == ConstValues.TRANSFORM_WRITE_ACCOUNT)
            assert(transformWriteAccount1.transform.itsValue[0] == "account-hash-5c6c49477ffa1dabc642d4cc894a21b248e721ee66128c59588393acea1ff196")
            val transformWriteAccount2:  TransformEntry = effect5.transforms[18]
            assert(transformWriteAccount2.key == "account-hash-5c6c49477ffa1dabc642d4cc894a21b248e721ee66128c59588393acea1ff196")
            assert(transformWriteAccount2.transform.itsType == ConstValues.TRANSFORM_WRITE_ACCOUNT)
            assert(transformWriteAccount2.transform.itsValue[0] == "account-hash-5c6c49477ffa1dabc642d4cc894a21b248e721ee66128c59588393acea1ff196")
        } catch (e: IllegalArgumentException) {
        }
        try {
            //Test 8 Transform of type AddKeys,  WriteContractWasm,  WriteContract,  WriteContractPackage,  Session ModuleBytes empty args list
            //https: //testnet.cspr.live/deploy/091bd25de1769c0df180fed8c67a396c7e4373639b4a8469e26a605f92e72df0
            getDeployParams.deploy_hash = "091bd25de1769c0df180fed8c67a396c7e4373639b4a8469e26a605f92e72df0"
            val postParameter6 = getDeployParams.generatePostParameterStr()
            getDeployRPC.methodURL = ConstValues.TESTNET_URL
            val getDeployResult6 = getDeployRPC.getDeployFromJsonStr(postParameter6)
            assert(getDeployResult6.deploy.hash == "091bd25de1769c0df180fed8c67a396c7e4373639b4a8469e26a605f92e72df0")
            assert(getDeployResult6.deploy.session.itsType == ExecutableDeployItem.MODULE_BYTES)
            val ediSession6 = getDeployResult6.deploy.session.itsValue[0] as ExecutableDeployItem_ModuleBytes
            assert(ediSession6.args.listNamedArg.count() == 0)
            val effect6:  ExecutionEffect = getDeployResult6.executionResults[0].result.effect
            //assertion for Transform of type WriteContractWasm
            val transformWriteContractWasm:  TransformEntry = effect6.transforms[11]
            assert(transformWriteContractWasm.key == "hash-98c2fd452242526d4c53366705f169812a4cc5b2ee89bf63c6fb28f3c5a3f503")
            assert(transformWriteContractWasm.transform.itsType == ConstValues.TRANSFORM_WRITE_CONTRACT_WASM)
            //assertion for Transform of type WriteContract
            val transformWriteContract:  TransformEntry = effect6.transforms[12]
            assert(transformWriteContract.key == "hash-5bfb3233216d08490ab74ddd04b0ff7df3450a63d15976efcf57facd2ebaf5bf")
            assert(transformWriteContract.transform.itsType == ConstValues.TRANSFORM_WRITE_CONTRACT)
            //assertion for Transform of type WriteContractPackage
            val transformWriteContractPackage:  TransformEntry = effect6.transforms[13]
            assert(transformWriteContractPackage.key == "hash-996d8ef48d767a83b8098acbfbbd25cc56a6194807af6e94f190d672c0b1ee23")
            assert(transformWriteContractPackage.transform.itsType == ConstValues.TRANSFORM_WRITE_CONTRACT_PACKAGE)
            //assertion for Transform of type AddKeys
            val transformAddKey:  TransformEntry = effect6.transforms[14]
            assert(transformAddKey.key == "account-hash-a66aa5ab61b26cd4178cba3fa5657652013f57689eb25e111f3aa974443591b1")
            assert(transformAddKey.transform.itsType == ConstValues.TRANSFORM_ADD_KEY)
            assert(transformAddKey.transform.itsValue.count() == 1)
            val namedKey:  NamedKey = transformAddKey.transform.itsValue[0] as NamedKey
            assert(namedKey.key == "hash-5bfb3233216d08490ab74ddd04b0ff7df3450a63d15976efcf57facd2ebaf5bf")
            assert(namedKey.name == "mykv_contract")
            //assertion for Transform of type CLValue Unit
            val transformCLValueUnit:  TransformEntry = effect6.transforms[8]
            assert(transformCLValueUnit.key == "uref-b9aa204ee039055ed4ac3986647ba165640e8fe04b6b4a0ebd7ab8fd4312f969-000")
            assert(transformCLValueUnit.transform.itsType == ConstValues.TRANSFORM_WRITE_CLVALUE)
            val clValueUnit:  CLValue = transformCLValueUnit.transform.itsValue[0] as CLValue
            assert(clValueUnit.itsParse.itsValueInStr == ConstValues.VALUE_NULL)
            assert(clValueUnit.itsCLType.itsTypeStr == ConstValues.CLTYPE_UNIT)
            assert(clValueUnit.itsBytes == "")
        } catch (e: IllegalArgumentException) {
        }
        try {
            //Test for CLType of Result,  based on this Deploy address
            //https: //testnet.cspr.live/deploy/2ad794272a1a805082f171f96f1ea0e71fcac3ae6dd0c525343199b553be8a61
            getDeployParams.deploy_hash = "2ad794272a1a805082f171f96f1ea0e71fcac3ae6dd0c525343199b553be8a61"
            val postParameter7 = getDeployParams.generatePostParameterStr()
            getDeployRPC.methodURL = ConstValues.TESTNET_URL
            val getDeployResult7 = getDeployRPC.getDeployFromJsonStr(postParameter7)
            assert(getDeployResult7.deploy.hash == "2ad794272a1a805082f171f96f1ea0e71fcac3ae6dd0c525343199b553be8a61")
            assert(getDeployResult7.deploy.session.itsType == ExecutableDeployItem.MODULE_BYTES)
            val ediSession7 = getDeployResult7.deploy.session.itsValue[0] as ExecutableDeployItem_ModuleBytes
            assert(ediSession7.args.listNamedArg.count() == 0)
            assert(getDeployResult7.deploy.approvals.count() == 1)
            assert(getDeployResult7.deploy.approvals[0].signature == "0159d9730d9ece17461fff8bc81c06a6c688667028bafdebb99dddfcbc337ca87a82a1a26b48375df0d0f302a8270b12e930d1186c78e95e1d4a5794cf5551fa0d")
            assert(getDeployResult7.deploy.approvals[0].signer == "01a018bf278f32fdb7b06226071ce399713ace78a28d43a346055060a660ba7aa9")
            val effect7:  ExecutionEffect = getDeployResult7.executionResults[0].result.effect
            //assertion for Transform of type CLValue Option(String)
            val transformCLValueResult:  TransformEntry = effect7.transforms[11]
            assert(transformCLValueResult.key == "uref-730f718c3c43ba884735c02dd7b5de931b0cc764e4c4e55e2ef222c0e50cd7c3-000")
            assert(transformCLValueResult.transform.itsType == ConstValues.TRANSFORM_WRITE_CLVALUE)
            val clValueOptionString:  CLValue = transformCLValueResult.transform.itsValue[0] as CLValue
            assert(clValueOptionString.itsBytes == "0103000000616263")
            assert(clValueOptionString.itsParse.innerParsed1.itsValueInStr == "abc")
            assert(clValueOptionString.itsCLType.itsTypeStr == ConstValues.CLTYPE_OPTION)
            assert(clValueOptionString.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_STRING)
            //assertion for Transform of type CLValue Result
            val transformCLValueResult2:  TransformEntry = effect7.transforms[16]
            assert(transformCLValueResult2.key == "uref-9eae5968006607cc910450d191dd2b83e93311a2200c4385cd9f47c2a0ff09f7-000")
            assert(transformCLValueResult2.transform.itsType == ConstValues.TRANSFORM_WRITE_CLVALUE)
            val clValueResult:  CLValue = transformCLValueResult2.transform.itsValue[0] as CLValue
            assert(clValueResult.itsCLType.itsTypeStr == ConstValues.CLTYPE_RESULT)
            assert(clValueResult.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_STRING)
            assert(clValueResult.itsCLType.innerCLType2.itsTypeStr == ConstValues.CLTYPE_STRING)
            assert(clValueResult.itsBytes == "010a000000676f6f64726573756c74")
            assert(clValueResult.itsParse.itsValueInStr == "Ok")
            assert(clValueResult.itsParse.innerParsed1.itsValueInStr == "goodresult")
            //assertion for CLType Tuple 2
            val transformCLValueTuple2:  TransformEntry = effect7.transforms[31]
            assert(transformCLValueTuple2.key == "uref-75789066d17abd5a2629c3f5b82af2827c2098edde6868356ea5114d7d6fa86d-000")
            assert(transformCLValueTuple2.transform.itsType == ConstValues.TRANSFORM_WRITE_CLVALUE)
            val clValueTuple2:  CLValue = transformCLValueTuple2.transform.itsValue[0] as CLValue
            assert(clValueTuple2.itsCLType.itsTypeStr == ConstValues.CLTYPE_TUPLE2)
            assert(clValueTuple2.itsBytes == "030000006162630101")
            assert(clValueTuple2.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_STRING)
            assert(clValueTuple2.itsCLType.innerCLType2.itsTypeStr == ConstValues.CLTYPE_U512)
            assert(clValueTuple2.itsParse.innerParsed1.itsValueInStr == "abc")
            assert(clValueTuple2.itsParse.innerParsed2.itsValueInStr == "1")
            //assertion for CLType Tuple 3
            val transformCLValueTuple3:  TransformEntry = effect7.transforms[36]
            assert(transformCLValueTuple3.key == "uref-e8c07b8ebbd0e7af43283a767975d5d82a334cd92b339eba5c1a1a7ba0137a27-000")
            assert(transformCLValueTuple3.transform.itsType == ConstValues.TRANSFORM_WRITE_CLVALUE)
            val clValueTuple3:  CLValue = transformCLValueTuple3.transform.itsValue[0] as CLValue
            assert(clValueTuple3.itsCLType.itsTypeStr == ConstValues.CLTYPE_TUPLE3)
            assert(clValueTuple3.itsBytes == "01a018bf278f32fdb7b06226071ce399713ace78a28d43a346055060a660ba7aa901030000006162630102")
            assert(clValueTuple3.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_PUBLIC_KEY)
            assert(clValueTuple3.itsCLType.innerCLType2.itsTypeStr == ConstValues.CLTYPE_OPTION)
            assert(clValueTuple3.itsCLType.innerCLType2.innerCLType1.itsTypeStr == ConstValues.CLTYPE_STRING)
            assert(clValueTuple3.itsCLType.innerCLType3.itsTypeStr == ConstValues.CLTYPE_U512)
            assert(clValueTuple3.itsParse.innerParsed1.itsValueInStr == "01a018bf278f32fdb7b06226071ce399713ace78a28d43a346055060a660ba7aa9")
            assert(clValueTuple3.itsParse.innerParsed2.innerParsed1.itsValueInStr == "abc")
            assert(clValueTuple3.itsParse.innerParsed3.itsValueInStr == "2")
        } catch (e: IllegalArgumentException) {
        }
        try {
            //Test 8 for CLValue of Map and Any
            //Base on this Deploy at this address
            //https: //testnet.cspr.live/deploy/430df377ae04726de907f115bb06c52e40f6cd716b4b475a10e4cd9226d1317e
            getDeployParams.deploy_hash = "430df377ae04726de907f115bb06c52e40f6cd716b4b475a10e4cd9226d1317e"
            val postParameter8 = getDeployParams.generatePostParameterStr()
            getDeployRPC.methodURL = ConstValues.TESTNET_URL
            val getDeployResult8 = getDeployRPC.getDeployFromJsonStr(postParameter8)
            assert(getDeployResult8.deploy.hash == "430df377ae04726de907f115bb06c52e40f6cd716b4b475a10e4cd9226d1317e")
            val effect8:  ExecutionEffect = getDeployResult8.executionResults[0].result.effect
            //assertion for Transform of type CLValue Map(String, String)
            val transformCLValueMap:  TransformEntry = effect8.transforms[86]
            assert(transformCLValueMap.key == "uref-857fcc81c7d3005f3fc8076efafb4eeda5c7a2e3243f3492a9599be80f2a7ad1-000")
            assert(transformCLValueMap.transform.itsType == ConstValues.TRANSFORM_WRITE_CLVALUE)
            val clValueMap:  CLValue = transformCLValueMap.transform.itsValue[0] as CLValue
            assert(clValueMap.itsCLType.itsTypeStr == ConstValues.CLTYPE_MAP)
            assert(clValueMap.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_STRING)
            assert(clValueMap.itsCLType.innerCLType2.itsTypeStr == ConstValues.CLTYPE_STRING)
            assert(clValueMap.itsBytes == "0400000015000000636f6e74726163745f7061636b6167655f6861736840000000643332444531353263306242464463414666356232613630373043643732394663304633656143463330306136623565326162414230333530323743343962630a0000006576656e745f747970650400000073796e630800000072657365727665301800000034313239343931343739373335363933323135333637343708000000726573657276653118000000393931373137313437323638353639383438313432343138")
            assert(clValueMap.itsParse.innerParsed1.itsArrayValue.count() == 4)
            assert(clValueMap.itsParse.innerParsed1.itsArrayValue[0].itsValueInStr == "contract_package_hash")
            assert(clValueMap.itsParse.innerParsed2.itsArrayValue.count() == 4)
            assert(clValueMap.itsParse.innerParsed2.itsArrayValue[0].itsValueInStr == "d32DE152c0bBFDcAFf5b2a6070Cd729Fc0F3eaCF300a6b5e2abAB035027C49bc")
            //assertion for Transform of type CLValue Any
            val transformCLValueAny:  TransformEntry = effect8.transforms[81]
            assert(transformCLValueAny.key == "dictionary-3f26bd6e2d853683dc35fd5353a4ff051c14efbd766a25b01a49cc9dab8c8120")
            assert(transformCLValueAny.transform.itsType == ConstValues.TRANSFORM_WRITE_CLVALUE)
            val clValueAny:  CLValue = transformCLValueAny.transform.itsValue[0] as CLValue
            assert(clValueAny.itsCLType.itsTypeStr == ConstValues.CLTYPE_ANY)
            assert(clValueAny.itsBytes == "100000000fe56b296e7d896d3d9710a1a9dfa7100720000000ff2a5885d14b4c44252e32973262141d2cb0240f18ce5edb3788a83b979d698d160000007072696365315f63756d756c61746976655f6c617374")
            assert(clValueAny.itsParse.itsValueInStr == ConstValues.VALUE_NULL)
        } catch (e: IllegalArgumentException) {
        }
        try {
            //Test for CLType of List(Map(String, String))
            //Base on deploy at this address
            //https: //testnet.cspr.live/deploy/a91d468e2ddc8936f7866bc594794b322f747508c2192fd4eca90ef8a121d45e
            getDeployParams.deploy_hash = "a91d468e2ddc8936f7866bc594794b322f747508c2192fd4eca90ef8a121d45e"
            val postParameter9 = getDeployParams.generatePostParameterStr()
            getDeployRPC.methodURL = ConstValues.TESTNET_URL
            val getDeployResult9 = getDeployRPC.getDeployFromJsonStr(postParameter9)
            assert(getDeployResult9.deploy.hash == "a91d468e2ddc8936f7866bc594794b322f747508c2192fd4eca90ef8a121d45e")
            assert(getDeployResult9.deploy.session.itsType == ExecutableDeployItem.STORED_CONTRACT_BY_HASH)
            val session9Value = getDeployResult9.deploy.session.itsValue[0] as ExecutableDeployItem_StoredContractByHash
            assert(session9Value.args.listNamedArg.count() == 4)
            //assertion for CLValue of type Key - Account again
            assert(session9Value.args.listNamedArg[0].itsName == "recipient")
            assert(session9Value.args.listNamedArg[0].clValue.itsBytes == "00c6d93dd827202f3b37297382b1cb9269c07d71a79a49824bb1a008c650a04473")
            assert(session9Value.args.listNamedArg[0].clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_KEY)
            assert(session9Value.args.listNamedArg[0].clValue.itsParse.itsValueInStr == "account-hash-c6d93dd827202f3b37297382b1cb9269c07d71a79a49824bb1a008c650a04473")
            //assertion for CLValue of type Option(List(String))
            val clValueOptionListString = session9Value.args.listNamedArg[1].clValue
            assert(clValueOptionListString.itsCLType.itsTypeStr == ConstValues.CLTYPE_OPTION)
            assert(clValueOptionListString.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_LIST)
            assert(clValueOptionListString.itsCLType.innerCLType1.innerCLType1.itsTypeStr == ConstValues.CLTYPE_STRING)
            assert(clValueOptionListString.itsBytes == "010100000018000000363165353465636231656366653538376561663963636530")
            assert(clValueOptionListString.itsParse.innerParsed1.itsArrayValue.count() == 1)
            assert(clValueOptionListString.itsParse.innerParsed1.itsArrayValue[0].itsValueInStr == "61e54ecb1ecfe587eaf9cce0")
            //assertion for CLValue of type List(Map(String, String))
            val clValueListMap = session9Value.args.listNamedArg[2].clValue
            assert(clValueListMap.itsBytes == "0100000004000000040000006e616d650f000000546573742050726f642041646d696e0b0000006465736372697074696f6e0700000054657374696e6708000000697066735f75726c5000000068747470733a2f2f676174657761792e70696e6174612e636c6f75642f697066732f516d6175505535726338676868795a465178423952356a43626161664777324d6e65514a524d44574c567a6a615511000000697066735f6d657461646174615f75726c5000000068747470733a2f2f676174657761792e70696e6174612e636c6f75642f697066732f516d627279797641795664426d355a346e774133613738316d6d717563366e476165754541504b393661334c506e")
            assert(clValueListMap.itsCLType.itsTypeStr == ConstValues.CLTYPE_LIST)
            assert(clValueListMap.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_MAP)
            assert(clValueListMap.itsCLType.innerCLType1.innerCLType1.itsTypeStr == ConstValues.CLTYPE_STRING)
            assert(clValueListMap.itsCLType.innerCLType1.innerCLType2.itsTypeStr == ConstValues.CLTYPE_STRING)
            assert(clValueListMap.itsParse.itsArrayValue.count() == 1)
            //map key list
            assert(clValueListMap.itsParse.itsArrayValue[0].innerParsed1.itsArrayValue.count() == 4)
            assert(clValueListMap.itsParse.itsArrayValue[0].innerParsed1.itsArrayValue[0].itsValueInStr == "name")
            //map value list
            assert(clValueListMap.itsParse.itsArrayValue[0].innerParsed2.itsArrayValue.count() == 4)
            assert(clValueListMap.itsParse.itsArrayValue[0].innerParsed2.itsArrayValue[0].itsValueInStr == "Test Prod Admin")
            //assertion for CLValue of type List(Map(String, String)) but parse empty
            val clValueListMap2 = session9Value.args.listNamedArg[3].clValue
            assert(clValueListMap2.itsBytes == "0100000000000000")
            assert(clValueListMap2.itsCLType.itsTypeStr == ConstValues.CLTYPE_LIST)
            assert(clValueListMap2.itsCLType.innerCLType1.itsTypeStr == ConstValues.CLTYPE_MAP)
            assert(clValueListMap2.itsCLType.innerCLType1.innerCLType1.itsTypeStr == ConstValues.CLTYPE_STRING)
            assert(clValueListMap2.itsCLType.innerCLType1.innerCLType2.itsTypeStr == ConstValues.CLTYPE_STRING)
            assert(clValueListMap2.itsParse.itsArrayValue.count() == 1)
            assert(clValueListMap2.itsParse.itsArrayValue[0].isInnerParsed1Initialize() == false)
            assert(clValueListMap2.itsParse.itsArrayValue[0].isInnerParsed2Initialize() == false)
        } catch (e: IllegalArgumentException) {
        }
        try {
            //Test for Session of type StoredVersionedContractByHash and CLType of U256
            //Base on the deploy at this address
            //https: //testnet.cspr.live/deploy/9d13abf758dce8663c070a223d95cca8b2e6f014d91fc6cd6f40ed21dbf55aba
            getDeployParams.deploy_hash = "9d13abf758dce8663c070a223d95cca8b2e6f014d91fc6cd6f40ed21dbf55aba"
            val postParameter10 = getDeployParams.generatePostParameterStr()
            getDeployRPC.methodURL = ConstValues.TESTNET_URL
            val getDeployResult10 = getDeployRPC.getDeployFromJsonStr(postParameter10)
            assert(getDeployResult10.deploy.hash == "9d13abf758dce8663c070a223d95cca8b2e6f014d91fc6cd6f40ed21dbf55aba")
            assert(getDeployResult10.deploy.session.itsType == ExecutableDeployItem.STORED_VERSIONED_CONTRACT_BY_HASH)
            val session10Value =
                getDeployResult10.deploy.session.itsValue[0] as ExecutableDeployItem_StoredVersionedContractByHash
            assert(session10Value.args.listNamedArg.count() == 2)
            assert(session10Value.itsHash == "5daa83c7d18629fcdf3910ef4a284b6a3288e8879b24b199966857e857244844")
            assert(session10Value.isVersionExisted == false)
            assert(session10Value.entryPoint == "flash_borrow")
            assert(session10Value.args.listNamedArg[1].itsName == "amount")
            assert(session10Value.args.listNamedArg[1].clValue.itsBytes == "02ae08")
            assert(session10Value.args.listNamedArg[1].clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_U256)
            assert(session10Value.args.listNamedArg[1].clValue.itsParse.itsValueInStr == "2222")
        } catch (e: IllegalArgumentException) {
        }
        //Negative Path 1:  Get Deploy with wrong deploy hash
        try {
            getDeployParams.deploy_hash = "AAABBBCCC3abf758dce8663c070a223d95cca8b2e6f014d91fc6cd6f40ed21dbf55aba"
            val postParameter11 = getDeployParams.generatePostParameterStr()
            getDeployRPC.methodURL = ConstValues.TESTNET_URL
            getDeployRPC.getDeployFromJsonStr(postParameter11)
        } catch (e: IllegalArgumentException) {
            println("Error IllegalArgumentException caught")
        }
        //Negative Path 1:  Get Deploy with no parameter - no deploy hash
        try {
            getDeployParams.deploy_hash = ""
            val postParameter11 = getDeployParams.generatePostParameterStr()
            getDeployRPC.methodURL = ConstValues.TESTNET_URL
            getDeployRPC.getDeployFromJsonStr(postParameter11)
        } catch (e: IllegalArgumentException) {
            println("Error IllegalArgumentException caught")
        }
    }
}