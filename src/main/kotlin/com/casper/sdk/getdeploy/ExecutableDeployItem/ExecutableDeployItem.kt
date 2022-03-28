package com.casper.sdk.getdeploy.ExecutableDeployItem

class ExecutableDeployItem {
        var itsType:String=  ""
        var itsValue:MutableList<Any> = mutableListOf()

        companion object{
                const val MODULE_BYTES                  = "ModuleBytes"
                const val  STORED_CONTRACT_BY_HASH      = "StoredContractByHash"
        }
}
