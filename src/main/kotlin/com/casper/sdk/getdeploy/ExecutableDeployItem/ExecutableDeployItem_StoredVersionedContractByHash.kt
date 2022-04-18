package com.casper.sdk.getdeploy.ExecutableDeployItem

/** Class built for storing ExecutableDeployItem enum of type StoredVersionedContractByHash */
class ExecutableDeployItem_StoredVersionedContractByHash {
    var itsHash: String = ""
    var entryPoint: String = ""
    var args: RuntimeArgs = RuntimeArgs()
    lateinit var version: ContractVersion
    var isVersionExisted: Boolean = false
}