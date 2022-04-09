package com.casper.sdk.getdeploy.ExecutableDeployItem

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
               println("i = ${i}")
               println(jsonArray[i].toJsonString())
               var oneItem:JsonArray = jsonArray[0] as JsonArray
               var itemName = oneItem[0].toJsonString()
               print("item name:${itemName}")
              /* var oneJson:JsonObject = JsonObject(jsonArray[i])
               var oneNA : NamedArg = NamedArg()
               oneNA.itsName = oneJson.get()*/
           }
           return ret
       }
   }
}
