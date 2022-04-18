package com.casper.sdk.getdeploy.ExecutionResult.Transform

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing SeigniorageAllocation information */
class SeigniorageAllocation {
    //can be either Validator or Delegator
    var isValidator: Boolean = false
    var validatorPublicKey: String = ""
    var amount: U512Class = U512Class()
    //this field is for Delegator
    var delegatorPublicKey: String = ""
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the SeigniorageAllocation object */
        fun fromJsonToSeigniorageAllocation(from: JsonObject): SeigniorageAllocation {
            val ret: SeigniorageAllocation = SeigniorageAllocation()
            val validator = from["Validator"].toJsonString()
            if(validator != "null") {//enum of type validator
                ret.isValidator = true
                ret.validatorPublicKey = from["Validator"].get("validator_public_key").toString()
                ret.amount = U512Class.fromStringToU512(from["Validator"].get("amount").toString())
            } else {//enum of type delegator
                ret.isValidator = false
                ret.validatorPublicKey = from["Delegator"].get("validator_public_key").toString()
                ret.amount = U512Class.fromStringToU512(from["Delegator"].get("amount").toString())
                ret.delegatorPublicKey = from["Delegator"].get("delegator_public_key").toString()
            }
            return ret
        }
    }
}