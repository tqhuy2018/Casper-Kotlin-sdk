package com.casper.sdk

class ConstValues {
    companion object {
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
        const val TESTNET_URL:String = "https://node-clarity-testnet.make.services/rpc"
        const val MAINNET_URL:String = "info_get_status"

    }
}