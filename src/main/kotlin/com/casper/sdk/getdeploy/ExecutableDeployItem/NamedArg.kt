package com.casper.sdk.getdeploy.ExecutableDeployItem

import com.casper.sdk.clvalue.CLValue
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class NamedArg {
    var itsName: String = ""
    var clValue: CLValue = CLValue()
    companion object {
        fun fromJsonToNamedArg(from: JsonArray): NamedArg {
            var ret: NamedArg = NamedArg()
            ret.itsName = from[0].toString()
            println("item name: ${ret.itsName}")
            var clObj :  JsonObject = from[1] as JsonObject
            ret.clValue = CLValue.fromJsonObjToCLValue(clObj)
            return ret
        }
    }
}