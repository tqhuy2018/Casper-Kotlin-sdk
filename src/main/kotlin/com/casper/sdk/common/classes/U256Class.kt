package com.casper.sdk.common.classes

class U256Class {
    var itsValue:String = ""
    companion object {
        fun fromStringToU256(from:String):U256Class {
            var ret:U256Class = U256Class()
            ret.itsValue = from
            return ret
        }
    }
}