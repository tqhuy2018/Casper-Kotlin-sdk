package com.casper.sdk.storedvalue

import net.jemzart.jsonkraken.values.JsonObject
import javax.swing.GroupLayout.Group

class Groups {
    var group:String = ""
    var key:String = ""
    companion object {
        fun  fromJsonObjectToGroups(from:JsonObject) : Groups {
            var ret:Groups = Groups()
            ret.group = from["group"].toString()
            ret.key = from["key"].toString()
            return ret
        }
    }
}