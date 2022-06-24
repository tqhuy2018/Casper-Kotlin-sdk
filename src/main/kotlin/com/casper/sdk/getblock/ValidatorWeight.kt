package com.casper.sdk.getblock

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing ValidatorWeight information */
class ValidatorWeight {
    var validator: String = ""
    var weight: U512Class = U512Class()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to ValidatorWeight object */
        fun fromJsonToValidatorWeight(from: JsonObject): ValidatorWeight {
            val ret = ValidatorWeight()
            ret.validator = from["validator"].toString()
            ret.weight = U512Class.fromStringToU512(from["weight"].toString())
            return ret
        }
    }
}