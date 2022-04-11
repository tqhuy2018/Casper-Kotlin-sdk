package com.casper.sdk.getdeploy.ExecutionResult

import com.casper.sdk.ConstValues
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

class TransformEntry {
    var key:String = ""
    var transform:CasperTransform= CasperTransform()
    companion object {
        fun fromJsonToCasperTransform(from: JsonObject) : TransformEntry {
            var ret:TransformEntry = TransformEntry()
            ret.key = from["key"].toString()
            println("KEy is:${ret.key}")
            val transform = from["transform"]
            if(transform is String) {
                if (transform == "Identity") {
                    var transform:CasperTransform = CasperTransform()
                    transform.itsType = ConstValues.TRANSFORM_IDENTITY
                    ret.transform = transform
                    println("Transform of type Identity")
                }
            } else if(transform is JsonObject){
                val clValue = transform["WriteCLValue"].toJsonString()
                if(clValue != "null") {
                    println("Of type WriteCLValue")
                    var transform:CasperTransform = CasperTransform()
                    transform.itsType = ConstValues.TRANSFORM_WRITE_CLVALUE
                    ret.transform = transform
                    return ret
                }
                val addU512 = transform["AddUInt512"].toJsonString()
                if(addU512 != "null") {
                    println("Of type AddUInt512")
                    var transform:CasperTransform = CasperTransform()
                    transform.itsType = ConstValues.TRANSFORM_WRITE_CLVALUE
                    ret.transform = transform
                    return ret
                }
            }
            return ret
        }
    }
}