package com.casper.sdk.clvalue

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.values.JsonObject

class CLType {
    var itsTypeStr:String = ""
    lateinit var innerCLType1:CLType
    lateinit var innerCLType2:CLType
    lateinit var innerCLType3: CLType
    var itsInnerType:MutableList<CLType> = mutableListOf()
    //var isPrimitive:Boolean = false

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
        fun getCLType(from:Any):CLType {
            var ret:CLType = CLType()
            if (from is String) {
                ret = CLType.getCLTypePrimitive(from as String)
            } else {
                ret = CLType.getCLTypeCompound(from as JsonObject)
            }
            return ret
        }
        fun getCLTypePrimitive(from:String):CLType {
            var ret:CLType = CLType()
            if (from == ConstValues.CLTYPE_BOOL) {
                ret.itsTypeStr = ConstValues.CLTYPE_BOOL
            }  else  if (from == ConstValues.CLTYPE_I32) {
                ret.itsTypeStr = ConstValues.CLTYPE_I32
            }  else  if (from == ConstValues.CLTYPE_I64) {
                ret.itsTypeStr = ConstValues.CLTYPE_I64
            } else  if (from == ConstValues.CLTYPE_U8) {
                ret.itsTypeStr = ConstValues.CLTYPE_U8
            }  else  if (from == ConstValues.CLTYPE_U32) {
                ret.itsTypeStr = ConstValues.CLTYPE_U32
            }  else  if (from == ConstValues.CLTYPE_U64) {
                ret.itsTypeStr = ConstValues.CLTYPE_U64
            }  else  if (from == ConstValues.CLTYPE_U128) {
                ret.itsTypeStr = ConstValues.CLTYPE_U128
            }  else  if (from == ConstValues.CLTYPE_U256) {
                ret.itsTypeStr = ConstValues.CLTYPE_U256
            }  else  if (from == ConstValues.CLTYPE_U512) {
                ret.itsTypeStr = ConstValues.CLTYPE_U512
            }  else  if (from == ConstValues.CLTYPE_STRING) {
                ret.itsTypeStr = ConstValues.CLTYPE_STRING
            } else  if (from == ConstValues.CLTYPE_UNIT) {
                ret.itsTypeStr = ConstValues.CLTYPE_UNIT
            } else if (from == ConstValues.CLTYPE_UREF) {
                ret.itsTypeStr = ConstValues.CLTYPE_UREF
            } else if (from == ConstValues.CLTYPE_PUBLIC_KEY) {
                ret.itsTypeStr = ConstValues.CLTYPE_PUBLIC_KEY
            } else if (from == ConstValues.CLTYPE_BYTEARRAY) {
                ret.itsTypeStr = ConstValues.CLTYPE_BYTEARRAY
            } else if (from == ConstValues.CLTYPE_ANY) {
                ret.itsTypeStr = ConstValues.CLTYPE_ANY
            } else if (from == ConstValues.CLTYPE_KEY) {
                ret.itsTypeStr = ConstValues.CLTYPE_KEY
            }
            return ret
        }
        fun getCLTypeCompound(from:JsonObject):CLType {
            var ret:CLType = CLType()
            from[ConstValues.CLTYPE_OPTION] ?.let  {
                println("Of type Option")
                ret.itsTypeStr = ConstValues.CLTYPE_OPTION
                ret.innerCLType1 = CLType();
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_OPTION] as Any)
                println("Option done clType")
                return ret
            }
            from[ConstValues.CLTYPE_LIST] ?.let  {
                println("Of type List")
                ret.itsTypeStr = ConstValues.CLTYPE_LIST
                ret.innerCLType1 = CLType()
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_LIST] as Any)
                println("List done clType")
                return ret
            }
            from[ConstValues.CLTYPE_MAP] ?.let  {
                println("Of type Map")
                ret.itsTypeStr = ConstValues.CLTYPE_MAP
                //clParse for key
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_MAP].get("key") as Any)
                //clParse for value
                ret.innerCLType2 = CLType.getCLType(from[ConstValues.CLTYPE_MAP].get("value") as Any)
                println("List done clType")
                return ret
            }
            from[ConstValues.CLTYPE_RESULT] ?.let  {
                println("Of type Result")
                ret.itsTypeStr = ConstValues.CLTYPE_RESULT
                //clParse for ok
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_RESULT].get("ok") as Any)
                //clParse for err
                ret.innerCLType2 = CLType.getCLType(from[ConstValues.CLTYPE_RESULT].get("err") as Any)
                println("List done clType")
                return ret
            }
            from[ConstValues.CLTYPE_BYTEARRAY] ?.let {
                println("Of type ByteArray")
                ret.itsTypeStr = ConstValues.CLTYPE_BYTEARRAY
                return ret
            }
            from[ConstValues.CLTYPE_TUPLE1] ?.let  {
                println("Of type Tuple2")
                ret.itsTypeStr = ConstValues.CLTYPE_TUPLE1
                //clParse for tuple 1
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_TUPLE1][0] as Any)
                println("Tuple1 type:${ret.innerCLType1.itsTypeStr}")
                return ret
            }
            from[ConstValues.CLTYPE_TUPLE2] ?.let  {
                println("Of type Tuple2")
                ret.itsTypeStr = ConstValues.CLTYPE_TUPLE2
                //clParse for tuple 1
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_TUPLE2][0] as Any)
                println("Tuple1 type:${ret.innerCLType1.itsTypeStr}")
                //clParse for tuple 2
                ret.innerCLType2 = CLType.getCLType(from[ConstValues.CLTYPE_TUPLE2][1] as Any)
                println("Tuple2 type:${ret.innerCLType2.itsTypeStr}")
                println("Tuple2 done clType")
                return ret
            }
            from[ConstValues.CLTYPE_TUPLE3] ?.let  {
                println("Of type Tuple3")
                ret.itsTypeStr = ConstValues.CLTYPE_TUPLE3
                //clParse for tuple 1
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_TUPLE3][0] as Any)
                println("Tuple1 type:${ret.innerCLType1.itsTypeStr}")
                //clParse for tuple 2
                ret.innerCLType2 = CLType.getCLType(from[ConstValues.CLTYPE_TUPLE3][1] as Any)
                println("Tuple2 type:${ret.innerCLType2.itsTypeStr}")
                //clParse for tuple 3
                ret.innerCLType3 = CLType.getCLType(from[ConstValues.CLTYPE_TUPLE3][2] as Any)
                println("Tuple3 type:${ret.innerCLType3.itsTypeStr}")
                println("Tuple3 done clType")
                return ret
            }
            println("Of type compound not option")
            println("Done")
            return ret
        }
    }
}