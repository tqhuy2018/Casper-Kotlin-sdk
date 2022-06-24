package com.casper.sdk.getdeploy.ExecutionResult

import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing ExecutionEffect information */
class ExecutionEffect {
    var operations: MutableList<CasperOperation> = mutableListOf()
    var transforms: MutableList<TransformEntry> = mutableListOf()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the ExecutionEffect object */
        fun  fromJsonToExecutionEffect(from: JsonObject): ExecutionEffect {
            val ret = ExecutionEffect()
            if(from.toJsonString() != "null") {
                val operationArray:  JsonArray = from["operations"] as JsonArray
                if(operationArray.count()>0) {
                    val totalOperation: Int = operationArray.count() - 1
                    for(i in 0.. totalOperation) {
                        val oneOJson: JsonObject = operationArray[i] as JsonObject
                        val oneOperation = CasperOperation()
                        oneOperation.key = oneOJson.get("key").toString()
                        oneOperation.kind = oneOJson.get("kind").toString()
                        ret.operations.add(oneOperation)
                    }
                }
                val transformArray: JsonArray = from["transforms"] as JsonArray
                if (transformArray.count() > 0) {
                    val totalTransform = transformArray.count()-1
                    for(i in 0..totalTransform) {
                        val oneTransform: TransformEntry = TransformEntry.fromJsonToCasperTransform(transformArray[i] as JsonObject)
                        ret.transforms.add(oneTransform)
                    }
                }
            }
            return ret
        }
    }
}