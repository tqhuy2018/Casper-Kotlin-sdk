package com.casper.sdk.storedvalue

/** Class built for storing EntryPointType information */
// This class is for enum type of 2 value:  Session and Contract,  the default value is Session
class EntryPointType {
    var itsType: String = "Session"
    companion object {
        /** This function get the String fill the value for EntryPointType object */
        fun fromJsonToEntryPointType(from: String) :  EntryPointType {
            var ret = EntryPointType()
            ret.itsType = from
            return  ret
        }
    }
}