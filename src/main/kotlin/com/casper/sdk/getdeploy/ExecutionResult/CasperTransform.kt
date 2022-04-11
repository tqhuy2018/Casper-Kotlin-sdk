package com.casper.sdk.getdeploy.ExecutionResult

import net.jemzart.jsonkraken.values.JsonObject

class CasperTransform {
    //itsType is 1 among 18 possible values
    var itsType:String = ""
    //itsValue hold the inner value such as CLValue, AccountHash,DeployInfo...
    var itsValue:MutableList<Any> = mutableListOf()

}