package com.casper.sdk.storedvalue

import net.jemzart.jsonkraken.values.JsonObject

// This class is for enum type of 2 value: Session and Contract, the default value is Session
class EntryPointType {
    var itsType:String = "Session"
    companion object {
        fun fromJsonToEntryPointType(from:JsonObject) : EntryPointType {
            var ret:EntryPointType = EntryPointType()
            ret.itsType = from["entry_point_type"].toString()
            return  ret
        }
    }
}