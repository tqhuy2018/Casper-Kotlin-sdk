package com.casper.sdk.storedvalue

import net.jemzart.jsonkraken.values.JsonObject
import javax.swing.GroupLayout.Group

/** Class built for storing Groups information */
class Groups {
    var group: String = ""
    var key: String = ""
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the Account object */

        fun  fromJsonObjectToGroups(from: JsonObject) :  Groups {
            var ret = Groups()
            ret.group = from["group"].toString()
            ret.key = from["key"].toString()
            return ret
        }
    }
}