package com.casper.sdk.serialization

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLParsed
import com.casper.sdk.clvalue.CLType
import com.casper.sdk.clvalue.CLValue
import com.casper.sdk.getdeploy.ExecutableDeployItem.*
/*
This class do the serialization for the ExecutableDeployItem object.
 */
class ExecutableDeployItemSerializationHelper {
   companion object {
       //This function do the serialization for RuntimeArgs object
       fun  serializeForRuntimeArgs(input: RuntimeArgs) : String {
           var ret:String
           val totalNamedArg:Int = input.listNamedArg.size
           //If args is emtpy, just return "00000000" - same as U32 serialization for 0 (0 item)
           if(totalNamedArg == 0) {
               return "00000000"
           }
           //if args is not empty, first take u32 serialization for the number of args
           val clParse = CLParsed()
           clParse.itsCLType = CLType()
           clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_U32
           clParse.itsValueInStr = totalNamedArg.toString()
           ret = CLParseSerialization.serializeFromCLParse(clParse)
           //then take serialization for each NamedArgs
           for(i in 0..totalNamedArg-1) {
               val oneNA:NamedArg = input.listNamedArg.get(i)
               //Serialization for NamedArg.itsName, just the String serialize on itsName
               val clParseName:CLParsed = CLParsed()
               clParseName.itsCLType = CLType()
               clParseName.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
               clParseName.itsValueInStr = oneNA.itsName
               val nameSerialization:String = CLParseSerialization.serializeFromCLParse(clParseName)
               //Serialization for NamedArg.itsCLValue
               //The flow of doing this: First serialize the CLValue parse - let call parsedSerialization
               //Serialize CLType - let call it clTypeSerialization
               //result = U32.serialize(parsedSerialization.length) + parsedSerialization + clTypeSerialization
               val clValue:CLValue = oneNA.clValue
               val parsedSerialization:String = CLParseSerialization.serializeFromCLParse(clValue.itsParse)
               val parseLength:Int = parsedSerialization.length/2
               val parseU32:CLParsed = CLParsed()
               parseU32.itsCLType = CLType()
               parseU32.itsCLType.itsTypeStr = ConstValues.CLTYPE_U32
               parseU32.itsValueInStr = parseLength.toString()
               val parseLengthSerialization:String = CLParseSerialization.serializeFromCLParse(parseU32)
               val clTypeSerialization: String = CLTypeSerialization.serializeForCLType(clValue.itsCLType)
               val clValueSerialization:String = parseLengthSerialization + parsedSerialization + clTypeSerialization
               val oneNASerialization: String =  nameSerialization + clValueSerialization
               ret = ret + oneNASerialization
           }
           return  ret
       }
       //This function do the serialization for ExecutableDeployItem, which can be among 1 of 6 value type:
       // ModuleBytes, StoredContractByHash, StoredContractByName, StoredVersionedContractByHash, StoredVersionedContractByName, Transfer
       fun serializeForExecutableDeployItem(input:ExecutableDeployItem) : String {
           var ret:String = ""
           val realItem = input.itsValue.get(0)
           when(realItem) {
               //Serialization for ModuleBytes
               is ExecutableDeployItem_ModuleBytes -> {
                   //prefix 00 for ExecutableDeployItem as type ModuleBytes
                   ret = "00"
                   //serialization for module_bytes
                   //if module_bytes is blank, just return U32 serialization for value 0, which equals to "00000000"
                   //if module_bytes is not blank, ret = "00" + moduleBytes.StringSerialization + args.Serialization
                   var moduleBytesSerialization:String
                   if (realItem.module_bytes == "") {
                       moduleBytesSerialization = "00000000"
                   } else {
                       val clParseString : CLParsed = CLParsed()
                       clParseString.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
                       clParseString.itsValueInStr = realItem.module_bytes
                       moduleBytesSerialization = CLParseSerialization.serializeFromCLParse(clParseString)
                   }
                   ret = ret + moduleBytesSerialization
                   val runtimeArgsSerialization:String = ExecutableDeployItemSerializationHelper.serializeForRuntimeArgs(realItem.args)
                   ret = ret + runtimeArgsSerialization
                   return ret
               }
               is ExecutableDeployItem_StoredContractByHash -> {
                   //prefix 01 for ExecutableDeployItem as type StoredContractByHash
                   //the result = "01" + hash + String.Serialize(entry_point) + args.Serialization
                   ret = "01"
                   val clParseString : CLParsed = CLParsed()
                   clParseString.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
                   clParseString.itsValueInStr = realItem.entryPoint
                   val entryPointSerialization : String = CLParseSerialization.serializeFromCLParse(clParseString)
                   val argsSerialization: String = ExecutableDeployItemSerializationHelper.serializeForRuntimeArgs(realItem.args)
                   ret = ret + realItem.itsHash + entryPointSerialization + argsSerialization
                   return  ret
               }
               is ExecutableDeployItem_StoredContractByName -> {
                   //prefix 02 for ExecutableDeployItem as type StoredContractByName
                   //the result = "02" + String.Serialize(name) + String.Serialize(entry_point) + Args.Serialization
                   ret = "02"
                   val clParseName:CLParsed = CLParsed()
                   clParseName.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
                   clParseName.itsValueInStr = realItem.itsName
                   val nameSerialization: String = CLParseSerialization.serializeFromCLParse(clParseName)
                   val clParseString : CLParsed = CLParsed()
                   clParseString.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
                   clParseString.itsValueInStr = realItem.entryPoint
                   val entryPointSerialization : String = CLParseSerialization.serializeFromCLParse(clParseString)
                   val argsSerialization: String = ExecutableDeployItemSerializationHelper.serializeForRuntimeArgs(realItem.args)
                   ret = ret + nameSerialization + entryPointSerialization + argsSerialization
                   return  ret
               }
               is ExecutableDeployItem_StoredVersionedContractByHash -> {
                   //prefix 03 for ExecutableDeployItem as type StoredVersionedContractByHash
                   //the result = "03" + hash + Option(U32).Serialize(version) + String.Serialize(entry_point) + Args.Serialized
                   ret = "03"
                   //Get the Option(U32).Serialize(version)
                   val parseOption:CLParsed = CLParsed()
                   parseOption.itsCLType.itsTypeStr = ConstValues.CLTYPE_OPTION
                   if(realItem.isVersionExisted) {
                       val parseU32:CLParsed = CLParsed()
                       parseU32.itsCLType.itsTypeStr = ConstValues.CLTYPE_U32
                       parseU32.itsValueInStr = realItem.version.toString()
                       parseOption.innerParsed1 = CLParsed()
                       parseOption.innerParsed1 = parseU32
                   } else {
                       parseOption.itsValueInStr = ConstValues.VALUE_NULL
                   }
                   val  versionSerialization:String = CLParseSerialization.serializeFromCLParse(parseOption)
                   //Get entry_point serialization, just String Serialization over the entry_point
                   val clParseEntryPoint : CLParsed = CLParsed()
                   clParseEntryPoint.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
                   clParseEntryPoint.itsValueInStr = realItem.entryPoint
                   val entryPointSerialization : String = CLParseSerialization.serializeFromCLParse(clParseEntryPoint)
                   //Get the args serialization
                   val argsSerialization: String = ExecutableDeployItemSerializationHelper.serializeForRuntimeArgs(realItem.args)
                   ret = ret + realItem.itsHash + versionSerialization + entryPointSerialization + argsSerialization
                   return  ret
               }
               is ExecutableDeployItem_StoredVersionedContractByName-> {
                   ret = "04"
                   //prefix 04 for ExecutableDeployItem as type StoredVersionedContractByName
                   //the result = "04" + String.Serialize(name) + Option(U32).Serialize(version) + String.Serialize(entry_point) + Args.Serialized
                   val clParseName:CLParsed = CLParsed()
                   clParseName.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
                   clParseName.itsValueInStr = realItem.itsName
                   val nameSerialization: String = CLParseSerialization.serializeFromCLParse(clParseName)
                   //Get the Option(U32).Serialize(version)
                   val parseOption:CLParsed = CLParsed()
                   parseOption.itsCLType.itsTypeStr = ConstValues.CLTYPE_OPTION
                   if(realItem.isVersionExisted) {
                       val parseU32:CLParsed = CLParsed()
                       parseU32.itsCLType.itsTypeStr = ConstValues.CLTYPE_U32
                       parseU32.itsValueInStr = realItem.version.toString()
                       parseOption.innerParsed1 = CLParsed()
                       parseOption.innerParsed1 = parseU32
                   } else {
                       parseOption.itsValueInStr = ConstValues.VALUE_NULL
                   }
                   val  versionSerialization:String = CLParseSerialization.serializeFromCLParse(parseOption)
                   val clParseEntryPoint : CLParsed = CLParsed()
                   clParseEntryPoint.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
                   clParseEntryPoint.itsValueInStr = realItem.entryPoint
                   val entryPointSerialization : String = CLParseSerialization.serializeFromCLParse(clParseEntryPoint)
                   //Get the args serialization
                   val argsSerialization: String = ExecutableDeployItemSerializationHelper.serializeForRuntimeArgs(realItem.args)
                   ret = ret + nameSerialization + versionSerialization + entryPointSerialization + argsSerialization
                   return  ret
               }
               is ExecutableDeployItem_Transfer -> {
                   ret = "05"
                   //prefix 05 for ExecutableDeployItem as type Trasfer
                   //the result = "05" + Args.Serialized
                   //Get the args serialization
                   val argsSerialization: String = ExecutableDeployItemSerializationHelper.serializeForRuntimeArgs(realItem.args)
                   ret = ret + argsSerialization
                   return  ret
               }
           }
           return ret

       }
   }
}