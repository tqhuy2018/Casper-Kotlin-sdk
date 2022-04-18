package com.casper.sdk.storedvalue

import com.casper.sdk.clvalue.CLType
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing Parameter information */
class Parameter {
    var name: String = ""
    var clType: CLType = CLType()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the Account object */

        fun fromJsonObjectToParameter(from: JsonObject): Parameter {
            var ret = Parameter()
            ret.name = from["name"].toString()
            ret.clType = CLType.getCLType(from["cl_type"] as Any)
            return ret
        }
    }
}