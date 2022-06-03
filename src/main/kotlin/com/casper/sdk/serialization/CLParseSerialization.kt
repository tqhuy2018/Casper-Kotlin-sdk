package com.casper.sdk.serialization

import com.casper.sdk.clvalue.CLParsed
import com.casper.sdk.clvalue.CLType

class CLParseSerialization {
    companion object {
        fun serializeForCLType(clType: CLParsed): String {
            return "--00--"
        }
    }
}