package com.casper.sdk.clvalue

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.values.JsonObject

class CLValue {
    var itsBytes:String = ""
    var itsParse:CLParsed = CLParsed()
    var itsCLType:CLType = CLType()
    companion object {
        fun  fromJsonObjToCLValue(fromJson:JsonObject):CLValue {
            var ret: CLValue = CLValue()
            ret.itsBytes = fromJson["bytes"].toString()
            println("Bytes is:${ret.itsBytes}")
            var clType = fromJson["cl_type"]
            ret.itsCLType = CLType.getCLType(clType as Any)
            ret.itsParse.itsCLType = ret.itsCLType
            println("About to get parsed")
            if(fromJson["parsed"].toString() != "null") {
                ret.itsParse = CLParsed.getCLParsed(fromJson["parsed"] as Any, ret.itsCLType)
            } else {
                println("Parse of type Option but got value NULL")
                var clParsed:CLParsed = CLParsed()
                clParsed.itsValueInStr = ConstValues.VALUE_NULL
                ret.itsParse = clParsed
            }
            return ret
        }
    }
}