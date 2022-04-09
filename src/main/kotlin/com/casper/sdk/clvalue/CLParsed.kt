package com.casper.sdk.clvalue

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.values.JsonObject

class CLParsed {
    var itsValueInStr:String = ""
    var itsCLType:CLType = CLType()
    var isPrimitive:Boolean = true
    //itsValue to hold value for the following type:
    //Option, Result, Tuple1 will take only 1 item of CLParse
    //Map, Tuple2 will take 2  item of CLParsed
    //Tuple3 will take 3 item of CLParsed
    //This property is for holding array value of List and FixList
    var itsValue:MutableList<CLParsed> = mutableListOf()
    fun isPrimitiveType():Boolean {
        return false
    }
    companion object
    {
        fun  fromObjToCLParsed(from: Any, withCLType: CLType):CLParsed {
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
            }
            return ret
        }
    }

}