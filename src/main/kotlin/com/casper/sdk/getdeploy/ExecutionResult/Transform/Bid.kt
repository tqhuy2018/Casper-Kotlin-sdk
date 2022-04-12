package com.casper.sdk.getdeploy.ExecutionResult.Transform

import com.casper.sdk.common.classes.U512Class
import net.jemzart.jsonkraken.get
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
            var dr = from["delegation_rate"].toJsonString()
            ret.delegationRate = dr.toUByte()
            ret.inactive = from["inactive"].toString().toBoolean()
            ret.stakedAmount = U512Class.fromStringToU512(from["staked_amount"].toString())
            ret.validatorPublicKey = from["validator_public_key"].toString()
            val delegatorList:JsonObject = from["delegators"] as JsonObject
            var listKey:List<String> = delegatorList.keys.toList()
            var listValue:List<JsonObject> = delegatorList.values.toList() as List<JsonObject>
            var totalElement = listKey.count() - 1
            for(i in 0 .. totalElement) {
                var oneDelegator:Delegator = Delegator()
                oneDelegator = Delegator.fromJsonToDelegator(listValue[i])
                oneDelegator.itsPublicKey = listKey[i].toString()
                ret.delegators.add(oneDelegator)
            }
           // println("Value number 0:${delegatorList.values[0].toJsonString()}")

            /*var totalKey = delegatorList.count()-1
            for(i in 0 ..totalKey) {
                var oneDelegator:Delegator = Delegator()
                oneDelegator = Delegator.fromJsonToDelegator(delegatorList.values.get(i) as JsonObject)
                oneDelegator.itsPublicKey = delegatorList.keys.get(i) as String
                ret.delegators.add(oneDelegator)
            }*/
            println("About to get VestingSchedule")
            val vsJson = from["vesting_schedule"].toJsonString()
            if(vsJson != "null") {
                ret.isVestingScheduleExisted = true
                ret.vestingSchedule = VestingSchedule()
                ret.vestingSchedule = VestingSchedule.fromJsonToVestingSchedule(from["vesting_schedule"] as JsonObject)
            } else {
                println("In bid, vestingSchedule is NULL")
            }
            return ret
        }
    }
}