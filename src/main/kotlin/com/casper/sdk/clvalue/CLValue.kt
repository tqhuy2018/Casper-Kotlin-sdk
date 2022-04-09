package com.casper.sdk.clvalue

import net.jemzart.jsonkraken.values.JsonObject

class CLValue {
    var itsBytes:String = ""
    var itsParse:CLParsed = CLParsed()
    var itsCLType:CLType = CLType()
    companion object {
        fun  fromJsonObjToCLValue(fromJson:JsonObject):CLValue {
            var ret: CLValue = CLValue()
            ret.itsBytes = fromJson["bytes"].toString()
            println("\nBytes is:${ret.itsBytes}")
            var clType = fromJson["cl_type"]
            if (clType is String) {
                print("Of type primitive")
                ret.itsCLType = CLType.fromStringToCLType(clType)
            } else {
                ret.itsCLType = CLType.fromJSonToCLType(fromJson["cl_type"] as JsonObject)
            }
            ret.itsParse.itsCLType = ret.itsCLType
           // ret.itsParse = CLParsed.fromObjToCLParsed(fromJson["parsed"] as Any, ret.itsCLType)
            return ret
        }
    }
}