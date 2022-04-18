package com.casper.sdk.getdictionary

import com.casper.sdk.ConstValues

/** Class built for storing GetDictionaryItemParams, used to generate the parameter for state_get_dictionary_item RPC call */
class GetDictionaryItemParams {
    var stateRootHash: String = ""
    var dictionaryIdentifier: DictionaryIdentifier = DictionaryIdentifier()

    /** This function generate the parameter for the post method of the state_get_dictionary_item RPC call
     * Depends on what the DictionaryIdentifier enum type, corresponding parameter is generated */
    fun generateParameterStr(): String {
        var ret: String = ""
        when(dictionaryIdentifier.itsType) {
            ConstValues.DI_ACCOUNT_NAMED_KEY-> {
                val ank: DIAccountNamedKey = dictionaryIdentifier.itsValue[0] as DIAccountNamedKey
                ret = """{"method" :  "${ConstValues.RPC_STATE_GET_DICTIONARY}", "id" :  1, "params" : {"state_root_hash" :  "${this.stateRootHash}", "dictionary_identifier": {"AccountNamedKey": {"dictionary_name": "${ank.dictionaryName}", "key": "${ank.key}", "dictionary_item_key": "${ank.dictionaryItemKey}"}}}, "jsonrpc" :  "2.0"}"""
            }
            ConstValues.DI_CONTRACT_NAMED_KEY-> {
                val ank: DIContractNamedKey = dictionaryIdentifier.itsValue[0] as DIContractNamedKey
                ret = """{"method" :  "${ConstValues.RPC_STATE_GET_DICTIONARY}", "id" :  1, "params" : {"state_root_hash" :  "${this.stateRootHash}", "dictionary_identifier": {"ContractNamedKey": {"dictionary_name": "${ank.dictionaryName}", "key": "${ank.key}", "dictionary_item_key": "${ank.dictionaryItemKey}"}}}, "jsonrpc" :  "2.0"}"""
            }
            ConstValues.DI_DICTIONARY-> {
                val ank: DIDictionary = dictionaryIdentifier.itsValue[0] as DIDictionary
                ret = """{"method" :  "${ConstValues.RPC_STATE_GET_DICTIONARY}", "id" :  1, "params" : {"state_root_hash" :  "${this.stateRootHash}", "dictionary_identifier": {"Dictionary": "${ank.itsValue}"}}, "jsonrpc" :  "2.0"}"""
            }
            ConstValues.DI_UREF-> {
                val ank: DIURef = dictionaryIdentifier.itsValue[0] as DIURef
                ret = """{"method" :  "${ConstValues.RPC_STATE_GET_DICTIONARY}", "id" :  1, "params" : {"state_root_hash" :  "${this.stateRootHash}", "dictionary_identifier": {"URef": {"seed_uref": "${ank.seedUref}", "dictionary_item_key": "${ank.dictionaryItemKey}"}}}, "jsonrpc" :  "2.0"}"""
            }
        }
        return ret
    }

}