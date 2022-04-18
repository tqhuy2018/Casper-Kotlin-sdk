package com.casper.sdk.common.classes
/**Class built for storing the  U512 number.
The value is stored in a variable call itsValue of type String
 */
class U512Class {
    var itsValue: String = ""
    companion object {
        //This function convert a string that represents the number to a U512Class
        fun fromStringToU512(from: String): U512Class {
            var ret: U512Class = U512Class()
            ret.itsValue = from
            return ret
        }
    }
}