package com.casper.sdk.getblock

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing JsonEraReport information */
class JsonEraReport {
    var equivocators: MutableList<String> = mutableListOf() // List of PublicKey
    var inactiveValidators: MutableList<String> = mutableListOf() // List of PublicKey
    var rewards: MutableList<Reward> = mutableListOf() // List of Reward
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to JsonEraReport object */
        fun fromJsonToJsonEraReport(from: JsonObject): JsonEraReport {
            val ret = JsonEraReport()
            val equivocators = from["equivocators"] as JsonArray
            val inactiveValidators = from["inactive_validators"] as JsonArray
            val rewards = from["rewards"] as JsonArray
            val totalEquivocator: Int = equivocators.count() - 1
            val totalInactiveValidator: Int = inactiveValidators.count() - 1
            val totalReward: Int = rewards.count() - 1
            if(totalEquivocator>=0) {
                for(i in 0..totalEquivocator) {
                    ret.equivocators.add(equivocators[i] as String)
                }
            }
            if (totalInactiveValidator>0) {
                for(i in 0..totalInactiveValidator) {
                    ret.inactiveValidators.add(inactiveValidators[i] as String)
                }
            }
            if(totalReward >0) {
                for(i in 0 .. totalReward) {
                    val oneReward: Reward = Reward.fromJsonToReward(rewards[i] as JsonObject)
                    ret.rewards.add(oneReward)
                }
            }
            return ret
        }
    }
}