package com.casper.sdk.getdeploy.ExecutableDeployItem
/** Class built for storing ExecutableDeployItem enum of type StoredVersionedContractByName */
class ExecutableDeployItem_StoredVersionedContractByName {
    var itsName: String = ""
    var entryPoint: String = ""
    var args: RuntimeArgs = RuntimeArgs()
    lateinit var version: ContractVersion
    var isVersionExisted: Boolean = false
}