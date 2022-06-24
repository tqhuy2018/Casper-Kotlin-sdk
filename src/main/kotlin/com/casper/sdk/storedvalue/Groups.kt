package com.casper.sdk.storedvalue

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing Groups information */
class Groups {
    var group: String = ""
    var keys: MutableList<String> = mutableListOf()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the Account object */
        fun  fromJsonObjectToGroups(from: JsonObject) :  Groups {
            val ret = Groups()
            ret.group = from["group"].toString()
            //ret.key = from["key"].toString()
            // Get keys
            val keyJson = from["keys"]
            if(keyJson!=null) {
                val keyList = from["keys"] as JsonArray
                val totalKey: Int = keyList.count()-1
                if(totalKey >= 0) {
                    for(i in 0..totalKey) {
                        ret.keys.add(keyList[i].toString())
                    }
                }
            }
            return ret
        }
    }
}