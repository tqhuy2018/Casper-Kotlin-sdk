package com.casper.sdk.common.classes
/**Class built for storing the  U128 number.
 The value is stored in a variable call itsValue of type String
 */
class U128Class {
    var itsValue: String = ""
    companion object {
        //This function convert a string that represents the number to a U128Class
        fun fromStringToU128(from: String): U128Class {
            var ret: U128Class = U128Class()
            ret.itsValue = from
            return ret
        }
    }
}