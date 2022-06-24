package com.casper.sdk

/** Class built for storing Constants used in the SDK */
class ConstValues {
    companion object {
        //For RPC methods
        const val RPC_GET_STATE_ROOT_HASH: String = "chain_get_state_root_hash"
        const val RPC_INFO_GET_PEERS: String = "info_get_peers"
        const val RPC_INFO_GET_DEPLOY: String = "info_get_deploy"
        const val RPC_INFO_GET_STATUS: String = "info_get_status"
        const val RPC_CHAIN_GET_BLOCK_TRANSFER: String = "chain_get_block_transfers"
        const val RPC_CHAIN_GET_BLOCK: String = "chain_get_block"
        const val RPC_CHAIN_GET_ERA: String = "chain_get_era_info_by_switch_block"
        const val RPC_STATE_GET_ITEM: String = "state_get_item"
        const val RPC_STATE_GET_DICTIONARY: String = "state_get_dictionary_item"
        const val RPC_STATE_GET_BALANCE: String = "state_get_balance"
        const val RPC_STATE_GET_AUTION_INFO: String = "state_get_auction_info"
        const val RPC_PUT_DEPLOY: String = "account_put_deploy"
        //For test net and main net
        const val TESTNET_URL: String = "https://node-clarity-testnet.make.services/rpc"
        const val MAINNET_URL: String = "https://node-clarity-mainnet.make.services/rpc"
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
        const val CLPARSE_RESULT_OK     = "Ok"
        const val CLPARSE_RESULT_ERR    = "Err"
        //NULL value
        const val  VALUE_NULL           = "NULL_VALUE"
        const val INVALID_VALUE         = "INVALID_VALUE"
        const val NULL_VALUE_JSON       = "NULL"
        //ExecutionResult
        const val EXECUTION_RESULT_SUCCESS = "Success"
        const val EXECUTION_RESULT_FAILURE     = "Failure"
        //OpKind
        const val OPKIND_READ = "Read"
        const val OPKIND_WRITE = "Write"
        const val OPKIND_ADD = "Add"
        const val OPKIND_NOOP = "NoOp"
        //Transform
        const val  TRANSFORM_IDENTITY                   = "Identity"
        const val  TRANSFORM_WRITE_CLVALUE              = "WriteCLValue"
        const val  TRANSFORM_WRITE_ACCOUNT              = "WriteAccount"
        const val  TRANSFORM_WRITE_CONTRACT_WASM        = "WriteContractWasm"
        const val  TRANSFORM_WRITE_CONTRACT             = "WriteContract"
        const val  TRANSFORM_WRITE_CONTRACT_PACKAGE     = "WriteContractPackage"
        const val  TRANSFORM_WRITE_DEPLOY_INFO          = "WriteDeployInfo"
        const val  TRANSFORM_WRITE_ERA_INFO             = "WriteEraInfo"
        const val  TRANSFORM_WRITE_TRANSFER             = "WriteTransfer"
        const val  TRANSFORM_WRITE_BID                  = "WriteBid"
        const val  TRANSFORM_WRITE_WITHDRAW             = "WriteWithdraw"
        const val  TRANSFORM_ADD_INT32                  = "AddInt32"
        const val  TRANSFORM_ADD_UINT64                 = "AddUInt64"
        const val  TRANSFORM_ADD_UINT128                = "AddUInt128"
        const val  TRANSFORM_ADD_UINT256                = "AddUInt256"
        const val  TRANSFORM_ADD_UINT512                = "AddUInt512"
        const val  TRANSFORM_ADD_KEY                    = "AddKeys"
        const val  TRANSFORM_FAILURE                    = "Failure"
        //StoredValue enum
        const val STORED_VALUE_CLVALUE                  = "CLValue"
        const val STORED_VALUE_ACCOUNT                  = "Account"
        const val STORED_VALUE_CONTRACT_WASM            = "ContractWasm"
        const val STORED_VALUE_CONTRACT                 = "Contract"
        const val STORED_VALUE_CONTRACT_PACKAGE         = "ContractPackage"
        const val STORED_VALUE_TRANSFER                 = "Transfer"
        const val STORED_VALUE_DEPLOY_INFO              = "DeployInfo"
        const val STORED_VALUE_ERA_INFO                 = "EraInfo"
        const val STORED_VALUE_BID                      = "Bid"
        const val STORED_VALUE_WITHDRAW                 = "Withdraw"
        //DictionaryIdentifier
        const val DI_ACCOUNT_NAMED_KEY                  = "AccountNamedKey"
        const val DI_CONTRACT_NAMED_KEY                 = "ContractNamedKey"
        const val DI_UREF                               = "URef"
        const val DI_DICTIONARY                         = "Dictionary"
        //For CLParse to Json, used for account_put_deploy RPC call
        val PARSED_FIXED_STRING: String = "!!!!!___PARSED___!!!!!"
        //For Crypto file name - read from Pem file
        val PEM_READ_PRIVATE_ED25519 : String = "Ed25519/KotlinEd25519PrivateKey.pem"
        val PEM_READ_PRIVATE_SECP256k1 : String = "Secp256k1/KotlinSecp256k1PrivateKey.pem"
        val PEM_READ_PUBLIC_ED25519 : String = "Ed25519/WriteSwiftPublicKeyEd25519.pem"
        val PEM_READ_PRIVATE2_ED25519 : String = "Ed25519/WriteSwiftPrivateKeyEd25519.pem"
        val PEM_READ_PUBLIC_SECP256k1 : String = "Secp256k1/writePublicSecp256k1.pem"
        val PEM_READ_PRIVATE2_SECP256k1 : String = "Secp256k1/writePrivateSecp256k1.pem"
        //For write to pem file
        val PEM_WRITE_PRIVATE_ED25519 : String = "Ed25519/writePrivateEd25519.pem"
        val PEM_WRITE_PUBLIC_ED25519 : String = "Ed25519/writePublicEd25519.pem"
        val PEM_WRITE_PRIVATE_SEC256K1 : String = "Secp256k1/writePrivateSecp256k1.pem"
        val PEM_WRITE_PUBLIC_SECP256K1 : String = "Secp256k1/writePublicSecp256k1.pem"

        //For account_put_deploy error message when can't send a deploy
        val PUT_DEPLOY_ERROR_MESSAGE : String = "ERROR_PUT_DEPLOY"


    }
}