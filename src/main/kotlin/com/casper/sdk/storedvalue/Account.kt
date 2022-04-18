package com.casper.sdk.storedvalue

import com.casper.sdk.getdeploy.ExecutionResult.Transform.NamedKey
import com.casper.sdk.getitem.ActionThresholds
import com.casper.sdk.getitem.AssociatedKey
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

class Account {
    var accountHash: String = ""
    var namedKeys: MutableList<NamedKey> = mutableListOf()
    var mainPurse: String = "" //URef
    var associatedKeys: MutableList<AssociatedKey> = mutableListOf()
    var actionThresholds:  ActionThresholds = ActionThresholds()
    companion object {
        fun fromJsonObjectToAccount(from: JsonObject):  Account {
            var ret:  Account = Account()
            ret.accountHash = from["account_hash"].toString()
            ret.mainPurse = from["main_purse"].toString()
            ret.actionThresholds =
                ActionThresholds.fromJsonObjectToActionThresholds(from["action_thresholds"] as JsonObject)
            val associatedKeysJson = from["associated_keys"]
            if(associatedKeysJson != null) {
                val associatedKeys = from["associated_keys"] as JsonArray
                var totalAK: Int = associatedKeys.count()-1
                if(totalAK >= 0) {
                    for(i in 0..totalAK) {
                        val oneAK = AssociatedKey.fromObjectToAssociatedKey(associatedKeys[i] as JsonObject)
                        ret.associatedKeys.add(oneAK)
                    }
                }
            }
            return ret
        }
    }
}