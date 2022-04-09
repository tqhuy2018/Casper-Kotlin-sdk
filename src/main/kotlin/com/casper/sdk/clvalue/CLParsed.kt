package com.casper.sdk.clvalue

class CLParsed {
    var itsValueInStr:String = ""
    var itsCLTypeInStr:String = ""
    var itsCLType:CLType = CLType()
    //InnerValue to hold value for the following type:
    //Option, Result, Tuple1 will take only innerValue1
    //Map, Tuple2 will take 2 inner values
    //Tuple3 will take 3 inner values
    var innerValue1:CLParsed = CLParsed()
    var innerValue2:CLParsed = CLParsed()
    var innerValue3:CLParsed = CLParsed()
    var isPrimitive:Boolean = true
    //This property is for holding array value of List and FixList
    var itsValue:MutableList<CLParsed> = mutableListOf()
    fun isPrimitiveType():Boolean {
        return false
    }
}