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
           var ret : RuntimeArgs = RuntimeArgs()
           var totalObj = jsonArray.size - 1
           println("Total args: ${totalObj}")
           for(i in 0..totalObj) {
               println("----------Get arg item number : ${i}----------")
               var oneNA: NamedArg = NamedArg.fromJsonToNamedArg(jsonArray[i] as JsonArray)
               ret.listNamedArg.add(oneNA)
           }
           return ret
       }
   }
}
