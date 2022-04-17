package com.casper.sdk.getauction

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.values.JsonObject

class JsonValidatorWeights {
    var publicKey:String = ""
    var weight:U512Class = U512Class()
    companion object {
        fun fromJsonObjectToJsonValidatorWeights(from:JsonObject):JsonValidatorWeights {
            var ret:JsonValidatorWeights = JsonValidatorWeights()
            ret.publicKey = from["public_key"].toString()
            ret.weight = U512Class.fromStringToU512(from["weight"].toString())
            return ret
        }
    }
}