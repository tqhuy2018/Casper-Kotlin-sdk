package com.casper.sdk.getdeploy.ExecutionResult.Transform

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.values.JsonObject

class UnbondingPurse {
    var bondingPurse:String = ""
    var validatorPublicKey:String = ""
    var unbonderPublicKey:String = ""
    var eraOfCreation:ULong = 0u
    var amount:U512Class = U512Class()
    companion object {
        fun  fromJsonToUnbondingPurse(from:JsonObject):UnbondingPurse {
            var ret:UnbondingPurse = UnbondingPurse()
            ret.bondingPurse = from["bonding_purse"].toString()
            ret.validatorPublicKey = from["validator_public_key"].toString()
            ret.unbonderPublicKey = from["unbonder_public_key"].toString()
            ret.eraOfCreation = from["unbonder_public_key"].toString().toULong()
            ret.amount = U512Class.fromStringToU512(from["amount"].toString())
            return ret
        }
    }
}