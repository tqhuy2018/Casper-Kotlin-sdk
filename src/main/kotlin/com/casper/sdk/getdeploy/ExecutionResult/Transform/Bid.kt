package com.casper.sdk.getdeploy.ExecutionResult.Transform

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class Bid {
    var bondingPurse:String = ""
    var delegationRate:UByte = 0u
    var inactive:Boolean = false
    var delegators:MutableList<Delegator> = mutableListOf()
    var stakedAmount:U512Class = U512Class()
    var validatorPublicKey:String = ""
    lateinit var vestingSchedule:VestingSchedule//Optional value
    var isVestingScheduleExisted:Boolean = false
    companion object {
        fun fromJsonToBid(from:JsonObject):Bid {
            var ret:Bid = Bid()
            ret.bondingPurse = from["bonding_purse"].toString()
            ret.delegationRate = from["delegation_rate"].toString().toUByte()
            ret.inactive = from["inactive"].toString().toBoolean()
            ret.stakedAmount = U512Class.fromStringToU512(from["staked_amount"].toString())
            ret.validatorPublicKey = from["validator_public_key"].toString()
            val delegatorList:JsonArray = from["delegators"] as JsonArray
            var totalDelegator:Int = delegatorList.count()
            if(totalDelegator > 0) {
                for (i in 0..totalDelegator-1) {
                    var oneDelegator : Delegator = Delegator.fromJsonToDelegator(delegatorList[i] as JsonObject)
                    ret.delegators.add(oneDelegator);
                }
            }
            val vsJson = from["vesting_schedule"].toJsonString()
            if(vsJson != "null") {
                ret.isVestingScheduleExisted = true
                ret.vestingSchedule = VestingSchedule()
                ret.vestingSchedule = VestingSchedule.fromJsonToVestingSchedule(from["vesting_schedule"] as JsonObject)
            }
            return ret
        }
    }
}