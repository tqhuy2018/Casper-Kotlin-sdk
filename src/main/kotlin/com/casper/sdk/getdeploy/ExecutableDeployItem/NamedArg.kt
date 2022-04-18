package com.casper.sdk.getdeploy.ExecutableDeployItem

import com.casper.sdk.clvalue.CLValue
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing NamedArg information */
class NamedArg {
    var itsName: String = ""
    var clValue: CLValue = CLValue()
    companion object {
        /** This function parse the JsonArray (taken from server RPC method call) to get the NamedArg object */
        fun fromJsonToNamedArg(from: JsonArray): NamedArg {
            var ret: NamedArg = NamedArg()
            ret.itsName = from[0].toString()
            ret.clValue = CLValue.fromJsonObjToCLValue(from[1] as JsonObject)
            return ret
        }
    }
}