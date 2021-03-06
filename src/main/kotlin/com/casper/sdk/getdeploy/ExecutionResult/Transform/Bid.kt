package com.casper.sdk.getdeploy.ExecutionResult.Transform

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.toJsonString
import net.jemzart.jsonkraken.values.JsonObject
/** Class built for storing Bid information */
class Bid {
    var bondingPurse: String = ""
    var delegationRate: UByte = 0u
    var inactive: Boolean = false
    var delegators: MutableList<Delegator> = mutableListOf()
    var stakedAmount: U512Class = U512Class()
    var validatorPublicKey: String = ""
    lateinit var vestingSchedule: VestingSchedule//Optional value
    var isVestingScheduleExisted: Boolean = false
    @Suppress("UNCHECKED_CAST")
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the Bid object */
        fun fromJsonToBid(from: JsonObject): Bid {
            val ret = Bid()
            ret.bondingPurse = from["bonding_purse"].toString()
            val dr = from["delegation_rate"].toJsonString()
            ret.delegationRate = dr.toUByte()
            ret.inactive = from["inactive"].toString().toBoolean()
            ret.stakedAmount = U512Class.fromStringToU512(from["staked_amount"].toString())
            ret.validatorPublicKey = from["validator_public_key"].toString()
            val delegatorList: JsonObject = from["delegators"] as JsonObject
            val listKey: List<String> = delegatorList.keys.toList()
            val listValue: List<JsonObject> = delegatorList.values.toList() as List<JsonObject>
            val totalElement = listKey.count() - 1
            for(i in 0 .. totalElement) {
                val oneDelegator = Delegator.fromJsonToDelegator(listValue[i])
                oneDelegator.itsPublicKey = listKey[i]
                ret.delegators.add(oneDelegator)
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