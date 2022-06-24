package com.casper.sdk.serialization

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLParsed

class CLParseSerialization {

    companion object {
        // Serialization for Bool parsed value
        fun serializeFromCLParseBool(clParse: CLParsed): String {
            if(clParse.itsValueInStr == "true") {
                return "01"
            } else if(clParse.itsValueInStr == "false") {
                return "00"
            } else {
                return ConstValues.INVALID_VALUE
            }
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
        fun  serializeFromCLParseInt32(clParse: CLParsed) : String {
            return  NumberSerialize.serializeForI32(clParse.itsValueInStr)
        }
        /**
        Serialize for CLValue of CLType Int64
        - Parameters:Int64 value in String format
        - Returns: Serialization of UInt64 if input >= 0.
        If input < 0 Serialization of UInt64.max complement to the input
        This function just call the NumberSerialize class method to get the result, the actual code is implemented in NumberSerialize class
         */
        fun  serializeFromCLParseInt64(clParse: CLParsed) : String {
            return  NumberSerialize.serializeForI64(clParse.itsValueInStr)
        }
        // This function serialize String
        fun serializeFromCLParseString(clParse: CLParsed) : String {
            if (clParse.itsValueInStr == "") {
                return "00000000"
            }
            var ret : String
            val strToParse: String = clParse.itsValueInStr
            val strLength : UInt = strToParse.length.toUInt()
            ret = NumberSerialize.serializeForU32(strLength.toString())
            val maxForLoop : Int = (strLength - 1u).toInt()
            for(i in 0..maxForLoop) {
                val oneChar:Char = strToParse.get(i)
                val charCode:Int = oneChar.code
                val oneCharSerialize:String = NumberSerialize.serializeForU8(charCode.toString())
                ret += oneCharSerialize
            }
            return ret
        }
        //This function serialize  CLValue of type  Unit, just return empty string
        fun serializeFromCLParseUnit(clParse: CLParsed) : String {
            return  ""
        }
        // This function serialize  CLValue of type  Key
        // Rule for serialization:
        // For type of account hash: "00" + value drop the prefix "account-hash-"
        // For type hash: "01" + value drop the prefix "hash-"
        // For type URef: same like CLValue of CLType URef serialization
        /// Sample value and serialization:
        /// For URef
        /// "uref-be1dc0fd639a3255c1e3e5e2aa699df66171e40fa9450688c5d718b470e057c6-007" will has the serialization with value
        /// "be1dc0fd639a3255c1e3e5e2aa699df66171e40fa9450688c5d718b470e057c607"
        /// For Account
        /// "account-hash-d0bc9cA1353597c4004B8F881b397a89c1779004F5E547e04b57c2e7967c6269" will has the serialization with value
        /// "d0bc9cA1353597c4004B8F881b397a89c1779004F5E547e04b57c2e7967c6269"
        /// For Hash
        /// "hash-8cf5e4acf51f54eb59291599187838dc3bc234089c46fc6ca8ad17e762ae4401" will has the serialization with value
        /// "8cf5e4acf51f54eb59291599187838dc3bc234089c46fc6ca8ad17e762ae4401"
        fun serializeFromCLParseKey(clParse: CLParsed) : String {
            val keyStr : String = clParse.itsValueInStr
            if(keyStr.contains("account-hash-")) {
                val array:List<String> = keyStr.split("-")
                return  "00" + array[2]
            } else if (keyStr.contains("hash-")) {
                val array:List<String> = keyStr.split("-")
                return  "01" + array[1]
            } else if (keyStr.contains("uref-")) {
                val array:List<String> = keyStr.split("-")
                val suffix:String = array[2].substring(1,3)
                return  "02" + array[1] + suffix
            }
            return ConstValues.INVALID_VALUE
        }
        /// This function serialize  CLValue of type  URef
        /// Sample serialization for value : uref-be1dc0fd639a3255c1e3e5e2aa699df66171e40fa9450688c5d718b470e057c6-007
        /// Return result will be be1dc0fd639a3255c1e3e5e2aa699df66171e40fa9450688c5d718b470e057c607
        fun serializeFromCLParseURef(clParse: CLParsed): String {
            val keyStr : String = clParse.itsValueInStr
            if (keyStr.contains("uref-")) {
                val array:List<String> = keyStr.split("-")
                val suffix:String = array[2].substring(1,3)
                return  "02" + array[1] + suffix
            }
            return ConstValues.INVALID_VALUE
        }
        //This function serialize  CLValue of type  PublicKey, just return the PublicKey value
        fun serializeFromCLParsePublicKey(clParse: CLParsed): String {
            return clParse.itsValueInStr
        }
        //This function serialize  CLValue of type  ByteArray,, simply return the ByteArray value
        fun serializeFromCLParseByteArray(clParse:CLParsed): String {
            return clParse.itsValueInStr
        }
        ///This function serialize  CLValue of type  Option
        ///Rule for Option serialization:
        ///If the value inside the Option is Null, return "00"
        ///else return "01" + (Option.inner parse value).serialization
        fun serializeFromCLParseOption(clParse: CLParsed): String {
            if (clParse.itsValueInStr == ConstValues.VALUE_NULL) {
                return "00"
            }
            val innerParsedSerialization: String = serializeFromCLParse(clParse.innerParsed1)
            val ret: String = "01" + innerParsedSerialization
            return  ret
        }
        ///This function serialize  CLValue of type  List
        /// The rule is:
        /// If the List is empty, just return empty string ""
        /// If the List is not empty, then first take the length of the List, let say it totalElement
        /// Get the prefix as U32 Serialization for totalElement: prefix = U32.serialization(totalElement)
        /// Get through the List element from one to one, get the Serialization of each element and concatenate them
        /// Add the prefix to the concatenation from the List element serialization, that the result need to return.
        fun serializeFromCLParseList(clParse: CLParsed): String {
            val totalElement: Int = clParse.itsArrayValue.size
            if(totalElement == 0) {
                return  ""
            }
            var ret:String = NumberSerialize.serializeForU32(totalElement.toString())
            for(i in 0..totalElement-1) {
                //val oneCLParse:CLParsed = clParse.itsArrayValue.get(i)
                val oneSerialization:String = serializeFromCLParse(clParse.itsArrayValue.get(i))
                ret = ret + oneSerialization
            }
            return ret
        }
        ///This function serialize  CLValue of type  Map, the rule for serialization:
        ///If the map is empty return "00000000"
        ///else
        ///First get the size of the map, then get the U32.serialize of the map size, let call it lengthSerialization
        ///For 1 pair (key,value) the serialization is key.serialization + value.serialization
        ///map.serialization = lengthSerialization +  concatenation of all pair(key,value)
        fun serializeFromCLParseMap(clParse: CLParsed):String {
            val totalElement: Int = clParse.innerParsed1.itsArrayValue.size
            if(totalElement == 0) {
                return "00000000"
            }
            var ret:String = NumberSerialize.serializeForU32(totalElement.toString())
            for(i in 0..totalElement-1) {
                val keySerialization:String = serializeFromCLParse(clParse.innerParsed1.itsArrayValue.get(i))
                val valueSerialization:String = serializeFromCLParse(clParse.innerParsed2.itsArrayValue.get(i))
                ret = ret + keySerialization + valueSerialization
            }
            return ret
        }
        ///This function serialize  CLValue of type  Result, the rule is:
        ///If the result is Ok, then the prefix = "01"
        ///If the result is Err, then the prefix = "00"
        ///result = prefix + (inner CLParse value).serialized
        fun serializeFromCLParseResult(clParse: CLParsed): String {
            val prefixStr : String
            if(clParse.itsValueInStr == ConstValues.CLPARSE_RESULT_OK) {
                prefixStr = "01"
            } else if(clParse.itsValueInStr == ConstValues.CLPARSE_RESULT_ERR) {
                prefixStr = "00"
            } else {
                prefixStr = "ERR_RESULT"
            }
            val innerSerialize:String = serializeFromCLParse(clParse.innerParsed1)
            return prefixStr + innerSerialize
        }
        //This function serialize  CLValue of type  Tuple1, the result is the serialization of the CLParse inner value in the Tuple1
        fun serializeFromCLParseTuple1(clParse: CLParsed):String {
            return serializeFromCLParse(clParse.innerParsed1)
        }
        //This function serialize  CLValue of type  Tuple2,  the result is the concatenation of 2 inner CLParse values in the Tuple2
        fun serializeFromCLParseTuple2(clParse: CLParsed):String {
            return serializeFromCLParse(clParse.innerParsed1) + serializeFromCLParse(clParse.innerParsed2)
        }
        //This function serialize  CLValue of type  Tuple3, the result is the concatenation of 3 inner CLParse values in the Tuple3
        fun serializeFromCLParseTuple3(clParse: CLParsed):String {
            return serializeFromCLParse(clParse.innerParsed1) + serializeFromCLParse(clParse.innerParsed2) + serializeFromCLParse(clParse.innerParsed3)
        }

        ///Function for the serialization of  CLParse primitive in type with no recursive CLValue inside, such as Bool, U8, U32, I32, String, ....
        fun serializeFromCLParsePrimitive(clParse: CLParsed): String {
            if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_BOOL) {
                return serializeFromCLParseBool(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_U8) {
                return serializeFromCLParseU8(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_U32) {
                return serializeFromCLParseU32(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_U64) {
                return serializeFromCLParseU64(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_I32) {
                return serializeFromCLParseInt32(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_I64) {
                return serializeFromCLParseInt64(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_U128) {
                return serializeFromCLParseBigNumber(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_U256) {
                return serializeFromCLParseBigNumber(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_U512) {
                return serializeFromCLParseBigNumber(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_STRING) {
                return serializeFromCLParseString(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_UNIT) {
                return serializeFromCLParseUnit(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_KEY) {
                return serializeFromCLParseKey(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_UREF) {
                return serializeFromCLParseURef(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_PUBLIC_KEY) {
                return serializeFromCLParsePublicKey(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_BYTEARRAY) {
                return serializeFromCLParseByteArray(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_ANY) {
                return "NULL"
            }
            return "NONE_PRIMITIVE"
        }
        fun serializeFromCLParseCompound(clParse: CLParsed): String {
            if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_OPTION) {
                return serializeFromCLParseOption(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_LIST) {
                return serializeFromCLParseList(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_MAP) {
                return serializeFromCLParseMap(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_TUPLE1) {
                return serializeFromCLParseTuple1(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_TUPLE2) {
                return serializeFromCLParseTuple2(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_TUPLE3) {
                return serializeFromCLParseTuple3(clParse)
            } else if(clParse.itsCLType.itsTypeStr == ConstValues.CLTYPE_RESULT) {
                return serializeFromCLParseResult(clParse)
            }
            return "NONE_COMPOUND"
        }
        /** Serialize for CLParse in general
        The flow is quite simple:
        If the CLParse is of type Primitive, such as Bool, U8, U32 ... - CLParse that does not contain recursive type inside it, then call  function
        serializeFromCLParsePrimitive to get the CLParse serialization value.
        If the CLParse is of type Compound, such as List, Option, Map ... - CLParse that contains recursive type inside it, then call  function
        serializeFromCLParseCompound to get the CLParse serialization value.
         */
        fun serializeFromCLParse(clParse: CLParsed): String {
            if(clParse.itsCLType.isCLTypePrimitive()) {
                return serializeFromCLParsePrimitive(clParse)
            } else {
                return serializeFromCLParseCompound(clParse)
            }
        }
    }
}