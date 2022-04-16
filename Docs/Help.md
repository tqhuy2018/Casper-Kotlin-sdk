# Kotlin Casper SDK manual on classes and methods

## RPC Calls

The calling the RPC follow this sequence:

- Create the POST request with corresponding paramters for each methods

- Send the POST request to the Casper server (test net or main net or localhost) 

- Get the Json message back from the server. The message could either be error message or the json string representing the object need to retrieve. If you send wrong parameter, such as in "chain_get_state_root_hash" RPC call, if you send BlockIdentifier with too big block height (that does not exist) then you will get error message back from Casper server. If you send correct parameter, you will get expected json message for the data you need.

- Handle the data sent back from Casper server for the POST request. Depends on the RPC method call, the corresponding json data is sent back in type of [String:Value] form. The task of the SDK is to parse this json data and put in correct data type built for each RPC method.

## List of RPC methods:

1) [Get state root hash (chain_get_state_root_hash)](#i-get-state-root-hash)

2) [Get peer list (info_get_peers)](#ii-get-peers-list)

3) [Get Deploy (info_get_deploy)](#iii-get-deploy)

4) [Get Status (info_get_status)](#iv-get-status)

5) [Get Block transfer (chain_get_block_transfers)](#v-get-block-transfers)

6) [Get Block (chain_get_block)](#vi-get-block)

7) [Get Era by switch block (chain_get_era_info_by_switch_block)](#vii-get-era-info-by-switch-block)

8) [Get Item (state_get_item)](#vii-get-item)

9) [Get Dictionary item (state_get_dictionary_item)](#ix-get-dictionaray-item)

10) [Get balance (state_get_balance)](#x-get-balance)

11) [Get Auction info (state_get_auction_info)](#xi-get-auction-info)

12) Put Deploy (account_put_deploy) 

### I. Get State Root Hash  

The task is done in file "GetStateRootHashRPC.kotlin" in package "com.casper.sdk.getstateroothash"

#### 1. Method declaration

```Kotlin
@Throws(IllegalArgumentException:: class)
    fun getStateRootHash(parameterStr: String): String
```

#### 2. Input & Output: 

Input: parameterStr represents the json parameter needed to send along with the POST method to Casper server. This parameter is build based on the BlockIdentifier.

Output: the state root hash in form of Kotlin String data type

When call this method to get the state root hash, you need to declare a BlockIdentifier object and then assign the height or hash or just none to the BlockIdentifier. Then the BlockIdentifier is transfer to the jsonString parameter. The whole sequence can be seen as the following code:
1. Declare a BlockIdentifier and assign its value
```Kotlin
    val bi: BlockIdentifier = BlockIdentifier()
    bi.blockType = BlockIdentifierType.NONE;
    
    //or you can set the block attribute like this
    
    bi.blockType = BlockIdentifierType.HASH;
    bi.blockHash = "fe35810a3dcfbf853b9d3ac2445fe1fa4aaab047d881d95d9009dc257d396e7e"
   
  // or like this
   
   bi.blockType = BlockIdentifierType.HEIGHT
   bi.blockHeight = 3345u
   
   //then you generate the jsonString to call the getStateRootHashWithJsonParam function
    val parameter:String = bi.toJsonStr(ConstValues.RPC_GET_STATE_ROOT_HASH)
```
2. Use the jsonString to call the function:

```Kotlin
+(void) getStateRootHashWithJsonParam:(NSString*) jsonString 
```

Output: the actual output is retrieved within the function body of getStateRootHashWithJsonParam function:

```Kotlin
[HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_GET_STATE_ROOT_HASH];
```
From this the other method is called

```Kotlin
+(NSString*) fromJsonToStateRootHash:(NSDictionary*) nsData 
```

This function return the state_root_hash value.

#### 3. The Unit test file for GetStateRootHash is in file "GetStateRootHashTest.m". 

In Unit test, the GetStateRootHash is done within the following sequence:

1. Declare a BlockIdentifier and assign its atributes

```Kotlin
    BlockIdentifier * bi = [[BlockIdentifier alloc] init];
    bi.blockType = USE_NONE;
    
    //or you can set the block attribute like this
    
    //bi.blockType = USE_BLOCK_HASH;
   // [bi assignBlockHashWithParam:@"d16cb633eea197fec519aee2cfe050fe9a3b7e390642ccae8366455cc91c822e"];
   
   or like this
   
   //bi.blockType = USE_BLOCK_HEIGHT;
   // [bi assignBlockHeigthtWithParam:12345];
   
   //then you generate the jsonString to call the getStateRootHashWithJsonParam function
    NSString * jsonString = [bi toJsonStringWithMethodName:@"chain_get_state_root_hash"];
```
2. Call the function to get the state root hash

```Kotlin
[self getStateRootHashWithJsonParam:jsonString];
```

### II. Get Peers List  

The task is done in file "GetPeerResult.h" and "GetPeerResult.m"

#### 1. Method declaration

```Kotlin
+(void) getPeerResultWithJsonParam:(NSString*) jsonString;
```

#### 2. Input & Output: 

Input: NSString represents the json parameter needed to send along with the POST method to Casper server. This string is just simple as:

```Kotlin
{"params" : [],"id" : 1,"method":"info_get_peers","jsonrpc" : "2.0"}
```

The code under  function handler the getting of peerlist

```Kotlin
[HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_INFO_GET_PEERS];
```

From this, in HttpHandler class, the retrieve of PeerEntry List is done with this function:

```Kotlin
+(GetPeerResult*) fromJsonObjToGetPeerResult:(NSDictionary*) jsonDict;
```

- Output: List of peer defined in class GetPeersResult, which contain a list of PeerEntry - a class contain of nodeId and address.

#### 3. The Unit test file for GetPeerResult is in file "GetPeerResultTest.m"

The steps in doing the test.

1.Declare the json parameter to send to POST request

```Kotlin
NSString *jsonString = @"{\"params\" : [],\"id\" : 1,\"method\":\"info_get_peers\",\"jsonrpc\" : \"2.0\"}";
```
From the POST request, the json data is retrieved and stored in forJSONObject variable.

2. Get GetPeerResult from the forJSONObject variable

```Kotlin
GetPeerResult * gpr = [[GetPeerResult alloc] init];
        gpr = [GetPeerResult fromJsonObjToGetPeerResult:forJSONObject];
```

From this you can Log out the retrieved information, such as the following code Log out total peer and print address and node id for each peer.

```Kotlin
NSLog(@"Get peer result api_version:%@",gpr.api_version);
NSLog(@"Get peer result, total peer entry:%lu",[gpr.PeersMap count]);
NSLog(@"List of peer printed out:");
NSInteger totalPeer = [gpr.PeersMap count];
NSInteger  counter = 1;
for (int i = 0 ; i < totalPeer;i ++) {
    PeerEntry * pe = [[PeerEntry alloc] init];
    pe = [gpr.PeersMap objectAtIndex:i];
    NSLog(@"Peer number %lu address:%@ and node id:%@",counter,pe.address,pe.nodeID);
    counter = counter + 1;
}
```

### III. Get Deploy 

#### 1. Method declaration

The call for Get Deploy RPC method is done through this function in "GetDeployResult.m" file

```Kotlin
+(void) getDeployWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_INFO_GET_DEPLOY];
}
```

From this the GetDeployResult is retrieved through this function, also in "GetDeployResult.m" file

```Kotlin
+(GetDeployResult*) fromJsonDictToGetDeployResult:(NSDictionary*) fromDict  
```

#### 2. Input & Output: 

* For function 

```Kotlin
+(void) getDeployWithParams:(NSString*) jsonString
```

Input is the string of parameter sent to Http Post request to the RPC method, which in form of

```Kotlin
{"id" : 1,"method" : "info_get_deploy","params" : {"deploy_hash" : "6e74f836d7b10dd5db7430497e106ddf56e30afee993dd29b85a91c1cd903583"},"jsonrpc" : "2.0"}
```
To generate such string, you need to use GetDeployParams class, which declared in file "GetDeployParams.h" and "GetDeployParams.m"

Instantiate the GetDeployParams, then assign the deploy_hash to the object and use function generatePostParam to generate such parameter string like above.

Sample  code for this process


```Kotlin
GetDeployParams * item = [[GetDeployParams alloc]init];
item.deploy_hash = @"acb4d78cbb900fe91a896ea8a427374c5d600cd9206efae2051863316265f1b1";
NSString * paramStr = [item generatePostParam];
[GetDeployResult getDeployWithParams:paramStr];
```
Output: The ouput is handler in HttpHandler class and then pass to fromJsonDictToGetDeployResult function, described below:

* For function 

```Kotlin
+(GetDeployResult*) fromJsonDictToGetDeployResult:(NSDictionary*) fromDict  
```

Input: The NSDictionaray object represents the GetDeployResult object. This NSDictionaray is returned from the POST method when call the RPC method. Information is sent back as JSON data and from that JSON data the NSDictionary part represents the GetDeployResult is taken to pass to the function to get the Deploy information.

Output: The GetDeployResult which contains all information of the Deploy. From this result you can retrieve information of Deploy hash, Deploy header, Deploy session, payment, ExecutionResults.

### IV. Get Status

#### 1. Method declaration

The call for Get Status RPC method is done through this function in "GetStatusResult.m" file

```Kotlin
+(void) getStatusWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_INFO_GET_STATUS];
}
```

From this the GetStatusResult is retrieved through this function, also in "GetStatusResult.m" file

```Kotlin
+(GetStatusResult *) fromJsonDictToGetStatusResult:(NSDictionary*) jsonDict
```

#### 2. Input & Output: 

* For function 

```Kotlin
+(void) getStatusWithParams:(NSString*) jsonString
```

Input: a JsonString of value 
```Kotlin
{"params" : [],"id" : 1,"method":"info_get_status","jsonrpc" : "2.0"}
```

Output: The ouput is handler in HttpHandler class and then pass to fromJsonDictToGetStatusResult function, described below:

* For function 

```Kotlin
+(GetStatusResult *) fromJsonDictToGetStatusResult:(NSDictionary*) jsonDict
```

Input: The NSDictionaray object represents the GetStatusResult object. This NSDictionaray is returned from the POST method when call the RPC method. Information is sent back as JSON data and from that JSON data the NSDictionary part represents the GetStatusResult is taken to pass to the function to get the status information.

Output: The GetStatusResult which contains all information of the status. From this result you can retrieve information such as: api_version,chainspec_name,starting_state_root_hash,peers,last_added_block_info...

### V. Get Block Transfers

#### 1. Method declaration

The call for Get Block Transfers RPC method is done through this function in "GetBlockTransfersResult.m" file

```Kotlin
+(void) getBlockTransfersWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_CHAIN_GET_BLOCK_TRANSFERS];
}
```

From this the GetBlockTransfersResult is retrieved through this function, also in "GetBlockTransfersResult.m" file

```Kotlin
+(GetBlockTransfersResult *) fromJsonDictToGetBlockTransfersResult:(NSDictionary*) jsonDict
```

#### 2. Input & Output: 

* For function 

```Kotlin
+(void) getBlockTransfersWithParams:(NSString*) jsonString
```

Input: a JsonString of such value:
```Kotlin
{"method" : "chain_get_block_transfers","id" : 1,"params" : {"block_identifier" : {"Hash" :"d16cb633eea197fec519aee2cfe050fe9a3b7e390642ccae8366455cc91c822e"}},"jsonrpc" : "2.0"}
```

To generate such string, you need to use an object of type BlockIdentifier class, which declared in file "BlockIdentifier.h" and "BlockIdentifier.m"

Instantiate the BlockIdentifier, then assign the block with block hash or block height or just assign nothing to the object and use function "toJsonStringWithMethodName" of the "BlockIdentifier" class to generate such parameter string like above.

Sample  code for this process

```Kotlin
BlockIdentifier * bi = [[BlockIdentifier alloc] init];
bi.blockType = USE_BLOCK_HASH;
[bi assignBlockHashWithParam:@"d16cb633eea197fec519aee2cfe050fe9a3b7e390642ccae8366455cc91c822e"];
NSString * paramStr = [bi toJsonStringWithMethodName:@"chain_get_block"];
[GetBlockTransfersResult getBlockTransfersWithParams:paramStr];
```

Output: The ouput is handler in HttpHandler class and then pass to fromJsonDictToGetBlockTransfersResult function, described below:

* For function 

```Kotlin
+(GetBlockTransfersResult *) fromJsonDictToGetBlockTransfersResult:(NSDictionary*) jsonDict
```

Input: The NSDictionaray object represents the GetBlockTransfersResult object. This NSDictionaray is returned from the POST method when call the RPC method. Information is sent back as JSON data and from that JSON data the NSDictionary part represents the GetBlockTransfersResult is taken to pass to the function to get the block transfers information.

Output: The GetBlockTransfersResult which contains all information of the Block Transfers. From this result you can retrieve information such as: api_version,block_hash, list of transfers. (Transfer is wrap in class Transfer.h and all information of Transfer can retrieve from this result).

### VI. Get Block 

#### 1. Method declaration

The call for Get Block Transfers RPC method is done through this function in "GetBlockResult.m" file

```Kotlin
+(void) getBlockWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_CHAIN_GET_BLOCK];
}
```

From this the GetBlockResult is retrieved through this function, also in "GetBlockResult.m" file

```Kotlin
+(GetBlockResult*) fromJsonDictToGetBlockResult:(NSDictionary *) jsonDict
```

#### 2. Input & Output: 

* For function 

```Kotlin
+(void) getBlockWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_CHAIN_GET_BLOCK];
}
```

Input: a JsonString of such value:
```Kotlin
{"method" : "chain_get_block","id" : 1,"params" : {"block_identifier" : {"Hash" :"d16cb633eea197fec519aee2cfe050fe9a3b7e390642ccae8366455cc91c822e"}},"jsonrpc" : "2.0"}
```

To generate such string, you need to use an object of type BlockIdentifier class, which declared in file "BlockIdentifier.h" and "BlockIdentifier.m"

Instantiate the BlockIdentifier, then assign the block with block hash or block height or just assign nothing to the object and use function "toJsonStringWithMethodName" of the "BlockIdentifier" class to generate such parameter string like above.

Sample  code for this process

```Kotlin
BlockIdentifier * bi = [[BlockIdentifier alloc] init];
bi.blockType = USE_BLOCK_HASH;
[bi assignBlockHashWithParam:@"d16cb633eea197fec519aee2cfe050fe9a3b7e390642ccae8366455cc91c822e"];
NSString * paramStr = [bi toJsonStringWithMethodName:@"chain_get_block"];
[GetBlockResult getBlockWithParams:paramStr];
```

Output: The ouput is handler in HttpHandler class and then pass to fromJsonDictToGetBlockResult function, described below:

* For function 

```Kotlin
+(GetBlockResult *) fromJsonDictToGetBlockResult:(NSDictionary*) jsonDict
```

Input: The NSDictionaray object represents the GetBlockResult object. This NSDictionaray is returned from the POST method when call the RPC method. Information is sent back as JSON data and from that JSON data the NSDictionary part represents the GetBlockResult is taken to pass to the function to get the block information.

Output: The GetBlockResult which contains all information of the block. From this result you can retrieve information such as: api_version,JsonBlock object(in which you can retrieve information such as: blockHash, JsonBlockHeader,JsonBlockBody, list of proof)

### VII. Get Era Info By Switch Block

#### 1. Method declaration

The call for Get Era Info RPC method is done through this function in "GetEraInfoResult.m" file

```Kotlin
+(void) getEraInfoWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_CHAIN_GET_ERA_BY_SWITCH_BLOCK];
}
```

From this the GetEraInfoResult is retrieved through this function, also in "GetEraInfoResult.m" file

```Kotlin
+(GetEraInfoResult*) fromJsonDictToGetEraInfoResult:(NSDictionary*) fromDict
```

#### 2. Input & Output: 

* For function 

```Kotlin
+(void) getEraInfoWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_CHAIN_GET_ERA_BY_SWITCH_BLOCK];
}
```

Input: a JsonString of such value:
```Kotlin
{"method" : "chain_get_era_info_by_switch_block","id" : 1,"params" : {"block_identifier" : {"Hash" :"d16cb633eea197fec519aee2cfe050fe9a3b7e390642ccae8366455cc91c822e"}},"jsonrpc" : "2.0"}
```

To generate such string, you need to use an object of type BlockIdentifier class, which declared in file "BlockIdentifier.h" and "BlockIdentifier.m"

Instantiate the BlockIdentifier, then assign the block with block hash or block height or just assign nothing to the object and use function "toJsonStringWithMethodName" of the "BlockIdentifier" class to generate such parameter string like above.

Sample  code for this process

```Kotlin
BlockIdentifier * bi = [[BlockIdentifier alloc] init];
bi.blockType = USE_BLOCK_HASH;
[bi assignBlockHashWithParam:@"d16cb633eea197fec519aee2cfe050fe9a3b7e390642ccae8366455cc91c822e"];
NSString * paramStr = [bi toJsonStringWithMethodName:@"chain_get_block"];
[GetEraInfoResult getEraInfoWithParams:paramStr];
```

Output: The ouput is handler in HttpHandler class and then pass to fromJsonDictToGetEraInfoResult function, described below:

* For function 

```Kotlin
+(GetEraInfoResult*) fromJsonDictToGetEraInfoResult:(NSDictionary*) fromDict 
```

Input: The NSDictionaray object represents the GetEraInfoResult object. This NSDictionaray is returned from the POST method when call the RPC method. Information is sent back as JSON data and from that JSON data the NSDictionary part represents the GetEraInfoResult is taken to pass to the function to get the era info information.

Output: The GetEraInfoResult which contains all information of the era info. From this result you can retrieve information such as: api_version, era_summary (in which you can retrieve information such as: block_hash, era_id, state_root_hash, merkle_proof, stored_value).


### VII. Get Item

#### 1. Method declaration

The call for Get Item RPC method is done through this function in "GetItemResult.m" file

```Kotlin
+(void) getItemWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_STATE_GET_ITEM];
}
```

From this the GetItemResult is retrieved through this function, also in "GetItemResult.m" file

```Kotlin
+(GetItemResult*) fromJsonDictToGetItemResult:(NSDictionary*) fromDict
```

#### 2. Input & Output: 

* For function 

```Kotlin
+(void) getItemWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_STATE_GET_ITEM];
}
```

Input: a JsonString of such value:
```Kotlin
{"method" : "state_get_item","id" : 1,"params" :{"state_root_hash" : "d360e2755f7cee816cce3f0eeb2000dfa03113769743ae5481816f3983d5f228","key":"withdraw-df067278a61946b1b1f784d16e28336ae79f48cf692b13f6e40af9c7eadb2fb1","path":[]},"jsonrpc" : "2.0"}
```

To generate such string, you need to use an object of type GetItemParams class, which declared in file "GetItemParams.h" and "GetItemParams.m"

Instantiate the GetItemParams, then assign the GetItemParams object with state_root_hash, key, and path, then use function "toJsonString" of the "GetItemParams" class to generate such parameter string like above.

Sample  code for this process:

```Kotlin
GetItemParams * item = [[GetItemParams alloc] init];
item.state_root_hash = @"d360e2755f7cee816cce3f0eeb2000dfa03113769743ae5481816f3983d5f228";
item.key = @"withdraw-df067278a61946b1b1f784d16e28336ae79f48cf692b13f6e40af9c7eadb2fb1";
NSString * paramStr = [item toJsonString];
[GetItemResult getItemWithParams:paramStr];
```

Output: The ouput is handler in HttpHandler class and then pass to fromJsonDictToGetItemResult function, described below:

* For function 

```Kotlin
+(GetItemResult*) fromJsonDictToGetItemResult:(NSDictionary*) fromDict 
```

Input: The NSDictionaray object represents the GetItemResult object. This NSDictionaray is returned from the POST method when call the RPC method. Information is sent back as JSON data and from that JSON data the NSDictionary part represents the GetItemResult is taken to pass to the function to get the item information.

Output: The GetItemResult which contains all information of the item. From this result you can retrieve information such as: api_version,merkle_proof, stored_value.

### IX. Get Dictionaray Item

#### 1. Method declaration

The call for Get Dictionary Item RPC method is done through this function in "GetDictionaryItemResult.m" file

```Kotlin
+(void) getDictionaryItemWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_STATE_GET_DICTIONARY_ITEM];
}
```

From this the GetDictionaryItemResult is retrieved through this function, also in "GetDictionaryItemResult.m" file

```Kotlin
+(GetDictionaryItemResult*) fromJsonDictToGetItemResult:(NSDictionary*) fromDict
```

#### 2. Input & Output: 

* For function 

```Kotlin
+(void) getDictionaryItemWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_STATE_GET_DICTIONARY_ITEM];
}
```

Input: a JsonString of such value:
```Kotlin
{"method" : "state_get_dictionary_item","id" : 1,"params" :{"state_root_hash" : "146b860f82359ced6e801cbad31015b5a9f9eb147ab2a449fd5cdb950e961ca8","dictionary_identifier":{"AccountNamedKey":{"dictionary_name":"dict_name","key":"account-hash-ad7e091267d82c3b9ed1987cb780a005a550e6b3d1ca333b743e2dba70680877","dictionary_item_key":"abc_name"}}},"jsonrpc" : "2.0"}
```

To generate such string, you need to use an object of type GetDictionaryItemParams class, which declared in file "GetDictionaryItemParams.h" and "GetDictionaryItemParams.m"

Instantiate the GetDictionaryItemParams, then assign the GetDictionaryItemParams object with state_root_hash and an DictionaryIdentifier value.
The DictionaryIdentifier can be 1 among 4 possible classes defined in folder "DictionaryIdentifierEnum".
When the state_root_hash and DictionaryIdentifier value are sets, use function "toJsonString" of the "GetDictionaryItemParams" class to generate such parameter string like above.

Sample  code for this process, with DictionaryIdentifier of type AccountNamedKey

```Kotlin
GetDictionaryItemParams * itemParam = [[GetDictionaryItemParams alloc] init];
itemParam.state_root_hash = @"146b860f82359ced6e801cbad31015b5a9f9eb147ab2a449fd5cdb950e961ca8";
DictionaryIdentifier_AccountNamedKey * item = [[DictionaryIdentifier_AccountNamedKey alloc] init];
item.key = @"account-hash-ad7e091267d82c3b9ed1987cb780a005a550e6b3d1ca333b743e2dba70680877";
item.dictionary_name = @"dict_name";
item.dictionary_item_key = @"abc_name";
itemParam.dictionaryIdentifierType = @"AccountNamedKey";
itemParam.innerDict = [[NSMutableArray alloc] init];
[itemParam.innerDict addObject:item];
NSString * jsonStr = [itemParam toJsonString];
[GetDictionaryItemResult getDictionaryItem:jsonStr];
```

Output: The ouput is handler in HttpHandler class and then pass to fromJsonDictToGetItemResult function, described below:

* For function 

```Kotlin
+(GetDictionaryItemResult*) fromJsonDictToGetItemResult:(NSDictionary*) fromDict 
```

Input: The NSDictionaray object represents the GetDictionaryItemResult object. This NSDictionaray is returned from the POST method when call the RPC method. Information is sent back as JSON data and from that JSON data the NSDictionary part represents the GetDictionaryItemResult is taken to pass to the function to get the dictionary item information.

Output: The GetDictionaryItemResult which contains all information of the dictionary item. From this result you can retrieve information such as: api_version,dictionary_key, merkle_proof,stored_value.

### X. Get Balance

#### 1. Method declaration

The call for Get Balance RPC method is done through this function in "GetBalanceResult.m" file

```Kotlin
+(void) getBalanceWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_STATE_GET_BALANCE];
}
```

From this the GetBalanceResult is retrieved through this function, also in "GetBalanceResult.m" file

```Kotlin
+(GetBalanceResult*) fromJsonDictToGetBalanceResult:(NSDictionary*) fromDict
```

#### 2. Input & Output: 

* For function 

```Kotlin
+(void) getBalanceWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_STATE_GET_BALANCE];
}
```

Input: a JsonString of such value:
```Kotlin
{"method" : "state_get_balance","id" : 1,"params" :{"state_root_hash" : "8b463b56f2d124f43e7c157e602e31d5d2d5009659de7f1e79afbd238cbaa189","purse_uref":"uref-be1dc0fd639a3255c1e3e5e2aa699df66171e40fa9450688c5d718b470e057c6-007"},"jsonrpc" : "2.0"}
```

To generate such string, you need to use an object of type GetBalanceParams class, which declared in file "GetBalanceParams.h" and "GetBalanceParams.m"

Instantiate the GetBalanceParams, then assign the GetBalanceParams with state_root_hash and purse_uref then use function "toJsonString" of the "GetBalanceParams" class to generate such parameter string like above.

Sample  code for this process

```Kotlin
 GetBalanceParams * param = [[GetBalanceParams alloc] init];
 param.state_root_hash = @"8b463b56f2d124f43e7c157e602e31d5d2d5009659de7f1e79afbd238cbaa189";
 param.purse_uref = @"uref-be1dc0fd639a3255c1e3e5e2aa699df66171e40fa9450688c5d718b470e057c6-007";
 NSString * jsonStr = [param toJsonString];
 [GetBalanceResult getBalance:jsonStr];
```

Output: The ouput is handler in HttpHandler class and then pass to fromJsonDictToGetBalanceResult function, described below:

* For function 
```Kotlin
+(GetBalanceResult*) fromJsonDictToGetBalanceResult:(NSDictionary*) fromDict 
```

Input: The NSDictionaray object represents the GetBalanceResult object. This NSDictionaray is returned from the POST method when call the RPC method. Information is sent back as JSON data and from that JSON data the NSDictionary part represents the GetBalanceResult is taken to pass to the function to get the balance information.

Output: The GetBalanceResult which contains all information of the balance. From this result you can retrieve information such as: api_version,balance_value, merkle_proof.

### XI. Get Auction Info

#### 1. Method declaration

The call for Get Auction RPC method is done through this function in "GetAuctionInfoResult.m" file

```Kotlin
+(void) getAuctionWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_STATE_GET_AUCTION_INFO];
}
```

From this the GetAuctionInfoResult is retrieved through this function, also in "GetAuctionInfoResult.m" file

```Kotlin
+(GetAuctionInfoResult*) fromJsonDictToGetAuctionResult:(NSDictionary*) fromDict
```

#### 2. Input & Output: 

* For function 

```Kotlin
+(void) getAuctionWithParams:(NSString*) jsonString {
    [HttpHandler handleRequestWithParam:jsonString andRPCMethod:CASPER_RPC_METHOD_STATE_GET_AUCTION_INFO];
}
```

Input: a JsonString of such value:
```Kotlin
{"method" : "state_get_auction_info","id" : 1,"params" : {"block_identifier" : {"Hash" :"d16cb633eea197fec519aee2cfe050fe9a3b7e390642ccae8366455cc91c822e"}},"jsonrpc" : "2.0"}
```

To generate such string, you need to use an object of type BlockIdentifier class, which declared in file "BlockIdentifier.h" and "BlockIdentifier.m"

Instantiate the BlockIdentifier, then assign the block with block hash or block height or just assign nothing to the object and use function "toJsonStringWithMethodName" of the "BlockIdentifier" class to generate such parameter string like above.

Sample  code for this process

```Kotlin
 BlockIdentifier * bi = [[BlockIdentifier alloc] init];
 bi.blockType = USE_BLOCK_HASH;
[bi assignBlockHashWithParam:@"d16cb633eea197fec519aee2cfe050fe9a3b7e390642ccae8366455cc91c822e"];
 NSString * paramStr = [bi toJsonStringWithMethodName:@"chain_get_block"];
[GetAuctionInfoResult getAuctionWithParams:paramStr];
```

Output: The ouput is handler in HttpHandler class and then pass to fromJsonDictToGetAuctionResult function, described below:

* For function 

```Kotlin
+(GetAuctionInfoResult*) fromJsonDictToGetAuctionResult:(NSDictionary*) fromDict 
```

Input: The NSDictionaray object represents the GetAuctionInfoResult object. This NSDictionaray is returned from the POST method when call the RPC method. Information is sent back as JSON data and from that JSON data the NSDictionary part represents the GetAuctionInfoResult is taken to pass to the function to get the aunction information.

Output: The GetAuctionInfoResult which contains all information of the aunction. From this result you can retrieve information such as: api_version,auction_state (in which you can retrieve information such as state_root_hash, block_height, list of JsonEraValidators).
