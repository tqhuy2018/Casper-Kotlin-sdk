package com.casper.sdk.clvalue

import com.casper.sdk.ConstValues
import com.casper.sdk.serialization.CLParseSerialization
import net.jemzart.jsonkraken.values.JsonObject
/**Class built for storing the  CLValue object.
Information of a sample CLValue object
{
"bytes": "0400e1f505"
"parsed": "100000000"
"cl_type": "U512"
}
 */
class CLValue {
    var itsBytes: String = ""
    var itsParse: CLParsed = CLParsed()
    var itsCLType: CLType = CLType()
    companion object {
        /*This function generate the Json String represent the CLValue, built for account_put_deploy call
        The Json string is somehow like this
        {
        "bytes":"3bf9765881010000"
        "parsed":1655046601019
        "cl_type":"U64"
        }
        */
        fun toJsonString(clValue:CLValue) : String{
            val ret : String
            var clTypeStr : String = CLType.toJsonString(clValue.itsCLType)
            var clParsedStr: String = CLParsed.toJsonString(clValue.itsParse)
            if (clParsedStr.contains(ConstValues.PARSED_FIXED_STRING)) {
                clParsedStr = clParsedStr.replace(ConstValues.PARSED_FIXED_STRING + ":","")
            }
            var bytesStr:String = CLParseSerialization.serializeFromCLParse(clValue.itsParse)
            bytesStr = "\"bytes\": \"" + bytesStr + "\""
            clTypeStr = "\"cl_type\":" + clTypeStr
            clParsedStr = "\"parsed\":" + clParsedStr
            ret = "{" + bytesStr + "," + clTypeStr + "," + clParsedStr + "}"
            return ret
        }
        //Generate the CLValue object  from the JsonObject
        fun  fromJsonObjToCLValue(fromJson: JsonObject): CLValue {
            val ret = CLValue()
            ret.itsBytes = fromJson["bytes"].toString()
            val clType = fromJson["cl_type"]
            ret.itsCLType = CLType.getCLType(clType as Any)
            ret.itsParse.itsCLType = ret.itsCLType
            if(fromJson["parsed"].toString() != "null") {
                ret.itsParse = CLParsed.getCLParsed(fromJson["parsed"] as Any,  ret.itsCLType)
            } else {
                val clParsed = CLParsed()
                clParsed.itsValueInStr = ConstValues.VALUE_NULL
                ret.itsParse = clParsed
            }
            return ret
        }
    }
}