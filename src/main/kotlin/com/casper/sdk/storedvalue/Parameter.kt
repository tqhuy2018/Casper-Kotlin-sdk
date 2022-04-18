package com.casper.sdk.storedvalue

import com.casper.sdk.clvalue.CLType
import net.jemzart.jsonkraken.values.JsonObject

class Parameter {
    var name: String = ""
    var clType: CLType = CLType()
    companion object {
        fun fromJsonObjectToParameter(from: JsonObject): Parameter {
            var ret: Parameter = Parameter()
            ret.name = from["name"].toString()
            ret.clType = CLType.getCLType(from["cl_type"] as Any)
            return ret
        }
    }
}