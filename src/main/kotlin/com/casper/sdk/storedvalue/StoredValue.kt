package com.casper.sdk.storedvalue

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLValue
import net.jemzart.jsonkraken.values.JsonObject

class StoredValue {
    var itsType:String = ConstValues.STORED_VALUE_CLVALUE
    var itsValue:MutableList<Any> = mutableListOf()
    companion object {
        fun fromJsonObjectToStoredValue(from:JsonObject): StoredValue {
            var ret: StoredValue = StoredValue()
            //Get StoredValue as enum type CLValue
            val storeValueCLValue = from["CLValue"]
            if(storeValueCLValue != null) {
                val clValue: CLValue = CLValue.fromJsonObjToCLValue(from["CLValue"] as JsonObject)
                ret.itsValue.add(clValue)
                ret.itsType = ConstValues.STORED_VALUE_CLVALUE
            }
            //Get StoredValue as enum type Account
            val storeValueAccount = from["Account"]
            if(storeValueAccount != null) {
                var account: Account = Account.fromJsonObjectToAccount(from["Account"] as JsonObject)
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
               // ret.itsValue.add(from["Contract"] as String)
                ret.itsType = ConstValues.STORED_VALUE_CONTRACT
            }
            return ret
        }
    }
}