package com.casper.sdk.getdeploy.ExecutionResult

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class ExecutionEffect {
    var operations: MutableList<CasperOperation> = mutableListOf()
    var transforms: MutableList<TransformEntry> = mutableListOf()
    companion object {
        fun  fromJsonToExecutionEffect(from: JsonObject): ExecutionEffect {
            var ret:  ExecutionEffect = ExecutionEffect()
            if(from.toJsonString() != "null") {
                val operationArray:  JsonArray = from["operations"] as JsonArray
                if(operationArray.count()>0) {
                    val totalOperation: Int = operationArray.count() - 1
                    println("Total operation: ${totalOperation}")
                    for(i in 0.. totalOperation) {
                        var oneOJson: JsonObject = operationArray[i] as JsonObject
                        var oneOperation: CasperOperation = CasperOperation()
                        oneOperation.key = oneOJson.get("key").toString()
                        oneOperation.kind = oneOJson.get("kind").toString()
                        ret.operations.add(oneOperation)
                    }
                } else {
                    println("Operations list empty")
                }
                val transformArray: JsonArray = from["transforms"] as JsonArray
                if (transformArray.count() > 0) {
                    println("Array of transform not empty")
                    val totalTransform = transformArray.count()-1
                    for(i in 0..totalTransform) {
                        println("Get transform number ${i}")
                        val oneTransform: TransformEntry = TransformEntry.fromJsonToCasperTransform(transformArray[i] as JsonObject)
                        ret.transforms.add(oneTransform)
                    }
                } else {
                    println("Transform list empty")
                }
            } else {
                println("Effect Empty")
            }
            return ret
        }
    }
}