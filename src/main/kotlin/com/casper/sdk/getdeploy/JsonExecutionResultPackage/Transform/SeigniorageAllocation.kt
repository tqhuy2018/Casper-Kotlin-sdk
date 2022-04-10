package com.casper.sdk.getdeploy.JsonExecutionResultPackage.Transform

import com.casper.sdk.common.classes.U512Class

class SeigniorageAllocation {
    //can be either Validator or Delegator
    var itsType:String = ""
    var validatorPublicKey:String = ""
    var amount:U512Class = U512Class()
    //this field is for Delegator
    var delegatorPublicKey:String = ""

}