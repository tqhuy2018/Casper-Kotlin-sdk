package com.casper.sdk.common.classes

class U512Class {
    var itsValue:String = ""
    companion object {
        fun fromStringToU512(from:String):U512Class {
            var ret:U512Class = U512Class()
            ret.itsValue = from
            return ret
        }
    }
}