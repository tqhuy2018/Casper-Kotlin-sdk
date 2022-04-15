package com.casper.sdk.storedvalue

import com.casper.sdk.clvalue.CLType
import net.jemzart.jsonkraken.values.JsonObject

class EntryPoint {
    var access:EntryPointAccess = EntryPointAccess()
    var args:MutableList<Parameter> = mutableListOf()
    var entryPointType:EntryPointType = EntryPointType()
    var name:String = ""
    var ret:CLType = CLType()
    companion object {
        fun fromJsonObjectToEntryPoint(from:JsonObject):EntryPoint {
            var ret:EntryPoint = EntryPoint()
            //ret.access = EntryPointAccess
            return ret
        }
    }
}