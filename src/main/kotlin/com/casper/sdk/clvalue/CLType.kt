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
        /*This function generate the Json String represent the CLType, built for account_put_deploy call
       The Json string is somehow like this
        {
            "Map":{
                "key":"String"
                "value":"String"
            }
        }
        or
        "U512"
       */
        fun toJsonString(clType: CLType) :String{
            if(clType.isCLTypePrimitive()) {
                return "\"" + clTypePrimitiveToJsonString(clType) + "\""
            } else {
                return clTypeCompoundToJsonString(clType)
            }
        }
        /*This function generate the Json String represent the CLType primitive, such as Bool, I32, I64 , U32 , ...
        * CLType that does not contain recursive declaration inside its body*/
        fun clTypePrimitiveToJsonString(clType: CLType) : String {
            if (clType.itsTypeStr == ConstValues.CLTYPE_BOOL) {
                return "Bool"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_I32) {
                return "I32"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_I64) {
                return "I64"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_U8) {
                return "U8"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_U32) {
                return "U32"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_U64) {
                return "U64"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_U128) {
                return "U128"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_U256) {
                return "U256"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_U512) {
                return "U512"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_UNIT) {
                return "Unit"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_STRING) {
                return "String"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_KEY) {
                return "Key"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_UREF) {
                return "URef"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_PUBLIC_KEY) {
                return "PublicKey"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_ANY) {
                return "Any"
            } else {
                return ConstValues.INVALID_VALUE
            }
        }
        /*This function generate the Json String represent the CLType compound, such as List, Map, Option, Result, Tuple1, Tuple2, Tuple3 ...
        * CLType that contains recursive declaration inside its body*/
        fun clTypeCompoundToJsonString(clType: CLType) : String {
            if (clType.itsTypeStr == ConstValues.CLTYPE_BYTEARRAY) {
                return "{\"ByteArray\": 32}"
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_TUPLE1) {
                if(clType.innerCLType1.isCLTypePrimitive()) {
                    val clTypeStr:String = clTypePrimitiveToJsonString(clType.innerCLType1)
                    return "{\"Tuple1\": \"" + clTypeStr + "\"}"
                } else {
                    val clTypeStr:String = clTypeCompoundToJsonString(clType.innerCLType1)
                    return "{\"Tuple1\": " + clTypeStr + "}"
                }
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_TUPLE2) {
                if(clType.innerCLType1.isCLTypePrimitive()) {
                    val clType1Str:String = clTypePrimitiveToJsonString(clType.innerCLType1)
                    if(clType.innerCLType2.isCLTypePrimitive()) {
                        val clType2Str:String = clTypePrimitiveToJsonString(clType.innerCLType2)
                        return "{\"Tuple2\": [\"" + clType1Str +"\", \"" + clType2Str + "\"]}"
                    } else {
                        val clType2Str:String = clTypeCompoundToJsonString(clType.innerCLType2)
                        return "{\"Tuple2\": [\"" + clType1Str +"\", " + clType2Str + "]}"
                    }
                } else {
                    val clType1Str:String = clTypeCompoundToJsonString(clType.innerCLType1)
                    if(clType.innerCLType2.isCLTypePrimitive()) {
                        val clType2Str:String = clTypePrimitiveToJsonString(clType.innerCLType2)
                        return "{\"Tuple2\": [" + clType1Str +", \"" + clType2Str + "\"]}"
                    } else {
                        val clType2Str:String = clTypeCompoundToJsonString(clType.innerCLType2)
                        return "{\"Tuple2\": [" + clType1Str +", " + clType2Str + "]}"
                    }
                }
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_TUPLE3) {
                var ret = "{\"Tuple3\": ["
                if(clType.innerCLType1.isCLTypePrimitive()) {
                    val  clTypeStr1 : String = "\"" + clTypePrimitiveToJsonString(clType.innerCLType1) + "\""
                    ret = ret + clTypeStr1 + ", "
                } else {
                    val clType1Str:String = clTypeCompoundToJsonString(clType.innerCLType1)
                    ret = ret + clType1Str + ", "
                }
                if(clType.innerCLType2.isCLTypePrimitive()) {
                    val  clTypeStr2 : String = "\"" + clTypePrimitiveToJsonString(clType.innerCLType2) + "\""
                    ret = ret + clTypeStr2 + ", "
                } else {
                    val clType2Str:String = clTypeCompoundToJsonString(clType.innerCLType2)
                    ret = ret + clType2Str + ", "
                }
                if(clType.innerCLType3.isCLTypePrimitive()) {
                    val  clTypeStr3 : String = "\"" + clTypePrimitiveToJsonString(clType.innerCLType3) + "\""
                    ret = ret + clTypeStr3 + "]"
                } else {
                    val clType3Str:String = clTypeCompoundToJsonString(clType.innerCLType3)
                    ret = ret + clType3Str + "]"
                }
                return ret
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_MAP) {
                if(clType.innerCLType1.isCLTypePrimitive()) {
                    val clType1Str:String = clTypePrimitiveToJsonString(clType.innerCLType1)
                    if(clType.innerCLType2.isCLTypePrimitive()) {
                        val clType2Str:String = clTypePrimitiveToJsonString(clType.innerCLType2)
                        return "{\"Map\": {\"key\":\"" + clType1Str + "\", \"value\":\"" + clType2Str + "\")}}"
                    } else {
                        val clType2Str:String = clTypeCompoundToJsonString(clType.innerCLType2)
                        return "{\"Map\": {\"key\":" + clType1Str + ", \"value\":" + clType2Str + ")}}"
                    }
                } else {
                    val clType1Str:String = clTypeCompoundToJsonString(clType.innerCLType1)
                    if(clType.innerCLType2.isCLTypePrimitive()) {
                        val clType2Str:String = clTypePrimitiveToJsonString(clType.innerCLType2)
                        return "{\"Map\": {\"key\":" + clType1Str + ", \"value\":\"" + clType2Str + "\")}}"
                    } else {
                        val clType2Str:String = clTypeCompoundToJsonString(clType.innerCLType2)
                        return "{\"Map\": {\"key\":" + clType1Str + ", \"value\":" + clType2Str + ")}}"
                    }
                }
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_LIST) {
                if(clType.innerCLType1.isCLTypePrimitive()) {
                    val clTypeStr:String = clTypePrimitiveToJsonString(clType.innerCLType1)
                    return "{\"List\": \"" + clTypeStr + "\"}"
                } else {
                    val clTypeStr:String = clTypeCompoundToJsonString(clType.innerCLType1)
                    return "{\"List\": " + clTypeStr + "}"
                }
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_OPTION) {
                if(clType.innerCLType1.isCLTypePrimitive()) {
                    val clTypeStr:String = clTypePrimitiveToJsonString(clType.innerCLType1)
                    return "{\"Option\": \"" + clTypeStr + "\"}"
                } else {
                    val clTypeStr:String = clTypeCompoundToJsonString(clType.innerCLType1)
                    return "{\"Option\": " + clTypeStr + "}"
                }
            } else if (clType.itsTypeStr == ConstValues.CLTYPE_RESULT) {
                if(clType.innerCLType1.isCLTypePrimitive()) {
                    val clType1Str:String = clTypePrimitiveToJsonString(clType.innerCLType1)
                    if(clType.innerCLType2.isCLTypePrimitive()) {
                        val clType2Str:String = clTypePrimitiveToJsonString(clType.innerCLType2)
                        return "{\"Result\": {\"ok\":" + clType1Str + ", \"err\":" + clType2Str + ")}}"
                    } else {
                        val clType2Str:String = clTypeCompoundToJsonString(clType.innerCLType2)
                        return "{\"Result\": {\"ok\":" + clType1Str + ", \"err\":" + clType2Str + ")}}"
                    }
                } else {
                    val clType1Str:String = clTypeCompoundToJsonString(clType.innerCLType1)
                    if(clType.innerCLType2.isCLTypePrimitive()) {
                        val clType2Str:String = clTypePrimitiveToJsonString(clType.innerCLType2)
                        return "{\"Result\": {\"ok\":" + clType1Str + ", \"err\":" + clType2Str + ")}}"
                    } else {
                        val clType2Str:String = clTypeCompoundToJsonString(clType.innerCLType2)
                        return "{\"Result\": {\"ok\":" + clType1Str + ", \"err\":" + clType2Str + ")}}"
                    }
                }
            } else {
                return ConstValues.INVALID_VALUE
            }
        }
        //Generate the CLType object  from the JSON object
        fun getCLType(from: Any): CLType {
            if (from is String) {
                return getCLTypePrimitive(from)
            } else {
                return getCLTypeCompound(from as JsonObject)
            }
        }
        //Generate the CLType object (of type primitive (such as bool,  i32,  i64,  u8,  u32,  u64,  u128,  u266,  u512,  string,  unit,  publickey,  key,  ...)  from the JSON object
        fun getCLTypePrimitive(from: String): CLType {
            val ret = CLType()
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
            val ret = CLType()
            from[ConstValues.CLTYPE_OPTION] ?.let  {
                ret.itsTypeStr = ConstValues.CLTYPE_OPTION
                ret.innerCLType1 = CLType()
                ret.innerCLType1 = getCLType(from[ConstValues.CLTYPE_OPTION] as Any)
                return ret
            }
            from[ConstValues.CLTYPE_LIST] ?.let  {
                ret.itsTypeStr = ConstValues.CLTYPE_LIST
                ret.innerCLType1 = CLType()
                ret.innerCLType1 = getCLType(from[ConstValues.CLTYPE_LIST] as Any)
                return ret
            }
            from[ConstValues.CLTYPE_MAP] ?.let  {
                ret.itsTypeStr = ConstValues.CLTYPE_MAP
                //clParse for key
                ret.innerCLType1 = getCLType(from[ConstValues.CLTYPE_MAP].get("key") as Any)
                //clParse for value
                ret.innerCLType2 = getCLType(from[ConstValues.CLTYPE_MAP].get("value") as Any)
                return ret
            }
            from[ConstValues.CLTYPE_RESULT] ?.let  {
                ret.itsTypeStr = ConstValues.CLTYPE_RESULT
                //clParse for ok
                ret.innerCLType1 = getCLType(from[ConstValues.CLTYPE_RESULT].get("ok") as Any)
                //clParse for err
                ret.innerCLType2 = getCLType(from[ConstValues.CLTYPE_RESULT].get("err") as Any)
                return ret
            }
            from[ConstValues.CLTYPE_BYTEARRAY] ?.let {
                ret.itsTypeStr = ConstValues.CLTYPE_BYTEARRAY
                return ret
            }
            from[ConstValues.CLTYPE_TUPLE1] ?.let  {
                ret.itsTypeStr = ConstValues.CLTYPE_TUPLE1
                //clParse for tuple 1
                ret.innerCLType1 = getCLType(from[ConstValues.CLTYPE_TUPLE1][0] as Any)
                 return ret
            }
            from[ConstValues.CLTYPE_TUPLE2] ?.let  {
                 ret.itsTypeStr = ConstValues.CLTYPE_TUPLE2
                //clParse for tuple 1
                ret.innerCLType1 = getCLType(from[ConstValues.CLTYPE_TUPLE2][0] as Any)
                //clParse for tuple 2
                ret.innerCLType2 = getCLType(from[ConstValues.CLTYPE_TUPLE2][1] as Any)
                return ret
            }
            from[ConstValues.CLTYPE_TUPLE3] ?.let  {
                ret.itsTypeStr = ConstValues.CLTYPE_TUPLE3
                //clParse for tuple 1
                ret.innerCLType1 = getCLType(from[ConstValues.CLTYPE_TUPLE3][0] as Any)
                //clParse for tuple 2
                ret.innerCLType2 = getCLType(from[ConstValues.CLTYPE_TUPLE3][1] as Any)
                //clParse for tuple 3
                ret.innerCLType3 = getCLType(from[ConstValues.CLTYPE_TUPLE3][2] as Any)
                return ret
            }
            return ret
        }
    }
}