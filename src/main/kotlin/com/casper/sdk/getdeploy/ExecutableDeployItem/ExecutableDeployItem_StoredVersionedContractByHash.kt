package com.casper.sdk.getdeploy.ExecutableDeployItem

/** Class built for storing ExecutableDeployItem enum of type StoredVersionedContractByHash */
class ExecutableDeployItem_StoredVersionedContractByHash {
    var itsHash: String = ""
    var entryPoint: String = ""
    var args: RuntimeArgs = RuntimeArgs()
    var version: UInt = 0u
    var isVersionExisted: Boolean = false
}