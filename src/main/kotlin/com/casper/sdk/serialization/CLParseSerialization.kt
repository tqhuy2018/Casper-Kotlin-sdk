package com.casper.sdk.serialization

import com.casper.sdk.clvalue.CLParsed
import com.casper.sdk.clvalue.CLType

class CLParseSerialization {
    companion object {
        fun boolCLParseSerialize(clParse: CLParsed): String {
            if(clParse.itsValueInStr == "true") {
                return "01"
            }
            return "00"
        }
        fun u8CLParseSerialize(clParse: CLParsed) : String {
            return ""
        }
        fun i32CLParseSerialize(clParse: CLParsed) : String {
            return ""
        }
    }
}