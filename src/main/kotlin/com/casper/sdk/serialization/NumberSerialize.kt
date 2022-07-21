package com.casper.sdk.serialization
//This class do the serialization for number of all types: u8, u32, i32, i64, u128, u256, u512
class NumberSerialize {
    companion object {
        // Function for the serialization of unsigned number u8
        fun serializeForU8(valueInStr:String) : String {
            if (valueInStr == "0") {
                return "00"
            } else {
                val value = valueInStr.toUByte()
                if(value < 10u) {
                    return "0" + valueInStr
                } else {
                    val remainder : UInt = value % 16u
                    val quotient : UInt = (value - remainder) / 16u
                    val remainderStr : String = from10To16(remainder)
                    val quotientStr : String  = from10To16(quotient)
                    return  quotientStr + remainderStr
                }
            }
        }
        // Function for the serialization of unsigned number u32
        fun serializeForU32(numberInStr: String) :String {
            if (numberInStr == "0") {
                return "00000000"
            }
            var ret : String = fromDecimalStringToHexaString(numberInStr)
            val retLength : Int = ret.length
            if(retLength < 8) {
                val total0Add : Int = 8 - retLength - 1
                var prefix0  = ""
                for(i in 0 .. total0Add) {
                    prefix0 = prefix0 + "0"
                }
                ret = prefix0 + ret
            }
            val realRet : String = stringReversed2Digit(ret)
            return realRet
        }
        // Function for the serialization of unsigned number u64
        fun serializeForU64(numberInStr:String) : String {
            if (numberInStr == "0") {
                return "0000000000000000"
            }
            var ret : String = fromDecimalStringToHexaString(numberInStr)
            val retLength : Int = ret.length
            if(retLength < 16) {
                val total0Add : Int = 16 - retLength - 1
                var prefix0  = ""
                for(i in 0 .. total0Add) {
                    prefix0 = prefix0 + "0"
                }
                ret = prefix0 + ret
            }
            val realRet : String = stringReversed2Digit(ret)
            return realRet
        }
        /**
        Serialize for CLValue of CLType Int32
        - Parameters:Int32 value
        - Returns: Serialization of UInt32 if input >= 0.
        If input < 0 Serialization of UInt32.max complement to the input
         */
        fun serializeForI32(numberInStr:String) : String {
            val firstChar : String = numberInStr.substring(0,1)
            //is input is negative number
            if (firstChar == "-") {
                val lastChar = numberInStr.length
                val numberValue : UInt  = numberInStr.substring(1,lastChar).toUInt()
                val maxU32 = UInt.MAX_VALUE
                val remain : UInt = maxU32 - numberValue + 1u
                return  serializeForU32(remain.toString())
            } else {
                return  serializeForU32(numberInStr)
            }
        }
        /**
        Serialize for CLValue of CLType Int64
        - Parameters:Int64 value in String format
        - Returns: Serialization of UInt64 if input >= 0.
        If input < 0 Serialization of UInt64.max complement to the input
         */
        fun serializeForI64(numberInStr:String) : String {
            val firstChar : String = numberInStr.substring(0,1)
            //is input is negative number
            if (firstChar == "-") {
                val lastChar = numberInStr.length
                val numberValue : UInt  = numberInStr.substring(1,lastChar).toUInt()
                val maxU64 = ULong.MAX_VALUE
                val remain : ULong = maxU64 - numberValue + 1u
                return  serializeForU64(remain.toString())
            } else {
                return  serializeForU64(numberInStr)
            }
        }
        /*
        Serialize for CLValue of CLType U128 or U256 or U512, ingeneral the input value is called Big number
        - Parameters: value of big number  with decimal value in String format
        - Returns: Serialization for the big number, with this rule:
        - Get the hexa value from the  the decimal big number - let call it the main serialization
        - Get the length of the hexa value
        -First byte is the u8 serialization of the length, let call it prefix
        Return result = prefix + main serialization
        Special case: If input = "0" then output = "00"
         */
        fun serializeForBigNumber(numberInStr:String) : String {
            if (numberInStr == "0") {
                return "00"
            }
            var retStr : String = fromDecimalStringToHexaString(numberInStr)
            val retStrLength : Int = retStr.length
            val bytes : Int
            if(retStrLength % 2 == 1) {
                retStr = "0" + retStr
                 bytes = (retStrLength + 1)/2
            } else {
                bytes = retStrLength/2
            }
            val prefixLengthString : String = serializeForU8(bytes.toString())
            var realRet:String = stringReversed2Digit(retStr)
            realRet = prefixLengthString + realRet
            return realRet
        }
        fun stringReversed2Digit(fromString:String) : String {
            var retStr = ""
            var charIndex : Int = fromString.length
            while(charIndex > 0) {
                charIndex -= 2
                val sub2 : String = fromString.substring(charIndex,charIndex + 2)
                retStr += sub2
            }
            return  retStr
        }
        //This function find the quotient and remainder of a number when divide to 16
        //Input: number in string format. This function can accept input as number of very big value, such as U512 number
        //Output: a QuotientNRemainder object, which hold the quotient and remainder of the number when devide to 16
        fun findQuotientAndRemainderOfStringNumber(fromNumberInStr:String) : QuotientNRemainder {
            val retQNR  = QuotientNRemainder()
            var ret : String
            var strLength : UInt = fromNumberInStr.length.toUInt()
            var startIndex: UInt
            var remainder : UInt
            if(strLength < 2u ) {
                val value:UInt = fromNumberInStr.toUInt()
                retQNR.quotient = "0"
                retQNR.remainder = value
                return retQNR
            } else if(strLength == 2u) {
                val value:UInt = fromNumberInStr.toUInt()
                remainder = value % 16u
                val quotient : UInt = (value-remainder) / 16u
                retQNR.quotient = quotient.toString()
                retQNR.remainder = remainder
                return  retQNR
            } else { // string length >=3
                //take first 2 characters
                val first2 : String = fromNumberInStr.substring(0,2)
                val value : UInt = first2.toUInt()
                if(value < 16u) {
                    startIndex = 3u
                    val first3:String = fromNumberInStr.substring(0,3)
                    val value3 : UInt = first3.toUInt()
                    remainder = value3 % 16u
                    val quotient : UInt = (value3 - remainder) / 16u
                    ret = from10To16(quotient)
                } else {
                    startIndex = 2u
                    remainder = value % 16u
                    val quotient : UInt = (value-remainder) / 16u
                    ret = from10To16(quotient)
                }
                while(startIndex < strLength) {
                    val nextChar  = fromNumberInStr.subSequence(startIndex.toInt(),startIndex.toInt() + 1)
                    var nextValue : UInt = remainder * 10u + nextChar.toString().toUInt()
                    if (nextValue < 16u) {
                        if(startIndex + 2u <= strLength) {
                           // ret = "0" + ret
                            ret =  ret + "0"
                            val nextChar2 = fromNumberInStr.subSequence(startIndex.toInt(),startIndex.toInt() + 2)
                            nextValue = remainder * 100u + nextChar2.toString().toUInt()
                            remainder = nextValue % 16u
                            val quotient2 : UInt = (nextValue - remainder) / 16u
                            val nextCharInRet : String = from10To16(quotient2)
                            ret = ret + nextCharInRet
                            startIndex += 2u
                        } else {
                            val remainChar : UInt = strLength - startIndex
                            if(remainChar == 1u) {
                                ret = ret + "0"
                            } else if(remainChar == 2u) {
                                ret = ret + "00"
                            }
                            remainder = nextValue
                            strLength = 0u
                        }
                    } else {
                        remainder = nextValue % 16u
                        val quotient2 : UInt = (nextValue - remainder) / 16u
                        val nextCharInRet : String = from10To16(quotient2)
                        ret = ret + nextCharInRet
                        startIndex += 1u
                    }
                }
            }
            retQNR.remainder = remainder
            retQNR.quotient = ret
            return retQNR
        }
        //This function turn a number in decimal value to hexa value. The number is in String format and can be
        //very big number, such as U512
        fun fromDecimalStringToHexaString(fromNumberInStr : String) : String {
            var ret : String
            val ret1 : QuotientNRemainder = findQuotientAndRemainderOfStringNumber(fromNumberInStr)
            var numberLength : UInt = ret1.quotient.length.toUInt()
            var bigNumber : String = ret1.quotient
            var remainderStr : String = from10To16(ret1.remainder)
            ret = remainderStr
            var lastQuotient = ""
            if(numberLength < 2u) {
                lastQuotient = ret1.quotient
            }
            while(numberLength >= 2u) {
                val retN : QuotientNRemainder = findQuotientAndRemainderOfStringNumber(bigNumber)
                numberLength = retN.quotient.length.toUInt()
                bigNumber = retN.quotient
                remainderStr = from10To16(retN.remainder)
                ret = ret + remainderStr
                lastQuotient  = retN.quotient
            }
            if(lastQuotient == "0") {
            } else {
                ret = ret + lastQuotient
            }
            val realRet:String = stringReversed(ret)
            return realRet
        }
        //This function reverse a string, used to build the serialization of number for Casper.
        //For example the number in hexa format 0ca532 when do this function will turn to 235ac0
        fun stringReversed(fromString:String) : String {
            var ret = ""
            var charIndex : Int = fromString.length
            while(charIndex > 0) {
                charIndex --
                ret = ret + fromString.substring(charIndex,charIndex + 1)
            }
            return ret
        }
        //This function turn the number in decimal to hexa. The input number is the decimal format, with value from 0 to 15
        fun from10To16(number:UInt):String {
            if(number < 10u) {
                return number.toString()
            } else if (number == 10u) {
                return "a"
            } else if (number == 11u) {
                return "b"
            } else if (number == 12u) {
                return "c"
            } else if (number == 13u) {
                return "d"
            } else if (number == 14u) {
                return "e"
            } else if (number == 15u){
                return "f"
            } else {
                return "--0000---"
            }
        }
    }
}