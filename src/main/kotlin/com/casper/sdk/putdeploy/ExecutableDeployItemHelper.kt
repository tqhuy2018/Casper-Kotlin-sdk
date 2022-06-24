package com.casper.sdk.putdeploy

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLValue
import com.casper.sdk.getdeploy.ExecutableDeployItem.*

class ExecutableDeployItemHelper {
    companion object {
        fun toJsonString(executableDeployItem: ExecutableDeployItem) : String {
           if (executableDeployItem.itsType == ExecutableDeployItem.MODULE_BYTES) {
               val ediMB  = executableDeployItem.itsValue.get(0) as ExecutableDeployItem_ModuleBytes
               val argsString = argsToJsonString(ediMB.args)
               val innerJson: String = "{\"module_bytes\": \"" + ediMB.module_bytes + "\"," + argsString + "}"
               val retStr : String = "{\"ModuleBytes\": " + innerJson + "}"
               return retStr
           } else if (executableDeployItem.itsType == ExecutableDeployItem.STORED_CONTRACT_BY_HASH) {
               val ediContractHash  = executableDeployItem.itsValue.get(0) as ExecutableDeployItem_StoredContractByHash
               val argsString = argsToJsonString(ediContractHash.args)
               val innerJson: String  = "{\"hash\": \"" + ediContractHash.itsHash + "\",\"entry_point\": \"" + ediContractHash.entryPoint + "\"," + argsString + "}"
               val retStr : String = "{\"StoredContractByHash\":" + innerJson + "}"
               return retStr
           }  else if (executableDeployItem.itsType == ExecutableDeployItem.STORED_CONTRACT_BY_NAME) {
               val ediContractName = executableDeployItem.itsValue.get(0) as ExecutableDeployItem_StoredContractByName
               val argsString = argsToJsonString(ediContractName.args)
               val innerJson: String  = "{\"name\": \"" + ediContractName.itsName + "\",\"entry_point\": \"" + ediContractName.entryPoint + "\"," + argsString + "}"
               val retStr : String = "{\"StoredContractByName\":" + innerJson + "}"
               return retStr
           } else if (executableDeployItem.itsType == ExecutableDeployItem.STORED_VERSIONED_CONTRACT_BY_HASH) {
               val ediVersionedContractHash = executableDeployItem.itsValue.get(0) as ExecutableDeployItem_StoredVersionedContractByHash
               val argsString = argsToJsonString(ediVersionedContractHash.args)
               if (ediVersionedContractHash.isVersionExisted) {
                   val innerJson: String  = "{\"hash\": \"" + ediVersionedContractHash.itsHash + "\", \"version\":null, \"entry_point\": \"" + ediVersionedContractHash.entryPoint + "\"," + argsString + "}"
                   val retStr : String = "{\"StoredVersionedContractByHash\":" + innerJson + "}"
                   return retStr
               } else {
                   val innerJson: String  = "{\"hash\": \"" + ediVersionedContractHash.itsHash + "\", \"version\":" + ediVersionedContractHash.version + ", \"entry_point\": \"" + ediVersionedContractHash.entryPoint + "\"," + argsString + "}"
                   val retStr : String = "{\"StoredVersionedContractByHash\":" + innerJson + "}"
                   return retStr
               }
           } else if (executableDeployItem.itsType == ExecutableDeployItem.STORED_VERSIONED_CONTRACT_BY_NAME) {
               val ediVersionedContractName = executableDeployItem.itsValue.get(0) as ExecutableDeployItem_StoredVersionedContractByName
               val argsString = argsToJsonString(ediVersionedContractName.args)
               if (ediVersionedContractName.isVersionExisted) {
                   val innerJson: String  = "{\"name\": \"" + ediVersionedContractName.itsName + "\", \"version\":null, \"entry_point\": \"" + ediVersionedContractName.entryPoint + "\"," + argsString + "}"
                   val retStr : String = "{\"StoredVersionedContractByName\":" + innerJson + "}"
                   return retStr
               } else {
                   val innerJson: String  = "{\"name\": \"" + ediVersionedContractName.itsName + "\", \"version\":" + ediVersionedContractName.version + ", \"entry_point\": \"" + ediVersionedContractName.entryPoint + "\"," + argsString + "}"
                   val retStr : String = "{\"StoredVersionedContractByName\":" + innerJson + "}"
                   return retStr
               }
           } else  if (executableDeployItem.itsType == ExecutableDeployItem.TRANSFER) {
               val transfer = executableDeployItem.itsValue.get(0) as ExecutableDeployItem_Transfer
               val argsString = argsToJsonString(transfer.args)
               val innerJson: String = "{" + argsString + "}"
               val retStr : String = "{\"Transfer\": " + innerJson + "}"
               return retStr
           }
           return ConstValues.INVALID_VALUE
        }
        fun argsToJsonString(args: RuntimeArgs) :String{
            var ret = ""
            val totalArgs:Int = args.listNamedArg.size - 1
            var counter = 0
            for(i in 0.. totalArgs) {
                val clValueJsonStr : String = CLValue.toJsonString(args.listNamedArg.get(i).clValue)
                val argStr:String = "[\"" + args.listNamedArg.get(i).itsName + "\"," + clValueJsonStr + "]"
                if(counter < totalArgs) {
                    ret = ret + argStr + ","
                } else {
                    ret = ret + argStr
                }
                counter ++
            }
            ret = "\"args\": [" + ret + "]"
            return ret
        }
    }

}