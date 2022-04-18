package com.casper.sdk.getdeploy.ExecutionResult

/** Class built for storing Transform information */

class CasperTransform {
    //itsType is 1 among 18 possible values
    var itsType: String = ""
    //itsValue hold the inner value such as CLValue,  AccountHash, DeployInfo...
    var itsValue: MutableList<Any> = mutableListOf()

}