package com.casper.sdk.clvalue

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.get
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class CLParsed {
    var itsValueInStr:String = ""
    var itsCLType:CLType = CLType()
    //innerParsed to hold value for the following type:
    //Option, Result, Tuple1 will take only 1 item of innerParsed
    //Map, Tuple2 will take 2  item of innerParsed
    //Tuple3 will take 3 item of innerParsed
    lateinit var innerParsed1:CLParsed
    lateinit var innerParsed2:CLParsed
    lateinit var innerParsed3:CLParsed
    //This property is for holding array value of List and FixList
    var itsValue:MutableList<CLParsed> = mutableListOf()
    fun logInfo() {
        println("Information for CLParsed")
        if(itsCLType.isCLTypePrimitive()) {
            println("CLParsed for CLType of type primitive")
            println("CLParsed value: ${itsValueInStr}")
        } else {
            println("CLParsed for CLTYpe of type compound")
            if (itsCLType.itsTypeStr == ConstValues.CLTYPE_LIST){
                println("CLParsed for CLTYpe of type List, with total element:${this.itsValue.count()}")
                //this.innerParsed1.logInfo();
                for(i in 0..this.itsValue.count()-1) {
                    var oneParse: CLParsed = this.itsValue[i] as CLParsed
                    oneParse.logInfo()
                }
            }
        }
    }
    companion object
    {
        fun getCLParsedPrimitive(from:Any, withCLType: CLType):CLParsed {
            println("\nGet Parse")
            var ret:CLParsed = CLParsed()
            ret.itsCLType = withCLType
            if (withCLType.itsTypeStr == ConstValues.CLTYPE_BOOL) {
                ret.itsValueInStr = from.toJsonString()
                println("Parsed value Bool is:${ret.itsValueInStr}")
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_I32) {
                ret.itsValueInStr =  from.toJsonString()
                println("Parsed value I32 is:${ret.itsValueInStr}")
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_I64) {
                ret.itsValueInStr =  from.toJsonString()
                println("Parsed value I64 is:${ret.itsValueInStr}")
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U8) {
                ret.itsValueInStr = from.toJsonString()
                println("Parsed value U8 is:${ret.itsValueInStr}")
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U32) {
                ret.itsValueInStr = from.toJsonString()
                println("Parsed value U32 is:${ret.itsValueInStr}")
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U64) {
                ret.itsValueInStr = from.toJsonString()
                println("Parsed value U64 is:${ret.itsValueInStr}")
            }  else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U128) {
                ret.itsValueInStr = from as String
                println("Parsed value U128 is:${ret.itsValueInStr}")
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U256) {
                ret.itsValueInStr = from as String
                println("Parsed value U256 is:${ret.itsValueInStr}")
            }
            else if (withCLType.itsTypeStr == ConstValues.CLTYPE_U512) {
                ret.itsValueInStr = from as String
                println("Parsed value U512 is:${ret.itsValueInStr}")
            }  else if (withCLType.itsTypeStr == ConstValues.CLTYPE_STRING) {
                ret.itsValueInStr = from as String
                println("Parsed value String is:${ret.itsValueInStr}")
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_KEY) {
                println("Get parse of type Key")
                var keyValuePair:JsonObject = from as JsonObject
                if (from.get("Hash").toJsonString() != "null") {
                    ret.itsValueInStr = from.get("Hash") as String
                    println("Parsed value Key Hash is:${ret.itsValueInStr}")
                } else  if (from.get("Account").toJsonString() != "null") {
                    ret.itsValueInStr = from.get("Account") as String
                    println("Parsed value Key Account is:${ret.itsValueInStr}")
                }
            }  else if (withCLType.itsTypeStr == ConstValues.CLTYPE_UREF) {
                ret.itsValueInStr = from as String
                println("Parsed value UREF is:${ret.itsValueInStr}")
            }   else if (withCLType.itsTypeStr == ConstValues.CLTYPE_PUBLIC_KEY) {
                ret.itsValueInStr = from as String
                println("Parsed value PublicKey is:${ret.itsValueInStr}")
            }  else if (withCLType.itsTypeStr == ConstValues.CLTYPE_BYTEARRAY) {
                ret.itsValueInStr = from as String
                println("Parsed value BytesArray is:${ret.itsValueInStr}")
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_ANY) {
                ret.itsValueInStr = ConstValues.VALUE_NULL
                println("Parsed value BytesArray is:${ret.itsValueInStr}")
            } else {
                println("Of differnet type")
            }
            return ret
        }
        fun getCLParsedCompound(from: Any, withCLType: CLType):CLParsed {
            var ret: CLParsed = CLParsed()
            ret.itsCLType = withCLType
            if (withCLType.itsTypeStr == ConstValues.CLTYPE_OPTION) {
                println("Get CLParsed of type Compound Option, with innerType:${withCLType.innerCLType1.itsTypeStr}")
                ret.innerParsed1 = CLParsed()
                ret.innerParsed1 = CLParsed.getCLParsed(from,withCLType.innerCLType1)
            } else if (withCLType.itsTypeStr == ConstValues.CLTYPE_TUPLE1) {
                println("Get CLParsed of type Compound Tuple2, with Tuple1 type:${withCLType.innerCLType1.itsTypeStr}")
                ret.innerParsed1 = CLParsed()
                var jsonArray:JsonArray = from as JsonArray
                ret.innerParsed1 = CLParsed.getCLParsed(jsonArray[0] as Any,withCLType.innerCLType1)
                return ret
            }else if (withCLType.itsTypeStr == ConstValues.CLTYPE_TUPLE2) {
                println("Get CLParsed of type Compound Tuple2, with Tuple1 type:${withCLType.innerCLType1.itsTypeStr}")
                println("Get CLParsed of type Compound Tuple2, with Tuple2 type:${withCLType.innerCLType2.itsTypeStr}")
                ret.innerParsed1 = CLParsed()
                ret.innerParsed2 = CLParsed()
                var jsonArray:JsonArray = from as JsonArray
                ret.innerParsed1 = CLParsed.getCLParsed(jsonArray[0] as Any,withCLType.innerCLType1)
                ret.innerParsed2 = CLParsed.getCLParsed(jsonArray[1] as Any,withCLType.innerCLType2)
                return ret
            }else if (withCLType.itsTypeStr == ConstValues.CLTYPE_TUPLE3) {
                println("Get CLParsed of type Compound Tuple3, with Tuple1 type:${withCLType.innerCLType1.itsTypeStr}")
                println("Get CLParsed of type Compound Tuple3, with Tuple2 type:${withCLType.innerCLType2.itsTypeStr}")
                println("Get CLParsed of type Compound Tuple3, with Tuple3 type:${withCLType.innerCLType3.itsTypeStr}")
                ret.innerParsed1 = CLParsed()
                ret.innerParsed2 = CLParsed()
                ret.innerParsed3 = CLParsed()
                var jsonArray:JsonArray = from as JsonArray
                ret.innerParsed1 = CLParsed.getCLParsed(jsonArray[0] as Any,withCLType.innerCLType1)
                ret.innerParsed2 = CLParsed.getCLParsed(jsonArray[1] as Any,withCLType.innerCLType2)
                ret.innerParsed3 = CLParsed.getCLParsed(jsonArray[2] as Any,withCLType.innerCLType3)
                return ret
            }else if (withCLType.itsTypeStr == ConstValues.CLTYPE_RESULT) {
                println("Get CLParsed of type Compound Result, with ok type:${withCLType.innerCLType1.itsTypeStr}")
                println("Get CLParsed of type Compound Result, with err type:${withCLType.innerCLType2.itsTypeStr}")
                val parseOK = from.get("Ok").toJsonString()
                if(parseOK != "null") {
                    ret.itsValueInStr = "Ok"
                    ret.innerParsed1 = CLParsed()
                    ret.innerParsed1 = CLParsed.getCLParsed(from.get("Ok") as Any,withCLType.innerCLType1)
                    return ret
                }
                val parseErr = from.get("Err").toJsonString()
                if(parseErr != "null") {
                    ret.itsValueInStr = "Err"
                    ret.innerParsed1 = CLParsed()
                    ret.innerParsed1 = CLParsed.getCLParsed(from.get("Err") as Any,withCLType.innerCLType1)
                    return ret
                }

            } else if(withCLType.itsTypeStr == ConstValues.CLTYPE_LIST) {
                println("Get CLParsed of type Compound List")
                var listJson:JsonArray = from as JsonArray
                var totalElement = listJson.count()
                if(totalElement>0) {
                    for(i in 0..totalElement-1) {
                        var oneElement:Any = listJson[i] as Any
                        var oneParsed:CLParsed = CLParsed()
                        oneParsed = getCLParsed(oneElement,withCLType.innerCLType1)
                        ret.itsValue.add(oneParsed)
                    }
                }
            } else if(withCLType.itsTypeStr == ConstValues.CLTYPE_MAP) {
                //innerParse1.itsValue is the list of map-key value
                //innerParse2.itsValue is the list of map-value value
                println("Get CLParsed of type Compound MAP")
                var listJson:JsonArray = from as JsonArray
                var totalElement = listJson.count()
                if(totalElement>0) {
                    ret.innerParsed1 = CLParsed()
                    ret.innerParsed2 = CLParsed()
                    for(i in 0..totalElement-1) {
                        var oneElement:Any = listJson[i] as JsonObject
                        //add item for map-key
                        var parsedKey:CLParsed = CLParsed()
                        parsedKey = getCLParsed(oneElement.get("key") as Any,withCLType.innerCLType1)
                        ret.innerParsed1.itsValue.add(parsedKey)
                        //add item for map-value
                        var parsedValue:CLParsed = CLParsed()
                        parsedValue = getCLParsed(oneElement.get("value") as Any, withCLType.innerCLType2)
                        ret.innerParsed2.itsValue.add(parsedValue)
                    }
                }
                return ret
            }
            return ret;
        }
        fun getCLParsed(from: Any, withCLType: CLType):CLParsed {
            println("----------------------------BEGIN PARSED--------------------------------------------------------------")
           if ( withCLType.isCLTypePrimitive()) {
               println("Get parsed for cltype primitive, of type :${withCLType.itsTypeStr}")
               println("--------------------------------END PARSED----------------------------------------------------------")
               return CLParsed.getCLParsedPrimitive(from,withCLType);
           } else {
               println("Get parsed for cltype coupound of type:${withCLType.itsTypeStr}")
               println("--------------------------------END PARSED----------------------------------------------------------")
               return CLParsed.getCLParsedCompound(from,withCLType);
           }
        }
    }

}