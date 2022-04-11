package com.casper.sdk.getdeploy.ExecutionResult.Transform

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

class Transfer {
    var deployHash:String = ""
    var from:String = ""
    lateinit var to:String// Optional value
    lateinit var id:String//Optional value of ULong u64 but stored as String for easier manipulation
    var isToExisted:Boolean = false
    var isIDExisted:Boolean = false
    var source:String = ""// URef
    var target:String = ""// URef
    var amount:U512Class = U512Class()
    var gas:U512Class = U512Class()
    companion object {
        fun fromJsonToTransfer(from: JsonObject):Transfer {
            var ret:Transfer = Transfer()
            ret.isToExisted = true
            ret.isIDExisted = true
            ret.deployHash = from["deploy_hash"].toString()
            ret.from = from["from"].toString()
            ret.source = from["source"].toString()
            ret.target = from["target"].toString()
            ret.amount = U512Class.fromStringToU512(from["amount"].toString())
            ret.gas = U512Class.fromStringToU512(from["gas"].toString())
            val id = from["id"].toJsonString()
            if(id != "null") {
                ret.id = id
            } else {
                ret.isIDExisted = false
            }
            val to = from["to"].toJsonString()
            if(to != "null") {
                ret.to = to
            } else {
                ret.isToExisted = false
            }
            return ret
        }
    }
}