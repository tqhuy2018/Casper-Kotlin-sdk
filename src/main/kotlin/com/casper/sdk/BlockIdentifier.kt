package com.casper.sdk
enum class BlockIdentifierType {
    HASH,HEIGHT,NONE
}
class BlockIdentifier {
    var blockHash:String = ""
    var blockHeight:ULong  = 0u
    var blockType:BlockIdentifierType = BlockIdentifierType.NONE
    fun toJsonStr(methodName:String):String {
        when(blockType) {
            BlockIdentifierType.NONE -> {
                return """{"id" : 1,"method" : "${methodName}","params": [],"jsonrpc" : "2.0"}"""
            }
            BlockIdentifierType.HASH -> {
                return """{"id" : 1,"method" : "${methodName}","params": {"block_identifier" : {"Hash" :"${blockHash}"}},"jsonrpc" : "2.0"}"""
            }
            BlockIdentifierType.HEIGHT -> {
                return """{"id" : 1,"method" : "${methodName}","params": {"block_identifier" : {"Height" :${blockHeight}}},"jsonrpc" : "2.0"}"""
            }
        }
     return ""
    }
}