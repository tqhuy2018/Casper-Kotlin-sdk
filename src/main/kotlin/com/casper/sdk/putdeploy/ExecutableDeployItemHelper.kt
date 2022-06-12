package com.casper.sdk.putdeploy

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLValue
import com.casper.sdk.getdeploy.ExecutableDeployItem.*

class ExecutableDeployItemHelper {
    companion object {
        fun toJsonString(executableDeployItem: ExecutableDeployItem) : String {
           if (executableDeployItem.itsType == ExecutableDeployItem.MODULE_BYTES) {
               var ediMB: ExecutableDeployItem_ModuleBytes = ExecutableDeployItem_ModuleBytes()
               ediMB = executableDeployItem.itsValue.get(0) as ExecutableDeployItem_ModuleBytes
               val argsString = ExecutableDeployItemHelper.argsToJsonString(ediMB.args)
               val innerJson: String = "{\"module_bytes\": \"" + ediMB.module_bytes + "\"," + argsString + "}"
               val retStr : String = "{\"ModuleBytes\": " + innerJson + "}"
               return retStr
           } else if (executableDeployItem.itsType == ExecutableDeployItem.STORED_CONTRACT_BY_HASH) {
                var ediContractHash:ExecutableDeployItem_StoredContractByHash = ExecutableDeployItem_StoredContractByHash()
               ediContractHash = executableDeployItem.itsValue.get(0) as ExecutableDeployItem_StoredContractByHash
               val argsString = ExecutableDeployItemHelper.argsToJsonString(ediContractHash.args)
               val innerJson: String  = "{\"hash\": \"" + ediContractHash.itsHash + "\",\"entry_point\": \"" + ediContractHash.entryPoint + "\"," + argsString + "}"
               val retStr : String = "{\"StoredContractByHash\":" + innerJson + "}"
               return retStr
           }  else if (executableDeployItem.itsType == ExecutableDeployItem.STORED_CONTRACT_BY_NAME) {
               var ediContractName:ExecutableDeployItem_StoredContractByName = ExecutableDeployItem_StoredContractByName()
               ediContractName = executableDeployItem.itsValue.get(0) as ExecutableDeployItem_StoredContractByName
               val argsString = ExecutableDeployItemHelper.argsToJsonString(ediContractName.args)
               val innerJson: String  = "{\"name\": \"" + ediContractName.itsName + "\",\"entry_point\": \"" + ediContractName.entryPoint + "\"," + argsString + "}"
               val retStr : String = "{\"StoredContractByName\":" + innerJson + "}"
               return retStr
           } else if (executableDeployItem.itsType == ExecutableDeployItem.STORED_VERSIONED_CONTRACT_BY_HASH) {
               var ediVersionedContractHash:ExecutableDeployItem_StoredVersionedContractByHash = ExecutableDeployItem_StoredVersionedContractByHash()
               ediVersionedContractHash = executableDeployItem.itsValue.get(0) as ExecutableDeployItem_StoredVersionedContractByHash
               val argsString = ExecutableDeployItemHelper.argsToJsonString(ediVersionedContractHash.args)
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
               var ediVersionedContractName:ExecutableDeployItem_StoredVersionedContractByName = ExecutableDeployItem_StoredVersionedContractByName()
               ediVersionedContractName = executableDeployItem.itsValue.get(0) as ExecutableDeployItem_StoredVersionedContractByName
               val argsString = ExecutableDeployItemHelper.argsToJsonString(ediVersionedContractName.args)
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
               var transfer: ExecutableDeployItem_Transfer = ExecutableDeployItem_Transfer()
               transfer = executableDeployItem.itsValue.get(0) as ExecutableDeployItem_Transfer
               val argsString = ExecutableDeployItemHelper.argsToJsonString(transfer.args)
               val innerJson: String = "{" + argsString + "}"
               val retStr : String = "{\"Transfer\": " + innerJson + "}"
               return retStr
           }
           return ConstValues.INVALID_VALUE
        }
        fun argsToJsonString(args: RuntimeArgs) :String{
            var ret:String = ""
            val totalArgs:Int = args.listNamedArg.size - 1
            var counter:Int = 0
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