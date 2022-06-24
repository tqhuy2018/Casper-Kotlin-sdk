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
      fun getDeployHash(deploy: Deploy) : String {
         val deployHeaderSerialization:String = DeploySerializeHelper.serializeForHeader(deploy.header)
         val deployHash:String = CasperUtils.getBlake2bFromStr(deployHeaderSerialization)
         return deployHash
      }
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