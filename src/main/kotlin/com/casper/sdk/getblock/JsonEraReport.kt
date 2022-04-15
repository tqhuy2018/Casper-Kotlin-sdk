package com.casper.sdk.getblock

class JsonEraReport {
    var equivocators:MutableList<String> = mutableListOf() // List of PublicKey
    var inactiveValidators:MutableList<String> = mutableListOf() // List of PublicKey
    var rewards:MutableList<Reward> = mutableListOf() // List of Reward

}