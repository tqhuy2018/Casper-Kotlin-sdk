package com.casper.sdk.serialization

class NumberSerialize {
    companion object {
        fun u8Serialize(valueInStr:String) : String {
            if (valueInStr == "0") {
                return "00"
            } else {
                val value = valueInStr.toUByte()
                if(value < 10u) {
                    return "0" + valueInStr
                } else {
                    val remainder : UInt = value % 16u
                    val quotient : UInt = (value - remainder) / 16u
                    val remainderStr : String = NumberSerialize.from10To16(remainder)
                    val quotientStr : String  = NumberSerialize.from10To16(quotient)
                    return remainderStr + quotientStr
                }
            }
        }
        fun u32Serialize(valueInStr: String) :String {
            if (valueInStr == "0") {
                return "00000000"
            }
            return ""
        }
        fun fromDecimalToHexa(number:String) : String {
            return ""
        }
        fun findQuotientAndRemainderOfStringNumber(fromNumberInStr:String) : QuotientNRemainder {
            var retQNR : QuotientNRemainder = QuotientNRemainder()
            var ret : String = ""
            var strLength : UInt = fromNumberInStr.length.toUInt()
            var startIndex: UInt = 0u
            var remainder : UInt = 0u
            if(strLength < 2u ) {
                val value:UInt = fromNumberInStr.toUInt()
                retQNR.quotient = "0"
                retQNR.remainder = value
            } else if(strLength == 2u) {
                val value:UInt = fromNumberInStr.toUInt()
                remainder = value % 16u
                val quotient : UInt = (value-remainder) / 16u
                retQNR.quotient = quotient.toString()
                retQNR.remainder = remainder
            } else { // string length >=3
                //take first 2 characters
                startIndex = 2u
                val first2 : String = fromNumberInStr.substring(0,2)
                val value : UInt = first2.toUInt()
                if(value < 16u) {
                    startIndex = 3u
                    val first3:String = fromNumberInStr.substring(0,3)
                    val value3 : UInt = first3.toUInt()
                    remainder = value3 % 16u
                    val quotient : UInt = (value3 - remainder) / 16u
                    ret = NumberSerialize.from10To16(quotient)
                } else {
                    remainder = value % 16u
                    val quotient : UInt = (value-remainder) / 16u
                    ret = NumberSerialize.from10To16(quotient)
                }
                while(startIndex < strLength) {
                    val nextChar  = fromNumberInStr.substring(startIndex.toInt(),1)
                    var nextValue : UInt = remainder * 10u + nextChar.toUInt()
                    if (nextValue < 16u) {
                        if(startIndex + 2u < strLength) {
                            ret = "0" + ret
                            val nextChar2 = fromNumberInStr.substring(startIndex.toInt(),2)
                            nextValue = remainder * 100u + nextChar2.toUInt()
                            remainder = nextValue % 16u
                            val quotient2 : UInt = (nextValue - remainder) / 16u
                            val nextCharInRet : String = NumberSerialize.from10To16(quotient2)
                            ret = ret + nextCharInRet
                            startIndex += 2u
                        } else {
                            val remainChar : UInt = strLength - startIndex
                            if(remainder == 1u) {
                                ret = "0" + ret
                            } else {
                                ret = "00" + ret
                            }
                            remainder = nextValue
                            strLength = 0u
                        }
                    } else {
                        remainder = nextValue % 16u
                        val quotient2 : UInt = (nextValue - remainder) / 16u
                        val nextCharInRet : String = NumberSerialize.from10To16(quotient2)
                        ret = ret + nextCharInRet
                        startIndex += 1u
                    }
                }
            }
            retQNR.remainder = remainder
            retQNR.quotient = ret
            return retQNR
        }
        fun fromDecimalStringToHexaString(fromNumberInStr : String) : String {
            var ret : String = ""
            val ret1 : QuotientNRemainder = NumberSerialize.findQuotientAndRemainderOfStringNumber(fromNumberInStr)
            var numberLength : UInt = ret1.quotient.length.toUInt()
            var bigNumber : String = ret1.quotient
            var remainderStr : String = NumberSerialize.from10To16(ret1.remainder)
            ret = remainderStr
            var lastQuotient = ""
            if(numberLength < 2u) {
                lastQuotient = ret1.quotient
            }
            while(numberLength >= 2u) {
                val retN : QuotientNRemainder = NumberSerialize.findQuotientAndRemainderOfStringNumber(bigNumber)
                numberLength = retN.quotient.length.toUInt()
                bigNumber = retN.quotient
                remainderStr = NumberSerialize.from10To16(retN.remainder)
                ret = ret + remainderStr
                lastQuotient  = retN.quotient
            }
            if(lastQuotient == "0") {
            } else {
                ret = ret + lastQuotient
            }
            val realRet:String = NumberSerialize.stringReversed(ret)
            return realRet
        }
        fun stringReversed(fromString:String) : String {
            var ret:String = ""
            var charIndex : Int = fromString.length
            while(charIndex > 0) {
                charIndex --
                ret = ret + fromString.substring(charIndex,1)
            }
            return ret
        }
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