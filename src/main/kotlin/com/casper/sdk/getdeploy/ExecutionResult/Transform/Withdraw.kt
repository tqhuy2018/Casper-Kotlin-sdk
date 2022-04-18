package com.casper.sdk.getdeploy.ExecutionResult.Transform

import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class Withdraw {
    var listUnbondingPurse: MutableList<UnbondingPurse> = mutableListOf()
    companion object {
        fun  fromJsonArrayToWithdraw(from: JsonArray): Withdraw {
            var ret: Withdraw = Withdraw()
            var totalElement = from.count()
            if(totalElement>0) {
                for(i in 0..totalElement-1) {
                    var oneUP: UnbondingPurse = UnbondingPurse.fromJsonToUnbondingPurse(from[i] as JsonObject)
                    ret.listUnbondingPurse.add(oneUP)
                }
            }
            return ret
        }
    }
}