package com.casper.sdk.getdeploy

import com.casper.sdk.getdeploy.ExecutableDeployItem.ExecutableDeployItem

class Deploy(
   val approvals: List<Approval>,
   val hash: String,
   val header: Header,
   val payment: ExecutableDeployItem,
   val session: ExecutableDeployItem,
)