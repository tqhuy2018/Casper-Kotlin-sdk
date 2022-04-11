package com.casper.sdk.getdeploy.ExecutionResult.Transform

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject

class Delegator {
    var bondingPurse:String = ""
    var stakedAmount:U512Class = U512Class()
    var validatorPublicKey:String = ""
    var delegatorPublicKey:String = ""
    lateinit var vestingSchedule:VestingSchedule//Optional value
    var isVestingScheduleExisted:Boolean = false
    companion object {
        fun fromJsonToDelegator(from: JsonObject):Delegator {
            var ret:Delegator = Delegator()
            ret.bondingPurse = from["bonding_purse"].toString()
            ret.stakedAmount = U512Class.fromStringToU512(from["staked_amount"].toString())
            ret.validatorPublicKey = from["validator_public_key"].toString()
            ret.delegatorPublicKey = from["delegator_public_key"].toString()
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