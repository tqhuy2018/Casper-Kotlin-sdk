package com.casper.sdk.getdeploy.ExecutableDeployItem

import com.casper.sdk.clvalue.CLValue
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import javax.naming.Name
/** Class built for storing RuntimeArgs information */
class RuntimeArgs {
    var listNamedArg:  MutableList<NamedArg> = mutableListOf()
   companion object {
       /** This function parse the JsonArray (taken from server RPC method call) to get the list of NamedArg object
        * Then store it in the RuntimeArgs object */
       fun  fromJsonArrayToObj(jsonArray: JsonArray): RuntimeArgs {
           val ret = RuntimeArgs()
           val totalObj = jsonArray.size - 1
           for(i in 0..totalObj) {
               val oneNA: NamedArg = NamedArg.fromJsonToNamedArg(jsonArray[i] as JsonArray)
               ret.listNamedArg.add(oneNA)
           }
           return ret
       }
   }
}
