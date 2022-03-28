package com.casper.sdk.getdeploy

import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem

class Deploy{
   var approvals: MutableList<Approval> = mutableListOf()
   var hash: String = ""
   var header: DeployHeader = DeployHeader()
   var payment: ExecutableDeployItem = ExecutableDeployItem()
   var session: ExecutableDeployItem = ExecutableDeployItem()
}