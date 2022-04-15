package com.casper.sdk.storedvalue

import com.casper.sdk.getdeploy.ExecutionResult.Transform.NamedKey

class CasperContract {
    var contract_package_hash:String = ""
    var contract_wasm_hash:String = ""
    var entry_points:MutableList<EntryPoint> = mutableListOf()
    var named_keys:MutableList<NamedKey> = mutableListOf()
    var protocolVersion:String = ""
}