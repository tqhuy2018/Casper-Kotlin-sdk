package com.casper.sdk.clvalue

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.values.JsonObject

class CLType {
    var itsTypeStr:String = ""
    var itsInnerType:MutableList<CLType> = mutableListOf()
    var isPrimitive:Boolean = false

    fun isCLTypePrimitive() :Boolean{
        when(itsTypeStr) {
            ConstValues.CLTYPE_BOOL -> return true
            ConstValues.CLTYPE_I32 -> return true
            ConstValues.CLTYPE_I64 -> return true
            ConstValues.CLTYPE_U8 -> return true
            ConstValues.CLTYPE_U32 -> return true
            ConstValues.CLTYPE_U64 -> return true
            ConstValues.CLTYPE_U128 -> return true
            ConstValues.CLTYPE_U256 -> return true
            ConstValues.CLTYPE_U512 -> return true
            ConstValues.CLTYPE_UNIT -> return true
            ConstValues.CLTYPE_STRING -> return true
            ConstValues.CLTYPE_KEY -> return true
            ConstValues.CLTYPE_UREF -> return true
            ConstValues.CLTYPE_PUBLIC_KEY -> return true
            ConstValues.CLTYPE_BYTEARRAY -> return true
            ConstValues.CLTYPE_ANY -> return true
        }
        return false
    }
    companion object {
        fun fromStringToCLType(from:String):CLType {
            var ret:CLType = CLType()
            if (from == ConstValues.CLTYPE_BOOL) {
                ret.itsTypeStr = ConstValues.CLTYPE_BOOL
                ret.isPrimitive = true
            }  else  if (from == ConstValues.CLTYPE_I32) {
                ret.itsTypeStr = ConstValues.CLTYPE_I32
                ret.isPrimitive = true
            }  else  if (from == ConstValues.CLTYPE_I64) {
                ret.itsTypeStr = ConstValues.CLTYPE_I64
                ret.isPrimitive = true
            } else  if (from == ConstValues.CLTYPE_U8) {
                ret.itsTypeStr = ConstValues.CLTYPE_U8
                ret.isPrimitive = true
            }  else  if (from == ConstValues.CLTYPE_U32) {
                ret.itsTypeStr = ConstValues.CLTYPE_U32
                ret.isPrimitive = true
            }  else  if (from == ConstValues.CLTYPE_U64) {
                ret.itsTypeStr = ConstValues.CLTYPE_U64
                ret.isPrimitive = true
            }  else  if (from == ConstValues.CLTYPE_U128) {
                ret.itsTypeStr = ConstValues.CLTYPE_U128
                ret.isPrimitive = true
            }  else  if (from == ConstValues.CLTYPE_U256) {
                ret.itsTypeStr = ConstValues.CLTYPE_U256
                ret.isPrimitive = true
            }  else  if (from == ConstValues.CLTYPE_U512) {
                println("CLTYPE OF TYPE U512")
                ret.itsTypeStr = ConstValues.CLTYPE_U512
                ret.isPrimitive = true
            }  else  if (from == ConstValues.CLTYPE_UNIT) {
                ret.itsTypeStr = ConstValues.CLTYPE_UNIT
                ret.isPrimitive = true
            } else if (from == ConstValues.CLTYPE_UREF) {
                ret.itsTypeStr = ConstValues.CLTYPE_UREF
                ret.isPrimitive = true
            } else if (from == ConstValues.CLTYPE_PUBLIC_KEY) {
                ret.itsTypeStr = ConstValues.CLTYPE_PUBLIC_KEY
                ret.isPrimitive = true
            } else if (from == ConstValues.CLTYPE_BYTEARRAY) {
                ret.itsTypeStr = ConstValues.CLTYPE_BYTEARRAY
                ret.isPrimitive = true
            } else if (from == ConstValues.CLTYPE_ANY) {
                ret.itsTypeStr = ConstValues.CLTYPE_ANY
                ret.isPrimitive = true
            } else if (from == ConstValues.CLTYPE_KEY) {
                ret.itsTypeStr = ConstValues.CLTYPE_KEY
                ret.isPrimitive = true
            }
            return ret
        }
        fun fromJSonToCLType(from:JsonObject):CLType {
            var ret:CLType = CLType()
            from[ConstValues.CLTYPE_OPTION] ?.let  {
                println("Of type Option")
                ret.itsTypeStr = ConstValues.CLTYPE_OPTION
                ret.isPrimitive = false
                return ret
            }
            println("Of type compound not option")
            println("Done")
            return ret
        }
    }
}