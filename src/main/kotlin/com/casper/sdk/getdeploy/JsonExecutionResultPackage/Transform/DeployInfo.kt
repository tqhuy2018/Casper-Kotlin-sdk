package com.casper.sdk.getdeploy.JsonExecutionResultPackage.Transform

import com.casper.sdk.common.classes.U512Class

class DeployInfo {
    var deployHash:String = ""//DeployHash
    var from:String = ""//AccountHash
    var source:String = ""//URef
    var gas:U512Class = U512Class()
    var transfer:MutableList<String> = mutableListOf() //TransferAddr list
}