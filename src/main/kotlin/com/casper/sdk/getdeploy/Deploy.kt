package com.casper.sdk.getdeploy

import com.casper.sdk.CasperUtils
import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem
import com.casper.sdk.serialization.DeploySerializeHelper
import com.casper.sdk.serialization.ExecutableDeployItemSerializationHelper
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing Deploy information */
class Deploy{
   var approvals:  MutableList<Approval> = mutableListOf()
   var hash:  String = ""
   var header:  DeployHeader = DeployHeader()
   var payment:  ExecutableDeployItem = ExecutableDeployItem()
   var session:  ExecutableDeployItem = ExecutableDeployItem()
   companion object {
      /** This function parse the JsonArray (taken from server RPC method call) to get the list of Approval object */
      fun  fromJsonToListApprovals(from: JsonArray) :  MutableList<Approval> {
         val ret: MutableList<Approval> = mutableListOf()
         val totalApproval: Int = from.count()
         if(totalApproval > 0) {
            for(i in 0..totalApproval-1) {
               val oneApproval: Approval = Approval.fromJsonToApproval(from[i] as JsonObject)
               ret.add(oneApproval)
            }
         }
         return ret
      }
      // This function counts the deploy hash based on the the Deploy header serialization
      // Just take the serialization of the deploy header, then use blake2b256 over the deploy header serialization.
      fun getDeployHash(deploy: Deploy) : String {
         val deployHeaderSerialization:String = DeploySerializeHelper.serializeForHeader(deploy.header)
         val deployHash:String = CasperUtils.getBlake2bFromStr(deployHeaderSerialization)
         return deployHash
      }
      // This function counts the deploy body hash based on the serialization of the deploy payment and session
      // The flow is:
      // 1. take the deploy payment serialization
      // 2. take the deploy session serialization
      // 3. make the concatenation of deploy payment serialization and deploy session serialization
      // 4. take the blake2b256 hash over the concatenation string.
      fun getBodyHash(deploy: Deploy): String {
         val paymentSerialization: String =
            ExecutableDeployItemSerializationHelper.serializeForExecutableDeployItem(deploy.payment)
         val sessionSerialization =
            ExecutableDeployItemSerializationHelper.serializeForExecutableDeployItem(deploy.session)
          val deployBodyHash: String = CasperUtils.getBlake2bFromStr(paymentSerialization + sessionSerialization)
         return deployBodyHash
      }
   }
}