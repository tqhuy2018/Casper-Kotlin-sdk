package com.casper.sdk.getdeploy.ExecutionResult.Transform

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing UnbondingPurse information */
class UnbondingPurse {
    var bondingPurse: String = ""
    var validatorPublicKey: String = ""
    var unbonderPublicKey: String = ""
    var eraOfCreation: ULong = 0u
    var amount: U512Class = U512Class()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the UnbondingPurse object */
        fun  fromJsonToUnbondingPurse(from: JsonObject): UnbondingPurse {
            var ret: UnbondingPurse = UnbondingPurse()
            ret.bondingPurse = from["bonding_purse"].toString()
            ret.validatorPublicKey = from["validator_public_key"].toString()
            ret.unbonderPublicKey = from["unbonder_public_key"].toString()
            //val era = from["era_of_creation"].toJsonString()
           // println("era: ${era}")
            ret.eraOfCreation = from["era_of_creation"].toJsonString().toULong()
            ret.amount = U512Class.fromStringToU512(from["amount"].toString())
            return ret
        }
    }
}