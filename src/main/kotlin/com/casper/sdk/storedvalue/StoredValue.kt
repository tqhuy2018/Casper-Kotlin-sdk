package com.casper.sdk.storedvalue

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLValue
import com.casper.sdk.getdeploy.ExecutionResult.Transform.*
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing StoredValue information */
class StoredValue {
    var itsType: String = ""
    var itsValue: MutableList<Any> = mutableListOf()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the StoredValue object */
        fun fromJsonObjectToStoredValue(from: JsonObject):  StoredValue {
            val ret = StoredValue()
            //Get StoredValue as enum type CLValue
            val storeValueCLValue = from["CLValue"]
            if(storeValueCLValue != null) {
                val clValue:  CLValue = CLValue.fromJsonObjToCLValue(from["CLValue"] as JsonObject)
                ret.itsValue.add(clValue)
                ret.itsType = ConstValues.STORED_VALUE_CLVALUE
            }
            //Get StoredValue as enum type Account
            val storeValueAccount = from["Account"]
            if(storeValueAccount != null) {
                var account:  Account = Account.fromJsonObjectToAccount(from["Account"] as JsonObject)
                ret.itsValue.add(account)
                ret.itsType = ConstValues.STORED_VALUE_ACCOUNT
            }
            //Get StoredValue as enum type ContractWasm
            val storeValueContractWasm = from["ContractWasm"]
            if(storeValueContractWasm != null) {
                ret.itsValue.add(from["ContractWasm"] as String)
                ret.itsType = ConstValues.STORED_VALUE_CONTRACT_WASM
            }
            //Get StoredValue as enum type Contract
            val storeValueContract = from["Contract"]
            if(storeValueContract != null) {
                val contract: CasperContract = CasperContract.fromJsonObjectToCasperContract(from["Contract"] as JsonObject)
                ret.itsType = ConstValues.STORED_VALUE_CONTRACT
                ret.itsValue.add(contract)
            }
            //Get StoredValue as enum type Transfer
            val storedValueTransfer = from["Transfer"]
            if(storedValueTransfer != null) {
                ret.itsType = ConstValues.STORED_VALUE_TRANSFER
                val transfer :  Transfer = Transfer.fromJsonToTransfer(from["Transfer"] as JsonObject)
                ret.itsValue.add(transfer)
            }
            //Get StoredValue as enum type DeployInfo
            val storedValueDeployInfo = from["DeployInfo"]
            if(storedValueDeployInfo != null) {
                ret.itsType = ConstValues.STORED_VALUE_DEPLOY_INFO
                val deployInfo: DeployInfo = DeployInfo.fromJsonToDeployInfo(from["DeployInfo"] as JsonObject)
                ret.itsValue.add(deployInfo)
            }
            //Get StoredValue as enum type Bid
            val storedValueBid = from["Bid"]
            if(storedValueBid != null) {
                ret.itsType = ConstValues.STORED_VALUE_BID
                val bid: Bid = Bid.fromJsonToBid(from["Bid"] as JsonObject)
                ret.itsValue.add(bid)
            }
            //Get StoredValue as enum type Withdraw
            val storedValueWithdraw = from["Withdraw"]
            if(storedValueWithdraw != null) {
                ret.itsType = ConstValues.STORED_VALUE_WITHDRAW
                val withdraw: Withdraw = Withdraw.fromJsonArrayToWithdraw(from["Withdraw"] as JsonArray)
                ret.itsValue.add(withdraw)
            }
            //Get StoredValue as enum type EraInfo
            val storedValueEraInfo = from["EraInfo"]
            if(storedValueEraInfo != null) {
                ret.itsType = ConstValues.STORED_VALUE_ERA_INFO
                val eraInfo: EraInfo = EraInfo.fromJsonArrayToEraInfo(from["EraInfo"] as JsonObject)
                ret.itsValue.add(eraInfo)
            }
            //Get StoredValue as enum type ContractPackage
            val storedValueContractPackage = from["ContractPackage"]
            if(storedValueContractPackage != null) {
                ret.itsType = ConstValues.STORED_VALUE_CONTRACT_PACKAGE
                val contractPackage: ContractPackage = ContractPackage.fromJsonObjectToContractPackage(from["ContractPackage"] as JsonObject)
                ret.itsValue.add(contractPackage)
            }
            return ret
        }
    }
}