package com.casper.sdk.clvalue

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

/**Class built for storing the parse value of a CLValue object.
For example take this CLValue object
{
"bytes": "0400e1f505"
"parsed": "100000000"
"cl_type": "U512"
}
Then the parse will hold the value of 100000000.
There are some more attributes in the object to store more information on the type of the parse (CLType),  its value in String for later handle in serialization or show the information
 */
class CLParsed {
    var itsValueInStr: String = ""
    var itsCLType: CLType = CLType()
    //innerParsed to hold value for the following type: 
    //Option,  Result,  Tuple1 will take only 1 item of innerParsed
    //Map,  Tuple2 will take 2  item of innerParsed
    //Tuple3 will take 3 item of innerParsed
    lateinit var innerParsed1: CLParsed
    lateinit var innerParsed2: CLParsed
    lateinit var innerParsed3: CLParsed
    //This property is for holding array value of List and FixList,it is a list that can hold list of CLParse elements
    var itsValue: MutableList<CLParsed> = mutableListOf()
    fun isInnerParsed1Initialize(): Boolean {
        if(this:: innerParsed1.isInitialized) {
            return true
        }
        return false
    }
    fun isInnerParsed2Initialize(): Boolean {
        if(this:: innerParsed2.isInitialized) {
            return true
        }
        return false
    }
    fun isInnerParsed3Initialize(): Boolean {
        if(this:: innerParsed3.isInitialized) {
            return true
        }
        return false
    }
    companion object
    {
        //Generate the CLParse object  of type primitive (such as bool,  i32,  i64,  u8,  u32,  u64,  u128,  u266,  u512,  string,  unit,  publickey,  key,  ...)  from the JSON object fromObj with given clType
        fun getCLParsedPrimitive(from: Any,  withCLType:  CLType): CLParsed {
            val ret: CLParsed = CLParsed()
            ret.itsCLType = withCLType
            if (withCLType.itsTypeStr == ConstValues.CLTYPE_BOOL) {
                ret.itsValueInStr = from.toJsonString()
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_I32) {
                ret.itsValueInStr =  from.toJsonString()
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_I64) {
                ret.itsValueInStr =  from.toJsonString()
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U8) {
                ret.itsValueInStr = from.toJsonString()
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U32) {
                ret.itsValueInStr = from.toJsonString()
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U64) {
                ret.itsValueInStr = from.toJsonString()
            }  else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U128) {
                ret.itsValueInStr = from as String
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U256) {
                ret.itsValueInStr = from as String
            }
            else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U512) {
                ret.itsValueInStr = from as String
            }  else if (withCLType.itsTypeStr == ConstValues.CLTYPE_STRING) {
                ret.itsValueInStr = from as String
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_KEY) {
                if (from.get("Hash").toJsonString() != "null") {
                    ret.itsValueInStr = from.get("Hash") as String
                } else  if (from.get("Account").toJsonString() != "null") {
                    ret.itsValueInStr = from.get("Account") as String
                } else if (from.get("URef").toJsonString() != "null") {
                    ret.itsValueInStr = from.get("URef") as String
                }
            }  else if (withCLType.itsTypeStr == ConstValues.CLTYPE_UREF) {
                ret.itsValueInStr = from as String
            }   else if (withCLType.itsTypeStr == ConstValues.CLTYPE_PUBLIC_KEY) {
                ret.itsValueInStr = from as String
            }  else if (withCLType.itsTypeStr == ConstValues.CLTYPE_BYTEARRAY) {
                ret.itsValueInStr = from as String
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_ANY) {
                ret.itsValueInStr = ConstValues.VALUE_NULL
            } else {
            }
            return ret
        }
        //Generate the CLParse object  of type compound (type with recursive CLValue inside its body,  such as List,  Map,  Tuple ,  Result , Option...)  from the JSON object fromObj with given clType
        fun getCLParsedCompound(from:  Any,  withCLType:  CLType): CLParsed {
            val ret:  CLParsed = CLParsed()
            ret.itsCLType = withCLType
            if (withCLType.itsTypeStr == ConstValues.CLTYPE_OPTION) {
                ret.innerParsed1 = CLParsed()
                ret.innerParsed1 = CLParsed.getCLParsed(from, withCLType.innerCLType1)
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_TUPLE1) {
                ret.innerParsed1 = CLParsed()
                val jsonArray: JsonArray = from as JsonArray
                ret.innerParsed1 = CLParsed.getCLParsed(jsonArray[0] as Any, withCLType.innerCLType1)
                return ret
            }else if (withCLType.itsTypeStr == ConstValues.CLTYPE_TUPLE2) {
                ret.innerParsed1 = CLParsed()
                ret.innerParsed2 = CLParsed()
                val jsonArray: JsonArray = from as JsonArray
                ret.innerParsed1 = CLParsed.getCLParsed(jsonArray[0] as Any, withCLType.innerCLType1)
                ret.innerParsed2 = CLParsed.getCLParsed(jsonArray[1] as Any, withCLType.innerCLType2)
                return ret
            }else if (withCLType.itsTypeStr == ConstValues.CLTYPE_TUPLE3) {
                ret.innerParsed1 = CLParsed()
                ret.innerParsed2 = CLParsed()
                ret.innerParsed3 = CLParsed()
                val jsonArray: JsonArray = from as JsonArray
                ret.innerParsed1 = CLParsed.getCLParsed(jsonArray[0] as Any, withCLType.innerCLType1)
                ret.innerParsed2 = CLParsed.getCLParsed(jsonArray[1] as Any, withCLType.innerCLType2)
                ret.innerParsed3 = CLParsed.getCLParsed(jsonArray[2] as Any, withCLType.innerCLType3)
                return ret
            }else if (withCLType.itsTypeStr == ConstValues.CLTYPE_RESULT) {
                val parseOK = from.get("Ok").toJsonString()
                if(parseOK != "null") {
                    ret.itsValueInStr = "Ok"
                    ret.innerParsed1 = CLParsed()
                    ret.innerParsed1 = CLParsed.getCLParsed(from.get("Ok") as Any, withCLType.innerCLType1)
                    return ret
                }
                val parseErr = from.get("Err").toJsonString()
                if(parseErr != "null") {
                    ret.itsValueInStr = "Err"
                    ret.innerParsed1 = CLParsed()
                    ret.innerParsed1 = CLParsed.getCLParsed(from.get("Err") as Any, withCLType.innerCLType1)
                    return ret
                }

            } else if(withCLType.itsTypeStr == ConstValues.CLTYPE_LIST) {
                val listJson: JsonArray = from as JsonArray
                val totalElement = listJson.count()
                if(totalElement>0) {
                    for(i in 0..totalElement-1) {
                        val oneElement: Any = listJson[i] as Any
                        val oneParsed = getCLParsed(oneElement, withCLType.innerCLType1)
                        ret.itsValue.add(oneParsed)
                    }
                }
            } else if(withCLType.itsTypeStr == ConstValues.CLTYPE_MAP) {
                //innerParse1.itsValue is the list of map-key value
                //innerParse2.itsValue is the list of map-value value
                val listJson: JsonArray = from as JsonArray
                val totalElement = listJson.count()
                if(totalElement>0) {
                    ret.innerParsed1 = CLParsed()
                    ret.innerParsed2 = CLParsed()
                    for(i in 0..totalElement-1) {
                        val oneElement: Any = listJson[i] as JsonObject
                        //add item for map-key
                        val parsedKey = getCLParsed(oneElement.get("key") as Any, withCLType.innerCLType1)
                        ret.innerParsed1.itsValue.add(parsedKey)
                        //add item for map-value
                        val parsedValue = getCLParsed(oneElement.get("value") as Any,  withCLType.innerCLType2)
                        ret.innerParsed2.itsValue.add(parsedValue)
                    }
                }
                return ret
            }
            return ret
        }
        //Generate the CLParse object from JsonObject or String with given information of Type
        fun getCLParsed(from: Any,  withCLType: CLType): CLParsed {
            if ( withCLType.isCLTypePrimitive()) {
                return CLParsed.getCLParsedPrimitive(from, withCLType)
           } else {
                return CLParsed.getCLParsedCompound(from, withCLType)
           }
        }
    }

}