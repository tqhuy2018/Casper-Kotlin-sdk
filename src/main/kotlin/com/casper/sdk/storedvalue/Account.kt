package com.casper.sdk.storedvalue

import com.casper.sdk.getdeploy.ExecutionResult.Transform.NamedKey
import com.casper.sdk.getitem.ActionThresholds
import com.casper.sdk.getitem.AssociatedKey
import net.jemzart.jsonkraken.values.JsonArray
import net.jemzart.jsonkraken.values.JsonObject

/** Class built for storing Account information */
class Account {
    var accountHash: String = ""
    var namedKeys: MutableList<NamedKey> = mutableListOf()
    var mainPurse: String = "" //URef
    var associatedKeys: MutableList<AssociatedKey> = mutableListOf()
    var actionThresholds:  ActionThresholds = ActionThresholds()
    companion object {
        /** This function parse the JsonObject (taken from server RPC method call) to get the Account object */
        fun fromJsonObjectToAccount(from: JsonObject):  Account {
            val ret = Account()
            ret.accountHash = from["account_hash"].toString()
            ret.mainPurse = from["main_purse"].toString()
            ret.actionThresholds =
                ActionThresholds.fromJsonObjectToActionThresholds(from["action_thresholds"] as JsonObject)
            val associatedKeysJson = from["associated_keys"]
            if(associatedKeysJson != null) {
                val associatedKeys = from["associated_keys"] as JsonArray
                val totalAK: Int = associatedKeys.count()-1
                if(totalAK >= 0) {
                    for(i in 0..totalAK) {
                        val oneAK = AssociatedKey.fromObjectToAssociatedKey(associatedKeys[i] as JsonObject)
                        ret.associatedKeys.add(oneAK)
                    }
                }
            }
            // Get NamedKey
            val listNameKey = from["named_keys"]
            if(listNameKey != null) {
                val nameKeys = from["named_keys"] as JsonArray
                val totalNK: Int = nameKeys.count()-1
                if(totalNK >= 0) {
                    for(i in 0..totalNK) {
                        val oneNK = NamedKey.fromJsonObjectToNamedKey(nameKeys[i] as JsonObject)
                        ret.namedKeys.add(oneNK)
                    }
                }
            }
            return ret
        }
    }
}