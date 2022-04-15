package com.casper.sdk.getblock

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.values.JsonObject

class ValidatorWeight {
    var validator:String = ""
    var weight:U512Class = U512Class()
    companion object {
        fun fromJsonToValidatorWeight(from:JsonObject):ValidatorWeight {
            var ret:ValidatorWeight = ValidatorWeight()
            ret.validator = from["validator"].toString()
            ret.weight = U512Class.fromStringToU512(from["weight"].toString())
            return ret
        }
    }
}