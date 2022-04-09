package com.casper.sdk.getdeploy.ExecutableDeployItem

import net.jemzart.jsonkraken.values.JsonObject

class ExecutableDeployItem_ModuleBytes {
        var module_bytes:String = ""
        var args:RuntimeArgs = RuntimeArgs()
//static function
        companion object{
                fun fromJsonToObj(jsonObj: JsonObject): ExecutableDeployItem_ModuleBytes {
                       var ret:ExecutableDeployItem_ModuleBytes = ExecutableDeployItem_ModuleBytes()
                       return ret
                }
        }
        init {
                println("Init ModuleBytes")
        }
        constructor() {

        }
}
