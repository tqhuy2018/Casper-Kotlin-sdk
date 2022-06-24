package com.casper.sdk.getdeploy.ExecutionResult.Transform

import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing NamedKey information */
class NamedKey {
    var name: String = ""
    var key: String = ""
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the NamedKey object */
        fun fromJsonObjectToNamedKey(from: JsonObject): NamedKey {
            val ret = NamedKey()
            ret.name = from["name"].toString()
            ret.key = from["key"].toString()
            return ret
        }
    }
}