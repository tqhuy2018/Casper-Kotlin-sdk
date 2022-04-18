package com.casper.sdk.getdeploy.ExecutionResult

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLValue
import com.casper.sdk.getdeploy.ExecutionResult.Transform.*
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class TransformEntry {
    var key: String = ""
    var transform: CasperTransform= CasperTransform()
    companion object {
        fun fromJsonToCasperTransform(from:  JsonObject) :  TransformEntry {
            var ret: TransformEntry = TransformEntry()
            ret.key = from["key"].toString()
            val transform = from["transform"]
            if(transform is String) {
                if (transform == ConstValues.TRANSFORM_IDENTITY) {
                    var transform: CasperTransform = CasperTransform()
                    transform.itsType = ConstValues.TRANSFORM_IDENTITY
                    ret.transform = transform
                } else if (transform == ConstValues.TRANSFORM_WRITE_CONTRACT_WASM) {
                    var transform: CasperTransform = CasperTransform()
                    transform.itsType = ConstValues.TRANSFORM_WRITE_CONTRACT_WASM
                    ret.transform = transform
                } else if (transform == ConstValues.TRANSFORM_WRITE_CONTRACT) {
                    var transform: CasperTransform = CasperTransform()
                    transform.itsType = ConstValues.TRANSFORM_WRITE_CONTRACT
                    ret.transform = transform
                } else if (transform == ConstValues.TRANSFORM_WRITE_CONTRACT_PACKAGE) {
                    var transform: CasperTransform = CasperTransform()
                    transform.itsType = ConstValues.TRANSFORM_WRITE_CONTRACT_PACKAGE
                    ret.transform = transform
                }
            } else if(transform is JsonObject){
                val clValue = transform[ConstValues.TRANSFORM_WRITE_CLVALUE].toJsonString()
                if(clValue != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_WRITE_CLVALUE
                    val clValue: CLValue = CLValue.fromJsonObjToCLValue(transform[ConstValues.TRANSFORM_WRITE_CLVALUE] as JsonObject)
                    //println("************************cLValue bytes: ${clValue.itsBytes},  parse: ${clValue.itsParse.itsValueInStr},  type: ${clValue.itsCLType.itsTypeStr}")
                    casperTransform.itsValue.add(clValue)
                    ret.transform = casperTransform
                    return ret
                }
                val writeAccount = transform[ConstValues.TRANSFORM_WRITE_ACCOUNT].toJsonString()
                if(writeAccount!= "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_WRITE_ACCOUNT
                    casperTransform.itsValue.add(transform[ConstValues.TRANSFORM_WRITE_ACCOUNT].toString())
                    ret.transform = casperTransform
                    return ret
                }
                if(clValue != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_WRITE_ACCOUNT
                    casperTransform.itsValue.add(writeAccount)
                    ret.transform = casperTransform
                    return ret
                }
                val writeDeployInfo = transform[ConstValues.TRANSFORM_WRITE_DEPLOY_INFO].toJsonString()
                if(writeDeployInfo != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_WRITE_DEPLOY_INFO
                    //get Deploy information
                    casperTransform.itsValue.add(DeployInfo.fromJsonToDeployInfo(transform[ConstValues.TRANSFORM_WRITE_DEPLOY_INFO] as JsonObject))
                    ret.transform = casperTransform
                    return ret
                }
                val writeEraInfo = transform[ConstValues.TRANSFORM_WRITE_ERA_INFO].toJsonString()
                if(writeEraInfo != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_WRITE_ERA_INFO
                    casperTransform.itsValue.add(EraInfo.fromJsonArrayToEraInfo(transform[ConstValues.TRANSFORM_WRITE_ERA_INFO] as JsonObject))
                    ret.transform = casperTransform
                    return ret
                }
                val writeTransfer = transform[ConstValues.TRANSFORM_WRITE_TRANSFER].toJsonString()
                if(writeTransfer != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_WRITE_TRANSFER
                    casperTransform.itsValue.add(Transfer.fromJsonToTransfer(transform[ConstValues.TRANSFORM_WRITE_TRANSFER] as JsonObject))
                    ret.transform = casperTransform
                    return ret
                }
                val writeBid = transform[ConstValues.TRANSFORM_WRITE_BID].toJsonString()
                if(writeBid != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_WRITE_BID
                    casperTransform.itsValue.add(Bid.fromJsonToBid(transform[ConstValues.TRANSFORM_WRITE_BID] as JsonObject))
                    ret.transform = casperTransform
                    return ret
                }
                val writeWriteWithdraw = transform[ConstValues.TRANSFORM_WRITE_WITHDRAW].toJsonString()
                if(writeWriteWithdraw != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_WRITE_WITHDRAW
                    casperTransform.itsValue.add(Withdraw.fromJsonArrayToWithdraw(transform[ConstValues.TRANSFORM_WRITE_WITHDRAW] as JsonArray))
                    ret.transform = casperTransform
                    return ret
                }
                val addInt32 = transform[ConstValues.TRANSFORM_ADD_INT32].toJsonString()
                if(addInt32 != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_ADD_INT32
                    casperTransform.itsValue.add(addInt32.toInt())
                    ret.transform = casperTransform
                    return ret
                }
                val addUInt64 = transform[ConstValues.TRANSFORM_ADD_UINT64].toJsonString()
                if(addUInt64 != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_ADD_UINT64
                    casperTransform.itsValue.add(addUInt64.toULong())
                    ret.transform = casperTransform
                    return ret
                }
                val addU128 = transform[ConstValues.TRANSFORM_ADD_UINT128].toJsonString()
                if(addU128 != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_ADD_UINT128
                    casperTransform.itsValue.add(addU128)
                    ret.transform = casperTransform
                    return ret
                }
                val addU256 = transform[ConstValues.TRANSFORM_ADD_UINT256].toJsonString()
                if(addU256 != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_ADD_UINT256
                    casperTransform.itsValue.add(addU256)
                    ret.transform = casperTransform
                    return ret
                }
                val addU512 = transform[ConstValues.TRANSFORM_ADD_UINT512].toJsonString()
                if(addU512 != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_ADD_UINT512
                    casperTransform.itsValue.add(transform[ConstValues.TRANSFORM_ADD_UINT512].toString())
                    ret.transform = casperTransform
                    return ret
                }
                val addKey = transform[ConstValues.TRANSFORM_ADD_KEY].toJsonString()
                if(addKey != "null") {
                    var casperTransform: CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_ADD_KEY
                    //get list of NamedKey
                    val listNamedKey = transform[ConstValues.TRANSFORM_ADD_KEY] as JsonArray
                    val totalNamedKey = listNamedKey.count()
                    if(totalNamedKey>0) {
                        for(i in 0..totalNamedKey-1) {
                            var oneNKJson: JsonObject = listNamedKey[i] as JsonObject
                            var oneNamedKey: NamedKey = NamedKey()
                            oneNamedKey.key = oneNKJson["key"].toString()
                            oneNamedKey.name = oneNKJson["name"].toString()
                            casperTransform.itsValue.add(oneNamedKey)
                        }
                    }
                    ret.transform = casperTransform
                    return ret
                }
                val failureTransform = transform[ConstValues.TRANSFORM_FAILURE].toJsonString()
                if(failureTransform != "null") {
                    var casperTransform:  CasperTransform = CasperTransform()
                    casperTransform.itsType = ConstValues.TRANSFORM_FAILURE
                    casperTransform.itsValue.add(transform[ConstValues.TRANSFORM_FAILURE].toString())
                    ret.transform = casperTransform
                    return ret
                }
            }
            return ret
        }
    }
}