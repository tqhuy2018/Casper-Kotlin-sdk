package com.casper.sdk.storedvalue

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

//This class hold 1 among 2 possible value:
//1 is Public in String
//2 is Groups - a list of Group
class EntryPointAccess {
    var isPublic:Boolean = false
    lateinit var groups:Groups
    fun isGroupsInit():Boolean {
        if(this::groups.isInitialized) {
            return true
        }
        return false
    }
    companion object{
        fun fromJsonObjectToEntryPointAccess(from:JsonObject):EntryPointAccess {
            var ret:EntryPointAccess = EntryPointAccess()
            if(from.toJsonString().toString() == "Public") {
                ret.isPublic = true
            } else {
                ret.groups = Groups()
                ret.groups = Groups.fromJsonObjectToGroups(from["Groups"] as JsonObject)
            }
            return ret
        }
    }
}