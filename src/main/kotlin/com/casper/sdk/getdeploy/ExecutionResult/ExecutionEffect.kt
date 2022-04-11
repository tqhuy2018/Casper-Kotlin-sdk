package com.casper.sdk.getdeploy.ExecutionResult

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class ExecutionEffect {
    var operations:MutableList<CasperOperation> = mutableListOf()
    var transforms:MutableList<TransformEntry> = mutableListOf()
    companion object {
        fun  fromJsonToExecutionEffect(from:JsonObject):ExecutionEffect {
            var ret: ExecutionEffect = ExecutionEffect()
            if(from.toJsonString() != "null") {
                val operationArray: JsonArray = from["operations"] as JsonArray
                if(operationArray.count()>0) {
                    val totalOperation:Int = operationArray.count() - 1
                    println("Total operation:${totalOperation}")
                    for(i in 0.. totalOperation) {

                    }
                } else {
                    println("operations empty")
                }
                val transformArray:JsonArray = from["transforms"] as JsonArray
                if (transformArray.count() > 0) {
                    println("Array of transform not null")
                    val totalTransform = transformArray.count()-1
                    for(i in 0..totalTransform) {
                        println("Get transform number ${i}")
                        val oneTransform:TransformEntry = TransformEntry.fromJsonToCasperTransform(transformArray[i] as JsonObject)
                        ret.transforms.add(oneTransform)
                    }
                } else {
                    println("Array of transform null")
                }
            } else {
                println("Effect Empty")
            }
            return ret
        }
    }
}