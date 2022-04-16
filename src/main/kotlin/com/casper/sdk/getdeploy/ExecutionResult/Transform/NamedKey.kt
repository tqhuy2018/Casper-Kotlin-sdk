package com.casper.sdk.getdeploy.ExecutionResult.Transform

import net.jemzart.jsonkraken.values.JsonObject

class NamedKey {
    var name:String = ""
    var key :String = ""
    companion object {
        fun fromJsonObjectToNamedKey(from:JsonObject):NamedKey {
            var ret:NamedKey = NamedKey()
            ret.name = from["name"].toString()
            ret.key = from["key"].toString()
            return ret
        }
    }
}