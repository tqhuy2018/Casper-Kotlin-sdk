package com.casper.sdk.getauction

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing JsonValidatorWeights information */
class JsonValidatorWeights {
    var publicKey: String = ""
    var weight: U512Class = U512Class()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to generate the JsonValidatorWeights object */
        fun fromJsonObjectToJsonValidatorWeights(from: JsonObject): JsonValidatorWeights {
            val ret = JsonValidatorWeights()
            ret.publicKey = from["public_key"].toString()
            ret.weight = U512Class.fromStringToU512(from["weight"].toString())
            return ret
        }
    }
}