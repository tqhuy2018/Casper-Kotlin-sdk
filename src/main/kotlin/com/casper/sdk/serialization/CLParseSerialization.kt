package com.casper.sdk.serialization

import com.casper.sdk.clvalue.CLParsed
import com.casper.sdk.clvalue.CLType

class CLParseSerialization {
    companion object {
        // Serialization for Bool parsed value
        fun serializeFromCLParseBool(clParse: CLParsed): String {
            if(clParse.itsValueInStr == "true") {
                return "01"
            }
            return "00"
        }
        // Serialization for U8 parsed value
        fun serializeFromCLParseU8(clParse: CLParsed) : String {
            return NumberSerialize.serializeForU8(clParse.itsValueInStr)
        }
        // Serialization for U32 parsed value
        fun serializeFromCLParseU32(clParse: CLParsed) : String {
            return NumberSerialize.serializeForI32(clParse.itsValueInStr)
        }
        // Serialization for U64 parsed value
        fun serializeFromCLParseU64(clParse: CLParsed) : String {
            return NumberSerialize.serializeForU64(clParse.itsValueInStr)
        }
        /**
        Serialize for CLValue of CLType U128 or U256 or U512, ingeneral the input value is called Big number
        - Parameters: value of big number  with decimal value in String format
        - Returns: Serialization for the big number, with this rule:
        - Get the hexa value from the  the decimal big number - let call it the main serialization
        - Get the length of the hexa value
        -First byte is the u8 serialization of the length, let call it prefix
        Return result = prefix + main serialization
        Special case: If input = "0" then output = "00"
        This function just call the NumberSerialize class method to get the result, the actual code is implemented in NumberSerialize class
         */
        fun  serializeFromCLParseBigNumber(clParse: CLParsed) : String {
            return NumberSerialize.serializeForBigNumber(clParse.itsValueInStr)
        }
        /**
        Serialize for CLValue of CLType Int32
        - Parameters:Int32 value in String format
        - Returns: Serialization of UInt32 if input >= 0.
        If input < 0 Serialization of UInt32.max complement to the input
        This function just call the NumberSerialize class method to get the result, the actual code is implemented in NumberSerialize class
         */
        fun  serializeFromCLParseInt32(clParse: CLParsed) :String {
            return  NumberSerialize.serializeForI32(clParse.itsValueInStr)
        }
        /**
        Serialize for CLValue of CLType Int64
        - Parameters:Int64 value in String format
        - Returns: Serialization of UInt64 if input >= 0.
        If input < 0 Serialization of UInt64.max complement to the input
        This function just call the NumberSerialize class method to get the result, the actual code is implemented in NumberSerialize class
         */
        fun  serializeFromCLParseInt64(clParse: CLParsed) :String {
            return  NumberSerialize.serializeForI64(clParse.itsValueInStr)
        }
    }
}