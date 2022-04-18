package com.casper.sdk.getdeploy

import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem
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
         var ret: MutableList<Approval> = mutableListOf()
         var totalApproval: Int = from.count()
         if(totalApproval > 0) {
            for(i in 0..totalApproval-1) {
               var oneApproval: Approval = Approval.fromJsonToApproval(from[i] as JsonObject)
               ret.add(oneApproval)
            }
         }
         return ret
      }
   }
}