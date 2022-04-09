package com.casper.sdk.clvalue

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

class CLParsed {
    var itsValueInStr:String = ""
    var itsCLType:CLType = CLType()
    //itsValue to hold value for the following type:
    //Option, Result, Tuple1 will take only 1 item of CLParse
    //Map, Tuple2 will take 2  item of CLParsed
    //Tuple3 will take 3 item of CLParsed
    lateinit var innerType1:CLParsed;
    //This property is for holding array value of List and FixList
    var itsValue:MutableList<CLParsed> = mutableListOf()

    companion object
    {
        fun fromObjToCLParsedPrimitive(from:Any,withCLType: CLType):CLParsed {
            println("\nGet Parse")
            var ret:CLParsed = CLParsed()
            ret.itsCLType = withCLType
            if (withCLType.itsTypeStr == ConstValues.CLTYPE_U512) {
                ret.itsValueInStr = from as String
                println("Parsed value U512 is:${ret.itsValueInStr}")
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U128) {
                ret.itsValueInStr = from as String
                println("Parsed value U128 is:${ret.itsValueInStr}")
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U256) {
                ret.itsValueInStr = from as String
                println("Parsed value U256 is:${ret.itsValueInStr}")
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U64) {
                ret.itsValueInStr = from.toString()
                println("Parsed value U64 is:${ret.itsValueInStr}")
            }  else if (withCLType.itsTypeStr == ConstValues.CLTYPE_STRING) {
                ret.itsValueInStr = from as String
                println("Parsed value String is:${ret.itsValueInStr}")
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_KEY) {
                println("Get parse of type Key")
                var keyValuePair:JsonObject = from as JsonObject
                if (from.get("Hash").toJsonString() != "null") {
                    ret.itsValueInStr = from.get("Hash") as String
                    println("Parsed value Key Hash is:${ret.itsValueInStr}")
                } else  if (from.get("Account").toJsonString() != "null") {
                    ret.itsValueInStr = from.get("Account") as String
                    println("Parsed value Key Account is:${ret.itsValueInStr}")
                }
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_STRING) {
                ret.itsValueInStr = from as String
                println("Parsed value String is:${ret.itsValueInStr}")
            } else {
                println("Of differnet type")
            }
            return ret
        }
        fun fromObjToCLParsedCompound(from: Any, withCLType: CLType):CLParsed {
            var ret: CLParsed = CLParsed()

            return ret;
        }
        fun fromObjToCLParsed(from: Any, withCLType: CLType):CLParsed {
           if ( withCLType.isCLTypePrimitive()) {
               return CLParsed.fromObjToCLParsedPrimitive(from,withCLType);
           } else {
               return CLParsed.fromObjToCLParsedCompound(from,withCLType);
           }
        }
    }

}