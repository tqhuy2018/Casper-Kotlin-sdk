package com.casper.sdk

class ConstValues {
    companion object {
        //For RPC methods
        const val RPC_GET_STATE_ROOT_HASH:String = "chain_get_state_root_hash"
        const val RPC_INFO_GET_PEERS:String = "info_get_peers"
        const val RPC_INFO_GET_DEPLOY:String = "info_get_deploy"
        const val RPC_INFO_GET_STATUS:String = "info_get_status"
        const val RPC_CHAIN_GET_BLOCK_TRANSFER:String = "chain_get_block_transfers"
        const val RPC_CHAIN_GET_BLOCK:String = "chain_get_block"
        const val RPC_CHAIN_GET_ERA:String = "chain_get_era_info_by_switch_block"
        const val RPC_STATE_GET_ITEM:String = "state_get_item"
        const val RPC_STATE_GET_DICTIONARY:String = "state_get_dictionary_item"
        const val RPC_STATE_GET_BALANCE:String = "state_get_balance"
        const val RPC_STATE_GET_AUTION_INFO:String = "state_get_auction_info"
        const val RPC_PUT_DEPLOY:String = "account_put_deploy"
        //For test net and main net
        const val TESTNET_URL:String = "https://node-clarity-testnet.make.services/rpc"
        const val MAINNET_URL:String = "https://node-clarity-mainnet.make.services/rpc"
        //For CLType - 23 possible values
        const val CLTYPE_BOOL           = "Bool"
        const val CLTYPE_I32            = "I32"
        const val CLTYPE_I64            = "I64"
        const val CLTYPE_U8             = "U8"
        const val CLTYPE_U32            = "U32"
        const val CLTYPE_U64            = "U64"
        const val CLTYPE_U128           = "U128"
        const val CLTYPE_U256           = "U256"
        const val CLTYPE_U512           = "U512"
        const val CLTYPE_UNIT           = "Unit"
        const val CLTYPE_STRING         = "String"
        const val CLTYPE_KEY            = "Key"
        const val CLTYPE_UREF           = "URef"
        const val CLTYPE_PUBLIC_KEY     = "PublicKey"
        const val CLTYPE_OPTION         = "Option"
        const val CLTYPE_LIST           = "List"
        const val CLTYPE_BYTEARRAY      = "ByteArray"
        const val CLTYPE_RESULT         = "Result"
        const val CLTYPE_MAP            = "Map"
        const val CLTYPE_TUPLE1         = "Tuple1"
        const val CLTYPE_TUPLE2         = "Tuple2"
        const val CLTYPE_TUPLE3         = "Tuple3"
        const val CLTYPE_ANY            = "Any"
    }
}