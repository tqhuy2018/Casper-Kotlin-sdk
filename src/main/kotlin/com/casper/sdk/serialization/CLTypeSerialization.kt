package com.casper.sdk.serialization

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLType

class CLTypeSerialization {

    companion object {
        fun  serializeForCLType(clType:CLType) : String {
            if (clType.itsTypeStr == ConstValues.CLTYPE_BOOL) {
                return "00"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_I32) {
                return "01"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_I64) {
                return "02"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_U8) {
                return "03"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_U32) {
                return "04"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_U64) {
                return "05"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_U128) {
                return "06"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_U256) {
                return "07"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_U512) {
                return "08"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_UNIT) {
                return "09"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_STRING) {
                return "0a"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_KEY) {
                return "0b"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_UREF) {
                return "0c"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_OPTION) {
                val innerSerialize : String = CLTypeSerialization.serializeForCLType(clType.innerCLType1)
                    return "0d" + innerSerialize
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_LIST) {
                val innerSerialize : String = CLTypeSerialization.serializeForCLType(clType.innerCLType1)
                return "0e" + innerSerialize
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_BYTEARRAY) {
                return "0f"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_RESULT) {
                val innerSerialize1 : String = CLTypeSerialization.serializeForCLType(clType.innerCLType1)
                val innerSerialize2 : String = CLTypeSerialization.serializeForCLType(clType.innerCLType2)
                return "10" + innerSerialize1 + innerSerialize2
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_MAP) {
                val innerSerialize1 : String = CLTypeSerialization.serializeForCLType(clType.innerCLType1)
                val innerSerialize2 : String = CLTypeSerialization.serializeForCLType(clType.innerCLType2)
                return "11" + innerSerialize1 + innerSerialize2
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_TUPLE1) {
                val innerSerialize : String = CLTypeSerialization.serializeForCLType(clType.innerCLType1)
                return "12" + innerSerialize
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_TUPLE2) {
                val innerSerialize1 : String = CLTypeSerialization.serializeForCLType(clType.innerCLType1)
                val innerSerialize2 : String = CLTypeSerialization.serializeForCLType(clType.innerCLType2)
                return "13" + innerSerialize1 + innerSerialize2
            }  else if (clType.itsTypeStr == ConstValues.CLTYPE_TUPLE3) {
                val innerSerialize1 : String = CLTypeSerialization.serializeForCLType(clType.innerCLType1)
                val innerSerialize2 : String = CLTypeSerialization.serializeForCLType(clType.innerCLType2)
                val innerSerialize3 : String = CLTypeSerialization.serializeForCLType(clType.innerCLType3)
                return "14" + innerSerialize1 + innerSerialize2 + innerSerialize3
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_ANY) {
                return "15"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_PUBLIC_KEY) {
                return "16"
            } else {
                return "--00--"
            }
        }
    }
}