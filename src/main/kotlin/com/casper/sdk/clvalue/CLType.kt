package com.casper.sdk.clvalue

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.values.JsonObject
/**Class built for storing the cl_type value of a CLValue object.
For example take this CLValue object
{
"bytes": "0400e1f505"
"parsed": "100000000"
"cl_type": "U512"
}
Then the CLType will hold the value of U512.
There are some more attributes in the object to store more information in its value,  used to build   recursived CLType,  such as List,  Map,  Tuple,  Result,  Option
 */
class CLType {
    //Type of the CLType in String,  can be Bool,  String,  I32,  I64,  List,  Map, ...
    var itsTypeStr: String = ""
    //innerCLType to hold value for the following type: 
    //Option,  Result,  Tuple1 will take only 1 item:  innerCLType1
    //Map,  Tuple2 will take 2  item:  innerCLType1, innerCLType2
    //Tuple3 will take 3 item:  innerCLType1,  innerCLType2,  innerCLType3
    lateinit var innerCLType1: CLType
    lateinit var innerCLType2: CLType
    lateinit var innerCLType3:  CLType
    //var itsInnerType: MutableList<CLType> = mutableListOf()
   //Check if the  CLType is primitive,  type that has no recursive CLType inside (such as bool,  i32,  i64,  u8,  u32,  u64,  u128,  u266,  u512,  string,  unit,  publickey,  key,  ...)
    fun isCLTypePrimitive() : Boolean{
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
        //Generate the CLType object  from the JSON object
        fun getCLType(from: Any): CLType {
            if (from is String) {
                return CLType.getCLTypePrimitive(from)
            } else {
                return CLType.getCLTypeCompound(from as JsonObject)
            }
        }
        //Generate the CLType object (of type primitive (such as bool,  i32,  i64,  u8,  u32,  u64,  u128,  u266,  u512,  string,  unit,  publickey,  key,  ...)  from the JSON object
        fun getCLTypePrimitive(from: String): CLType {
            val ret: CLType = CLType()
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
        //Generate the CLType object  of type compound (type with recursive CLValue inside its body,  such as List,  Map,  Tuple ,  Result , Option...)  from the JSON object
        fun getCLTypeCompound(from: JsonObject): CLType {
            val ret: CLType = CLType()
            from[ConstValues.CLTYPE_OPTION] ?.let  {
                ret.itsTypeStr = ConstValues.CLTYPE_OPTION
                ret.innerCLType1 = CLType()
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_OPTION] as Any)
                return ret
            }
            from[ConstValues.CLTYPE_LIST] ?.let  {
                ret.itsTypeStr = ConstValues.CLTYPE_LIST
                ret.innerCLType1 = CLType()
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_LIST] as Any)
                return ret
            }
            from[ConstValues.CLTYPE_MAP] ?.let  {
                ret.itsTypeStr = ConstValues.CLTYPE_MAP
                //clParse for key
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_MAP].get("key") as Any)
                //clParse for value
                ret.innerCLType2 = CLType.getCLType(from[ConstValues.CLTYPE_MAP].get("value") as Any)
                return ret
            }
            from[ConstValues.CLTYPE_RESULT] ?.let  {
                ret.itsTypeStr = ConstValues.CLTYPE_RESULT
                //clParse for ok
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_RESULT].get("ok") as Any)
                //clParse for err
                ret.innerCLType2 = CLType.getCLType(from[ConstValues.CLTYPE_RESULT].get("err") as Any)
                return ret
            }
            from[ConstValues.CLTYPE_BYTEARRAY] ?.let {
                ret.itsTypeStr = ConstValues.CLTYPE_BYTEARRAY
                return ret
            }
            from[ConstValues.CLTYPE_TUPLE1] ?.let  {
                ret.itsTypeStr = ConstValues.CLTYPE_TUPLE1
                //clParse for tuple 1
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_TUPLE1][0] as Any)
                 return ret
            }
            from[ConstValues.CLTYPE_TUPLE2] ?.let  {
                 ret.itsTypeStr = ConstValues.CLTYPE_TUPLE2
                //clParse for tuple 1
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_TUPLE2][0] as Any)
                //clParse for tuple 2
                ret.innerCLType2 = CLType.getCLType(from[ConstValues.CLTYPE_TUPLE2][1] as Any)
                return ret
            }
            from[ConstValues.CLTYPE_TUPLE3] ?.let  {
                ret.itsTypeStr = ConstValues.CLTYPE_TUPLE3
                //clParse for tuple 1
                ret.innerCLType1 = CLType.getCLType(from[ConstValues.CLTYPE_TUPLE3][0] as Any)
                //clParse for tuple 2
                ret.innerCLType2 = CLType.getCLType(from[ConstValues.CLTYPE_TUPLE3][1] as Any)
                //clParse for tuple 3
                ret.innerCLType3 = CLType.getCLType(from[ConstValues.CLTYPE_TUPLE3][2] as Any)
                return ret
            }
            return ret
        }
    }
}