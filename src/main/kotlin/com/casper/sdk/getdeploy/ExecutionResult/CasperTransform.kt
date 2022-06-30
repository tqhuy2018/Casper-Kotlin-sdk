package com.casper.sdk.getdeploy.ExecutionResult

/** Class built for storing Transform information */

class CasperTransform {
    //itsType is 1 among 18 possible values
    //detail values can be seen at this address https://docs.rs/casper-types/1.5.0/casper_types/enum.Transform.html
    var itsType: String = ""
    //itsValue hold the inner value such as CLValue,  AccountHash, DeployInfo...
    var itsValue: MutableList<Any> = mutableListOf()

}