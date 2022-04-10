package com.casper.sdk.common.classes

class U128Class {
    var itsValue:String = ""
    companion object {
        fun fromStringToU128(from:String):U128Class {
            var ret:U128Class = U128Class()
            ret.itsValue = from
            return ret
        }
    }
}