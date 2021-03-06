package com.casper.sdk.storedvalue

import com.casper.sdk.clvalue.CLType
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing EntryPoint information */
class EntryPoint {
    var access: EntryPointAccess = EntryPointAccess()
    var args: MutableList<Parameter> = mutableListOf()
    var entryPointType: EntryPointType = EntryPointType()
    var name: String = ""
    var ret: CLType = CLType()
    companion object {

        /** This function parse the JsonObject (taken from server RPC method call) to get the EntryPoint object */
        fun fromJsonObjectToEntryPoint(from: JsonObject): EntryPoint {
            val retEntryPoint = EntryPoint()
            retEntryPoint.name = from["name"].toString()
            retEntryPoint.ret = CLType.getCLType(from["ret"] as Any)
            retEntryPoint.access = EntryPointAccess.fromJsonObjectToEntryPointAccess(from["access"] as JsonObject)
            retEntryPoint.entryPointType = EntryPointType.fromJsonToEntryPointType(from["entry_point_type"] as String)
            // Get Parameters
            val parameter = from["args"]
            if(parameter!=null) {
                val parameterList = from["args"] as JsonArray
                val totalParameter: Int = parameterList.count()-1
                if(totalParameter >= 0) {
                    for(i in 0..totalParameter) {
                        val oneP: Parameter = Parameter.fromJsonObjectToParameter(parameterList[i] as JsonObject)
                        retEntryPoint.args.add(oneP)
                    }
                }
            }
            return retEntryPoint
        }
    }
}