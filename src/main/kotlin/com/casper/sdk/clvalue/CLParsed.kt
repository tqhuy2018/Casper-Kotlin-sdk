package com.casper.sdk.clvalue

import net.jemzart.jsonkraken.values.JsonObject

class CLParsed {
    var itsValueInStr:String = ""
    var itsCLTypeInStr:String = ""
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
    companion object {
        fun  fromObjToCLParsed(from: JsonObject):CLParsed {
            var ret:CLParsed = CLParsed()
            return ret
        }
    }
}