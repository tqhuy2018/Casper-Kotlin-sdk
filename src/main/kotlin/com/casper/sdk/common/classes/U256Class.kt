package com.casper.sdk.common.classes
/**Class built for storing the  U256 number.
The value is stored in a variable call itsValue of type String
 */
class U256Class {
    var itsValue: String = ""
    companion object {
        //This function convert a string that represents the number to a U256Class
        fun fromStringToU256(from: String): U256Class {
            val ret: U256Class = U256Class()
            ret.itsValue = from
            return ret
        }
    }
}