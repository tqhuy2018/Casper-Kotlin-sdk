package com.casper.sdk.storedvalue

import com.casper.sdk.clvalue.CLType
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class EntryPoint {
    var access:EntryPointAccess = EntryPointAccess()
    var args:MutableList<Parameter> = mutableListOf()
    var entryPointType:EntryPointType = EntryPointType()
    var name:String = ""
    var ret:CLType = CLType()
    companion object {
        fun fromJsonObjectToEntryPoint(from:JsonObject):EntryPoint {
            var retEntryPoint:EntryPoint = EntryPoint()
            retEntryPoint.name = from["name"].toString()
            retEntryPoint.ret = CLType.getCLType(from["ret"] as Any)
            // Get Parameters
            val parameter = from["args"]
            if(parameter!=null) {
                val parameterList = from["args"] as JsonArray
                val totalParameter:Int = parameterList.count()-1
                if(totalParameter >= 0) {
                    for(i in 0..totalParameter) {
                        val oneP:Parameter = Parameter.fromJsonObjectToParameter(parameterList[i] as JsonObject)
                        retEntryPoint.args.add(oneP)
                    }
                }
            }
            //retEntryPoint.entryPointType = EntryPointType.fromJsonToEntryPointType(from)
            return retEntryPoint
        }
    }
}