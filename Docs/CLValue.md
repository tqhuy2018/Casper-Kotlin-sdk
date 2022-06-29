# Kotlin version of CLType primitives

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

Map,  Tuple2 will take 2  items:  innerCLType1, innerCLType2

Tuple3 will take 3 items:  innerCLType1,  innerCLType2,  innerCLType3


These innerCLType variables are lateinit var, which means that they can be initialized or not, depends on the CLType. For example if the CLType is primitive, then the 3 variable innerCLType1, innerCLType2, innerCLType3 is not used and not initialized.

If the CLType is List, then the innerCLType1 is used in initialized, while innerCLType2 and innerCLType3 is not.

If the CLType is Map, then the innerCLType1, innerCLType2 are in used and initialized, while innerCLType3 is not.


#### Examples of declaring the CLType object for some types:

Declaration for a CLType of type Bool:

 ```Kotlin
 val clType = CLType()
 clType.itsTypeStr = ConstValues.CLTYPE_BOOL
 ```

Declaration for a CLType of type Option(U512):

 ```Kotlin
 val clType = CLType()
 clType.itsTypeStr = ConstValues.CLTYPE_OPTION
 clType.innerCLType1 = CLType()
 clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_U512
 ```


Declaration for a CLType of type Map(String,String):

```Kotlin
val clType = CLType()
clType.itsTypeStr = ConstValues.CLTYPE_MAP
clType.innerCLType1 = CLType()
clType.innerCLType2 = CLType()
clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_STRING
clType.innerCLType2.itsTypeStr = ConstValues.CLTYPE_STRING
```

### CLParsed in detail 

The "parsed" is wrapped in CLParsed class, which is declared in  "CLParsed.kt" file under package "com.casper.sdk.clvalue". The CLParsed class stores all information need when you want to declare a CLParsed object, and also this class provides functions to turn JSON object to CLParsed object and supporter function such as function to check if the CLParsed hold pure value of CLType object or with hold value of recursive CLType object inside its body.

The main properties of the CLParsed object are:

```Kotlin
var itsValueInStr: String = ""
var itsCLType: CLType = CLType()
lateinit var innerParsed1: CLParsed
lateinit var innerParsed2: CLParsed
lateinit var innerParsed3: CLParsed
var itsArrayValue: MutableList<CLParsed> = mutableListOf()
```

In which the property "itsCLType" is to hold CLType of the CLParsed object, which can be 1 among 23 possible value from "Bool", "I32","I64", "U8" ... to "Tuple1", "Tuple2", "Tuple3" and "Any".
 
The property "itsValueInStr" is to hold value of CLParsed that doesn't contain recursive CLParsed inside its body

The property "itsArrayValue" is to hold value of List and FixedList elements
 
The property "innerParsed1" is to hold the inner CLParsed object for the following CLType: Tuple1, Option

The properties "innerParsed1" and "innerParsed2" are to hold the inner CLParsed for the following CLType: Map, Result, Tuple2

The properties "innerParsed1", "innerParsed2" and "innerParsed3" are to hold the inner CLParsed for the following CLType: Tuple3

#### Here are some examples of declaring the CLParsed object for some types: 

To declare for a CLParsed of type Bool with value "true":

```Kotlin
val clParse = CLParsed()
clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_BOOL
clParse.itsValueInStr = "true"
```

To declare for a CLParsed of type U8 with value "12":

```Kotlin
val clParse = CLParsed()
clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_U8
clParse.itsValueInStr = "12"
```

To declare for a CLParsed of type U512 with value "999888666555444999887988887777666655556666777888999666999":

```Kotlin
val clParse = CLParsed()
clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_U512
clParse.itsValueInStr = "999888666555444999887988887777666655556666777888999666999"
```

To declare for a CLParsed of type Option(NULL)

```Kotlin
val clParse = CLParsed()
clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_OPTION
clParse.itsValueInStr = ConstValues.VALUE_NULL
```

To declare for a CLParsed of type Option(U32(10))

```Kotlin
clParse.innerParsed1 = CLParsed()
clParse.itsValueInStr = ""
clParse.innerParsed1.itsCLType = CLType()
clParse.innerParsed1.itsCLType.itsTypeStr = ConstValues.CLTYPE_U32
clParse.innerParsed1.itsValueInStr = "10"
```
