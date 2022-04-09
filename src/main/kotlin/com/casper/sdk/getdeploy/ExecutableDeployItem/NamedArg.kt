package com.casper.sdk.getdeploy.ExecutableDeployItem

import com.casper.sdk.clvalue.CLValue
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class NamedArg {
    var itsName:String = ""
    var clValue:CLValue = CLValue()
    companion object {
        fun fromJsonToNamedArg(from:JsonArray):NamedArg {
            var ret:NamedArg = NamedArg()
            ret.itsName = from[0].toString()
            print("item name:${ret.itsName}\n")
            var clObj : JsonObject = from[1] as JsonObject
            print("item CLValue:${clObj.toJsonString()}\n")
            ret.clValue = CLValue.fromJsonObjToCLValue(clObj)
            println("Done get CLValue")
            return ret
        }
    }
}