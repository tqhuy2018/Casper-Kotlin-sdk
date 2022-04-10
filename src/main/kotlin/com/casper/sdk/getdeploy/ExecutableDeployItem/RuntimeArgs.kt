package com.casper.sdk.getdeploy.ExecutableDeployItem

import com.casper.sdk.clvalue.CLValue
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
import javax.naming.Name

class RuntimeArgs {
    var listNamedArg: MutableList<NamedArg> = mutableListOf()
   companion object {
       fun  fromJsonArrayToObj(jsonArray:JsonArray):RuntimeArgs {
           var ret :RuntimeArgs = RuntimeArgs();
           var totalObj = jsonArray.size - 1
           println("Total args:${totalObj}")
           for(i in 0..totalObj) {
               println("----------Get arg item number :${i}----------")
               var oneNA:NamedArg = NamedArg.fromJsonToNamedArg(jsonArray[i] as JsonArray)
               ret.listNamedArg.add(oneNA)
           }
           return ret
       }
   }
}
