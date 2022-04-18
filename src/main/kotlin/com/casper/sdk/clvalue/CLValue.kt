package com.casper.sdk.clvalue

import com.casper.sdk.ConstValues
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
        //Generate the CLValue object  from the JsonObject
        fun  fromJsonObjToCLValue(fromJson: JsonObject): CLValue {
            var ret:  CLValue = CLValue()
            ret.itsBytes = fromJson["bytes"].toString()
            var clType = fromJson["cl_type"]
            ret.itsCLType = CLType.getCLType(clType as Any)
            ret.itsParse.itsCLType = ret.itsCLType
            if(fromJson["parsed"].toString() != "null") {
                ret.itsParse = CLParsed.getCLParsed(fromJson["parsed"] as Any,  ret.itsCLType)
            } else {
                var clParsed: CLParsed = CLParsed()
                clParsed.itsValueInStr = ConstValues.VALUE_NULL
                ret.itsParse = clParsed
            }
            return ret
        }
    }
}