package com.casper.sdk
enum class BlockIdentifierType {
    HASH, HEIGHT, NONE
}
/**
This class is for storing information of POST parameter when call for RPC method such as chain_get_block or chain_get_state_root_hash or chain_get_block_transfers
 */
class BlockIdentifier {
    var blockHash: String = ""
    var blockHeight: ULong  = 0u
    var blockType: BlockIdentifierType = BlockIdentifierType.NONE
    /**This function build up the json parameter string,  used for sending the POST method for RPC methods such as chain_get_block or chain_get_state_root_hash or chain_get_block_transfers

     * For example with RPC method "chain_get_state_root_hash" if USE_BLOCK_TYPE = USE_NONE the generated output will be like this

    {"params" :  [], "id" :  1, "method": "chain_get_state_root_hash", "jsonrpc" :  "2.0"}

     * For example with RPC method "chain_get_block",   if USE_BLOCK_TYPE = USE_BLOCK_HASH and  BlockIdentifier object with blockHash = @"d16cb633eea197fec519aee2cfe050fe9a3b7e390642ccae8366455cc91c822e",  the generated output will be like this: 

    {"method" :  "chain_get_block", "id" :  1, "params" :  {"block_identifier" :  {"Hash" : "d16cb633eea197fec519aee2cfe050fe9a3b7e390642ccae8366455cc91c822e"}}, "jsonrpc" :  "2.0"}


     * For example with RPC method "chain_get_state_root_hash",   if USE_BLOCK_TYPE = USE_BLOCK_HEIGHT and  BlockIdentifier object with blockHeight = 100,  the generated output will be like this: 

    {"method" :  "chain_get_state_root_hash", "id" :  1, "params" :  {"block_identifier" :  {"Height" : 100}}, "jsonrpc" :  "2.0"}

     */
    fun toJsonStr(methodName: String): String {
        when(blockType) {
            BlockIdentifierType.NONE -> {
                return """{"id" :  1, "method" :  "${methodName}", "params":  [], "jsonrpc" :  "2.0"}"""
            }
            BlockIdentifierType.HASH -> {
                return """{"id" :  1, "method" :  "${methodName}", "params":  {"block_identifier" :  {"Hash" : "${blockHash}"}}, "jsonrpc" :  "2.0"}"""
            }
            BlockIdentifierType.HEIGHT -> {
                return """{"id" :  1, "method" :  "${methodName}", "params":  {"block_identifier" :  {"Height" : ${blockHeight}}}, "jsonrpc" :  "2.0"}"""
            }
        }
    }
}