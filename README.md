# CSPR-Kotlin-SDK

Kotlin SDK library for interacting with a CSPR node.

## What is CSPR-Kotlin-SDK?

SDK  to streamline the 3rd party Kotlin client integration processes. Such 3rd parties include exchanges & app developers. 

## System requirement

- The SDK is built with Kotlin language versioned 1.5

To build and test the SDK you need the flowing things installed in your computer:

- Java JDK version 11 or above, Java JDK 18 is suggested

- Maven latest version.

## Build and test

The package can be built and tested from IntelliJ IDEA

### Build and test in IntelliJ IDEA

Download the latest IntelliJ IDEA from https://www.jetbrains.com/idea/

The SDK is developed with IntelliJ IDEA 2021.3.3 (Community Edition). Then a Community Edition is good enough to test the SDK.

Download or clone the code from github,  then open it with IntelliJ IDEA.

#### Set up environment for ItelliJ IDEA

The first thing to do is to set up the SDK for the Project.

In IntelliJ IDEA hit "File-> Project Structure..." 

<img width="1440" alt="Screen Shot 2022-04-20 at 06 12 33" src="https://user-images.githubusercontent.com/94465107/164116163-5844aa6c-75e3-4a0a-a92f-d82578785b3a.png">

You will see this screen shown up

<img width="1440" alt="Screen Shot 2022-04-20 at 06 13 39" src="https://user-images.githubusercontent.com/94465107/164116186-e67ff39f-8fac-4ffc-aef3-0be8b955aed3.png">

Click on tab "SDKs" in the left panel to see the list of SDK being installed in your ItelliJ IDEA. You need to install the SDK from 11 or above, the suggested SDK is "openjdk-18".

<img width="1440" alt="Screen Shot 2022-04-20 at 06 17 16" src="https://user-images.githubusercontent.com/94465107/164116476-9a07e449-28d5-4751-bde9-8f1ddf607f6a.png">

By default, the SDKs list is just "Kotlin SDK". To install more SDK, hit the "+" button and choose "Download JDK ..."

<img width="1440" alt="Screen Shot 2022-04-20 at 06 26 28" src="https://user-images.githubusercontent.com/94465107/164117325-3a36cde3-791b-45a0-ba8d-20dee1fceb36.png">

Select your JDK version. You can choose any option in the list box, but the suggested version is 18, then hit the "Download" button

<img width="1440" alt="Screen Shot 2022-04-20 at 06 29 07" src="https://user-images.githubusercontent.com/94465107/164117560-135a3666-697e-4090-b5b2-af2b3b4d7352.png">

After the downloading is completed, you'll see the "openjdk-18" item (if you install JDK version 18) together with the Kotlin SDK.

Add more JDK version with the same step if you wish to test on more version of the JDK.

Next in the tab Project, choose the SDK for the project. In this case the choosen SDK is "openjdk-18"

<img width="1440" alt="Screen Shot 2022-04-20 at 06 31 20" src="https://user-images.githubusercontent.com/94465107/164117795-965a4138-2088-45aa-8087-5cfe171283e4.png">

Just do this for the SDK configuration, left the other configurations with default values. Make sure that the "Language level" is "SDK default" as in the image below.

<img width="1440" alt="Screen Shot 2022-04-20 at 09 17 17" src="https://user-images.githubusercontent.com/94465107/164136336-6900e4cd-bcca-41fd-97b7-f31e5aad76ee.png">


Click "Apply" and then "OK" to save the configruation. The next step is to import the necessary Jar files to the SDK.

#### Import jars file to the SDK

Get the jars file from this link https://drive.google.com/drive/folders/1oflaJ_1m3HX9NYI_5d0PuoT2WNGy6dPq?usp=sharing

Download all the jars file to your local computer, you will have two folders with name "bcutils" and "otherjars" containing the jar files that will be later imported to the SDK.

In IntelliJ IDEA, hit "File-> Project Structure ..."

<img width="1440" alt="Screen Shot 2022-06-28 at 10 15 14" src="https://user-images.githubusercontent.com/94465107/176084432-af7e4018-d3bc-4192-b2d4-d7b726a0ce97.png">

A new window appears, click on "Libraries" and then "Maven: net.jemzart:jsonkraken:1.2.0", then click the "+" button

<img width="1440" alt="Screen Shot 2022-06-28 at 10 16 45" src="https://user-images.githubusercontent.com/94465107/176084923-a25c72bb-091c-46df-9d9a-f8b3b4fce19f.png">

Browse to the 2 jar folders that you have already downloaded from the link https://drive.google.com/drive/folders/1oflaJ_1m3HX9NYI_5d0PuoT2WNGy6dPq?usp=sharing above. First choose "bcutils" folder

<img width="1440" alt="Screen Shot 2022-06-28 at 10 22 31" src="https://user-images.githubusercontent.com/94465107/176085572-fe6aab13-8d5e-4472-a7ec-575e8617f719.png">

Select all the files in the "bcutils" folder and click "Open" button

<img width="1440" alt="Screen Shot 2022-06-28 at 10 24 57" src="https://user-images.githubusercontent.com/94465107/176086250-6439a633-382f-457a-ab14-91234c9921e7.png">

You will see the jar files imported in the list

<img width="1440" alt="Screen Shot 2022-06-28 at 10 25 34" src="https://user-images.githubusercontent.com/94465107/176085755-2ab8155c-8510-4754-8f67-aeb066a9dcbf.png">

Next step is to import all the files in the "otherjars", click the "+" button again and browse to the "otherjars" folder

<img width="1440" alt="Screen Shot 2022-06-28 at 10 25 52" src="https://user-images.githubusercontent.com/94465107/176085871-c559a470-2854-4e5c-ad92-d85749573c16.png">

Choose all the files in the "otherjars" folder, then click "Open"

<img width="1440" alt="Screen Shot 2022-06-28 at 10 25 57" src="https://user-images.githubusercontent.com/94465107/176086026-e0026955-8677-4c69-b5bb-2970026c2d1e.png">

You will see that all the jars from the two folder "bcutils" and "otherjars" is now imported to the list

<img width="1440" alt="Screen Shot 2022-06-28 at 10 26 04" src="https://user-images.githubusercontent.com/94465107/176086072-d5d91da3-00d7-4876-a727-35bca7966947.png">

Click "Apply" and then "OK" button.

You will need to restart IntelliJ IDEA to make the Jar files work. In IntelliJ IDEA click "File->Invalidate Caches ..."

<img width="1440" alt="Screen Shot 2022-06-28 at 10 38 00" src="https://user-images.githubusercontent.com/94465107/176086946-5f97004c-6ac5-487f-97ef-0597579a0e1e.png">

A windows will appear, check all the check box then click "Invalidate and Restart"

<img width="466" alt="Screen Shot 2022-06-28 at 10 39 28" src="https://user-images.githubusercontent.com/94465107/176086984-583e19be-42bb-46dc-92c7-028836a653a3.png">

The IntelliJ IDEA will close and restart. You may have to wait for 1 or several minutes to wait for IntelliJ IDEA to be ready for the changes. Then you can build and test the SDK.

#### Build the sdk: 

To build the project, in IntelliJ IDEA, hit "Build->Build Project" as in the image below

<img width="1440" alt="Screen Shot 2022-04-18 at 20 31 31" src="https://user-images.githubusercontent.com/94465107/163815577-87bddf54-9489-4cb0-b627-e8a95680f3cf.png">

You can see the Build log by clicking the "Build" tab in the bottom of the IntelliJ IDEA window

<img width="1440" alt="Screen Shot 2022-06-28 at 10 48 01" src="https://user-images.githubusercontent.com/94465107/176087982-f6fb9d02-d954-45e9-b54a-b37379d9fd63.png">


#### Test the sdk:

To run the test, in the Project panel that show the files and folders, under the folder "test", right click on the "kotlin" folder (folder marked with green in the below image), hit "Run 'All Tests'".

<img width="1440" alt="Screen Shot 2022-04-19 at 17 19 50" src="https://user-images.githubusercontent.com/94465107/163983309-711a270a-33c9-4d31-bb51-664b6904047e.png">


The test list is in the right panel, the result is in the left panel, as shown in the image:

<img width="1440" alt="Screen Shot 2022-04-19 at 17 18 28" src="https://user-images.githubusercontent.com/94465107/163983055-31730b51-c730-4878-99a4-c7aa104b3ce5.png">

# Information for Secp256k1, Ed25519 Key Wrapper and Put Deploy

## Key wrapper specification:

The Key wrapper do the following work:(for both Secp256k1 and Ed25519):

- (PrivateKey,PublicKey) generation

- Sign message 

- Verify message

- Read PrivateKey/PublicKey from PEM file

- Write PrivateKey/PublicKey to PEM file

The key wrapper is used in account_put_deploy RPC method to generate approvals signature based on deploy hash.

The Crypto task for Ed25519 and Secp256k1 use BouncyCastle library at this address https://bouncycastle.org/, with the Jar files from this address 

https://bouncycastle.org/latest_releases.html

The Ed25519 crypto task is implemented in file Ed25519Handle under package com.casper.sdk.crypto

The Secp256k1 crypto task is implemented in file Secp256k1Handle under package com.casper.sdk.crypto

# Kotlin version of CLType primitives, Casper Domain Specific Objects and Serialization
 
 ## CLType primitives
 
 The CLType is an enum variables, defined at this address: (for Rust version)
 
 https://docs.rs/casper-types/1.4.6/casper_types/enum.CLType.html
 
 In Kotlin, the CLType when put into usage is part of a CLValue object.
 
 To more detail, a CLValue holds the information like this:
 
 ```Kotlin
 {
"bytes":"0400e1f505"
"parsed":"100000000"
"cl_type":"U512"
}
```

or


 ```Kotlin
 {
"bytes":"010000000100000009000000746f6b656e5f7572695000000068747470733a2f2f676174657761792e70696e6174612e636c6f75642f697066732f516d5a4e7a337a564e7956333833666e315a6762726f78434c5378566e78376a727134796a4779464a6f5a35566b"
"parsed":[
          [
             {
             "key":"token_uri"
             "value":"https://gateway.pinata.cloud/ipfs/QmZNz3zVNyV383fn1ZgbroxCLSxVnx7jrq4yjGyFJoZ5Vk"
             }
          ]
]
"cl_type":{
        "List":{
           "Map":{
           "key":"String"
           "value":"String"
           }
      }
}
 ```
The CLValue is built up with 3 elements: cl_type, parsed and bytes.
In the examples above, 
 * For the first example:
   - The cl_type is: U512 
   - The parsed is: "100000000" 
   - The bytes is: "0400e1f505"  

 * For the second example: 
   - The cl_type is: List(Map(String,String))
   - The parsed is: 
 
 ```Kotlin
 "[
          [
             {
             "key":"token_uri"
             "value":"https://gateway.pinata.cloud/ipfs/QmZNz3zVNyV383fn1ZgbroxCLSxVnx7jrq4yjGyFJoZ5Vk"
             }
          ]
       ]"
  ```
  
     - The bytes is: "010000000100000009000000746f6b656e5f7572695000000068747470733a2f2f676174657761792e70696e6174612e636c6f75642f697066732f516d5a4e7a337a564e7956333833666e315a6762726f78434c5378566e78376a727134796a4779464a6f5a35566b"
     
### CLType in detail


In Kotlin SDK the "cl_type" is wrapped in CLType class, which is declared in  "CLType.kt" file under package "com.casper.sdk.clvalue". The CLType class stores all information need when you want to declare a CLType, and also this class provides functions to turn JSON object to CLType object and supporter function such as function to check if the CLType hold pure value of CLType with recursive CLType inside its body.

The main properties of the CLType object are:

 ```Kotlin
  var itsTypeStr: String = ""
  lateinit var innerCLType1: CLType
  lateinit var innerCLType2: CLType
  lateinit var innerCLType3:  CLType
 ```
 
 In which itsTypeStr is the type of the CLType in String,  can be Bool,  String,  I32,  I64,  List,  Map, ...
 
 The CLType is divided into 2 types: Primitive and Compound CLType.
 
 Primitive CLType is the CLType with no recursive CLType declaration inside its body. The following CLType is primitive: Bool, I32, I64, U8, U32, U64, U128, U256, U512, String, Unit, Key, URef, PublicKey, ByteArray, Any
 
 Compound CLType is the CLType with recursive CLType declaration inside its body. The following CLType is compound: List, Map, Option, Result, Tuple1, Tuple2, Tuple 3.
 
  innerCLType to hold value for the following type: 
  
  Option,  Result,  Tuple1 will take only 1 item:  innerCLType1
  
  Map,  Tuple2 will take 2  item:  innerCLType1, innerCLType2
  
  Tuple3 will take 3 item:  innerCLType1,  innerCLType2,  innerCLType3
  
  
  These innerCLType variables are lateinit var, which means that they can be initialized or not, depends on the CLType. For example if the CLType is primitive, then the 3 variable innerCLType1, innerCLType2, innerCLType3 is not used and not initialized.
  
  If the CLType is List, then the innerCLType1 is used in initialized, while innerCLType2 and innerCLType3 is not.
  
  If the CLType is Map, then the innerCLType1, innerCLType2 are in used and initialized, while innerCLType3 is not.

# Documentation for classes and methods

* [List of classes and methods](./Docs/Help.md#list-of-rpc-methods)

  -  [Get State Root Hash](/Docs/Help.md#i-get-state-root-hash)

  -  [Get Peer List](./Docs/Help.md#ii-get-peers-list)

  -  [Get Deploy](./Docs/Help.md#iii-get-deploy)
  
  -  [Get Status](./Docs/Help.md#iv-get-status)
  
  -  [Get Block Transfers](./Docs/Help.md#v-get-block-transfers)
  
  -  [Get Block](./Docs/Help.md#vi-get-block)
  
  -  [Get Era Info By Switch Block](./Docs/Help.md#vii-get-era-info-by-switch-block)
  
  -  [Get Item](./Docs/Help.md#vii-get-item)
  
  -  [Get Dictionary Item](./Docs/Help.md#ix-get-dictionaray-item)
  
  -  [Get Balance](./Docs/Help.md#x-get-balance)
  
  -  [Get Auction Info](./Docs/Help.md#xi-get-auction-info)
  
  -  [Put deploy](./Docs/Help.md#xii-put-deploy)
 
# SDK Usage

To use the SDK as external libary in other project, please refer to this document for more detail.

[Casper Kotlin SDK usage](./Docs/Usage.md)
