package com.casper.sdk.getitem

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLValue
import com.casper.sdk.getdeploy.ExecutionResult.Transform.DeployInfo
import com.casper.sdk.getdeploy.ExecutionResult.Transform.Transfer
import com.casper.sdk.storedvalue.Account
import com.casper.sdk.storedvalue.StoredValue
import com.casper.sdk.getdeploy.ExecutionResult.Transform.Bid
import com.casper.sdk.getdeploy.ExecutionResult.Transform.Withdraw
import org.junit.jupiter.api.Test

internal class GetItemRPCTest {

    @Test
    fun getItem() {
        val getItemRPC = GetItemRPC()
        val getItemParameter = GetItemParams()
        //Test for StoredValue of type CLValue
        try {
            getItemParameter.stateRootHash = "340a09b06bae99d868c68111b691c70d9d5a253c0f2fd7ee257a04a198d3818e"
            getItemParameter.key = "uref-ba620eee2b06c6df4cd8da58dd5c5aa6d42f3a502de61bb06dc70b164eee4119-007"
            val params:  String = getItemParameter.generateParameterStr()
            val getItemResult = getItemRPC.getItem(params)
            assert(getItemResult.apiVersion == "1.4.5")
            assert(getItemResult.merkleProof.length == 35056)
            assert(getItemResult.storedValue.itsType == ConstValues.STORED_VALUE_CLVALUE)
            val clValue: CLValue = getItemResult.storedValue.itsValue[0] as CLValue
            assert(clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_ANY)
            assert(clValue.itsParse.itsValueInStr == ConstValues.VALUE_NULL)
            assert(clValue.itsBytes == "050000006b69747479040000000c0000004f6666696365205370616365010c00000050756c702046696374696f6e011200000054686520426c7565732042726f7468657273000d00000054686520476f6466617468657200")
        } catch (e: IllegalArgumentException) { }
        //Test for StoredValue of type Account
        try {
            getItemParameter.stateRootHash = "b31f42523b6799d6d403a3119596c958abf2cdba31066322f01e5fa38ef999aa"
            getItemParameter.key = "account-hash-ff2ae80f71c1ffcac4921100a21b67ddecf59a30fb86eb6979f47c8838b3b7d3"
            val params :  String = getItemParameter.generateParameterStr()
            val getItemResult = getItemRPC.getItem(params)
            assert(getItemResult.apiVersion == "1.4.5")
            assert(getItemResult.merkleProof.length == 25428)
            val storedValue: StoredValue = getItemResult.storedValue
            assert(storedValue.itsType == ConstValues.STORED_VALUE_ACCOUNT)
            val account: Account = storedValue.itsValue[0] as Account
            assert(account.accountHash == "account-hash-ff2ae80f71c1ffcac4921100a21b67ddecf59a30fb86eb6979f47c8838b3b7d3")
            assert(account.namedKeys.count() == 0)
            assert(account.mainPurse == "uref-cd58e9c4a8d1caaba3a3fc030a112a8b4bd904fd83b806bf575c25751e20ee22-007")
            assert(account.associatedKeys.count() == 1)
            assert(account.associatedKeys[0].accountHash == "account-hash-ff2ae80f71c1ffcac4921100a21b67ddecf59a30fb86eb6979f47c8838b3b7d3")
            assert(account.associatedKeys[0].weight.toString() == "1")
            assert(account.actionThresholds.deployment.toString() == "1")
            assert(account.actionThresholds.keyManagement.toString() == "1")
        } catch (e: IllegalArgumentException) { }
        //Test for StoredValue of type Transfer
        try {
            getItemParameter.stateRootHash = "1416302b2c637647e2fe8c0b9f7ee815582cc7a323af5823313ff8a8684c8cf8"
            getItemParameter.key = "transfer-8218fa8c55c19264e977bf2bae9f5889082aee4d2c4eaf9642478173c37d1ed4"
            val params:  String = getItemParameter.generateParameterStr()
            val getItemResult = getItemRPC.getItem(params)
            assert(getItemResult.apiVersion == "1.4.5")
            assert(getItemResult.merkleProof.length == 41424)
            val storedValue:  StoredValue = getItemResult.storedValue
            assert(storedValue.itsType == ConstValues.STORED_VALUE_TRANSFER)
            val transfer:  Transfer = storedValue.itsValue[0] as Transfer
            assert(transfer.deployHash == "e96e884ea0d816d478e965a655e0280d69353b7e231180c34453407a6055646d")
            assert(transfer.from == "account-hash-516bae78a83f7b0f6a34a256507434e0f1a432cb0bb2212ca54a01d9ca5a15c9")
            assert(transfer.to == "account-hash-45f3aa6ce2a450dd5a4f2cc4cc9054aded66de6b6cfc4ad977e7251cf94b649b")
            assert(transfer.source == "uref-138ed0de11e2837215e06af87c579bc389459f885be8e124fde4c317df2891d7-007")
            assert(transfer.target == "uref-c874cfe0c930bb6d44bce16417a78aecf02f87c8890149f91d6b5802152a1dd6-004")
            assert(transfer.amount.itsValue == "2500000000")
            assert(transfer.gas.itsValue == "0")
            assert(transfer.id == "0")
        } catch (e: IllegalArgumentException) {}
        //Test for StoredValue of type DeployInfo
        try {
            getItemParameter.stateRootHash = "1416302b2c637647e2fe8c0b9f7ee815582cc7a323af5823313ff8a8684c8cf8"
            getItemParameter.key = "deploy-a49c06f9b2adf02812a7b2fdcad08804a2ce4896ffec7c06c920d417e7e39cfe"
            val params:  String = getItemParameter.generateParameterStr()
            val getItemResult = getItemRPC.getItem(params)
            assert(getItemResult.apiVersion == "1.4.5")
            assert(getItemResult.merkleProof.length == 39984)
            val storedValue:  StoredValue = getItemResult.storedValue
            assert(storedValue.itsType == ConstValues.STORED_VALUE_DEPLOY_INFO)
            val deployInfo:  DeployInfo = storedValue.itsValue[0] as DeployInfo
            assert(deployInfo.deployHash == "a49c06f9b2adf02812a7b2fdcad08804a2ce4896ffec7c06c920d417e7e39cfe")
            assert(deployInfo.from == "account-hash-516bae78a83f7b0f6a34a256507434e0f1a432cb0bb2212ca54a01d9ca5a15c9")
            assert(deployInfo.source == "uref-138ed0de11e2837215e06af87c579bc389459f885be8e124fde4c317df2891d7-007")
            assert(deployInfo.gas.itsValue == "100000000")
            assert(deployInfo.transfers.count() == 1)
            assert(deployInfo.transfers[0] == "transfer-c749305c07f8de0aa1898929db4a93b3b136e408707878ae15155910d840b4c7")
        } catch (e: IllegalArgumentException) {}
        //Test for StoredValue of type Bid
        try {
            getItemRPC.methodURL = ConstValues.MAINNET_URL
            getItemParameter.stateRootHash = "647C28545316E913969B032Cf506d5D242e0F857061E70Fb3DF55980611ace86"
            getItemParameter.key = "bid-24b6D5Aabb8F0AC17D272763A405E9CECa9166B75B745Cf200695E172857c2dD"
            val params:  String = getItemParameter.generateParameterStr()
            val getItemResult = getItemRPC.getItem(params)
            assert(getItemResult.apiVersion == "1.4.5")
            assert(getItemResult.merkleProof.length > 0)
            val storedValue:  StoredValue = getItemResult.storedValue
            assert(storedValue.itsType == ConstValues.STORED_VALUE_BID)
            val bid:  Bid = storedValue.itsValue[0] as Bid
            assert(bid.validatorPublicKey == "012bac1d0ff9240ff0b7b06d555815640497861619ca12583ddef434885416e69b")
            assert(bid.bondingPurse == "uref-9ef6b11bd095c1733956e3b7e5bb47630f5fa59ad9a89c87fa671a1177e0c025-007")
            assert(bid.stakedAmount.itsValue == "208330980103513")
            assert(bid.delegationRate.toString() == "10")
            assert(bid.vestingSchedule.initialReleaseTimestampMillis.toString() == "1624978800000")
            assert(bid.vestingSchedule.lockedAmounts.count() == 14)
            assert(bid.vestingSchedule.lockedAmounts[0].itsValue == "1359796682549793")
            assert(bid.vestingSchedule.lockedAmounts[1].itsValue == "1255196937738271")
            assert(bid.vestingSchedule.lockedAmounts[2].itsValue == "1150597192926749")
            assert(bid.vestingSchedule.lockedAmounts[13].itsValue == "0")
            assert(bid.delegators.count() > 0)
            assert(bid.delegators[0].delegatorPublicKey == "0100716b994e9264452111d2c9cdca048137906c22976129b93e0f1539ad4df449")
            assert(bid.delegators[0].itsPublicKey == "0100716b994e9264452111d2c9cdca048137906c22976129b93e0f1539ad4df449")
            assert(bid.delegators[0].stakedAmount.itsValue == "11906543547306")
            assert(bid.delegators[0].bondingPurse == "uref-1b52cdf087eb535471e0507269cb14be2abeb7427e102acb18b35cfb5df9a273-007")
            assert(bid.delegators[0].validatorPublicKey == "012bac1d0ff9240ff0b7b06d555815640497861619ca12583ddef434885416e69b")
            assert(bid.delegators[0].isVestingScheduleExisted == false)
            //assertion for delegator with existing vestingSchedule
            assert(bid.delegators[41].delegatorPublicKey == "01045ac0fd84e85c852526fa58447fd36c1054cc67cde05992acf65411bb568d01")
            assert(bid.delegators[41].itsPublicKey == "01045ac0fd84e85c852526fa58447fd36c1054cc67cde05992acf65411bb568d01")
            assert(bid.delegators[41].stakedAmount.itsValue == "133997464806886")
            assert(bid.delegators[41].bondingPurse == "uref-8f50ea318c811305439693990329c94d5a16214ea636e73167cb39d7630edcb7-007")
            assert(bid.delegators[41].validatorPublicKey == "012bac1d0ff9240ff0b7b06d555815640497861619ca12583ddef434885416e69b")
            assert(bid.delegators[41].isVestingScheduleExisted == true)
            assert(bid.delegators[41].vestingSchedule.initialReleaseTimestampMillis.toString() == "1624978800000")
            assert(bid.delegators[41].vestingSchedule.lockedAmounts.count() == 14)
            assert(bid.delegators[41].vestingSchedule.lockedAmounts[0].itsValue == "33805201971412311")
            assert(bid.delegators[41].vestingSchedule.lockedAmounts[1].itsValue == "31204801819765211")
        } catch (e: IllegalArgumentException) {}
        //Test for StoredValue of type Withdraw
        try {
            getItemRPC.methodURL = ConstValues.TESTNET_URL
            getItemParameter.stateRootHash = "d360e2755f7cee816cce3f0eeb2000dfa03113769743ae5481816f3983d5f228"
            getItemParameter.key = "withdraw-df067278a61946b1b1f784d16e28336ae79f48cf692b13f6e40af9c7eadb2fb1"
            val params:  String = getItemParameter.generateParameterStr()
            val getItemResult = getItemRPC.getItem(params)
            assert(getItemResult.apiVersion == "1.4.5")
            assert(getItemResult.merkleProof.length > 0)
            val storedValue:  StoredValue = getItemResult.storedValue
            assert(storedValue.itsType == ConstValues.STORED_VALUE_WITHDRAW)
            val withdraw: Withdraw = storedValue.itsValue[0] as Withdraw
            assert(withdraw.listUnbondingPurse.count() == 1)
            assert(withdraw.listUnbondingPurse[0].bondingPurse == "uref-5fcc3031ea2572f9929e0cfcfc84c6c3131bfe1e78bce8cb61f99f59eace7795-007")
            assert(withdraw.listUnbondingPurse[0].validatorPublicKey == "01d949a3a1963db686607a00862f79b76ceb185fc134d0aeedb686f1c151f4ae54")
            assert(withdraw.listUnbondingPurse[0].amount.itsValue == "500")
            assert(withdraw.listUnbondingPurse[0].unbonderPublicKey == "01d949a3a1963db686607a00862f79b76ceb185fc134d0aeedb686f1c151f4ae54")
            assert(withdraw.listUnbondingPurse[0].eraOfCreation.toString() == "3319")
        } catch (e: IllegalArgumentException) {}
    }
}