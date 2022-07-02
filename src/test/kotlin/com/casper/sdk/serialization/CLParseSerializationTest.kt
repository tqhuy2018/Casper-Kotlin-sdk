package com.casper.sdk.serialization

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLParsed
import com.casper.sdk.clvalue.CLType
import org.junit.jupiter.api.Test

internal class CLParseSerializationTest {
    @Test
    fun clParseSerialize() {
        val clParse = CLParsed()
        //Bool assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_BOOL
        clParse.itsValueInStr = "true"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "01")
        clParse.itsValueInStr = "false"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "00")
        clParse.itsValueInStr = "No"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == ConstValues.INVALID_VALUE)

        //U8 assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_U8
        clParse.itsValueInStr = "0"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "00")
        clParse.itsValueInStr = "9"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "09")
        clParse.itsValueInStr = "219"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "db")
        clParse.itsValueInStr = "255"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "ff")

        //I32 assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_I32
        clParse.itsValueInStr = "0"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "00000000")
        clParse.itsValueInStr = "-1024"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "00fcffff")
        clParse.itsValueInStr = "-3333"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "fbf2ffff")
        clParse.itsValueInStr = "1000"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "e8030000")
        clParse.itsValueInStr = "1000"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "e8030000")
        clParse.itsValueInStr = "5005"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "8d130000")
        clParse.itsValueInStr = "12764"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "dc310000")
        clParse.itsValueInStr = "-12369"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "afcfffff")

        //I64 assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_I64
        clParse.itsValueInStr = "0"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0000000000000000")
        clParse.itsValueInStr = "-1024"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "00fcffffffffffff")
        clParse.itsValueInStr = "1000"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "e803000000000000")
        clParse.itsValueInStr = "-123456789"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "eb32a4f8ffffffff")
        clParse.itsValueInStr = "-56789"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "2b22ffffffffffff")
        clParse.itsValueInStr = "56789"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "d5dd000000000000")

        //U32 assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_U32
        clParse.itsValueInStr = "1024"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "00040000")
        clParse.itsValueInStr = "5531024"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "90655400")
        clParse.itsValueInStr = "0"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "00000000")
        clParse.itsValueInStr = "334455"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "771a0500")
        clParse.itsValueInStr = "4099"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "03100000")

        //U64 assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_U64
        clParse.itsValueInStr = "1024"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0004000000000000")
        clParse.itsValueInStr = "33009900995531024"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "10d1e87e54467500")
        clParse.itsValueInStr = "0"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0000000000000000")
        clParse.itsValueInStr = "300000"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "e093040000000000")
        clParse.itsValueInStr = "123456789"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "15cd5b0700000000")

        //U128 assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_U128
        clParse.itsValueInStr = "123456789101112131415"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0957ff1ada959f4eb106")
        clParse.itsValueInStr = "1024"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "020004")
        clParse.itsValueInStr = "0"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "00")

        //U256 assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_U256
        clParse.itsValueInStr = "999988887777666655556666777888999"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0ee76837d2ca215879f7bc5ca24d31")
        clParse.itsValueInStr = "2048"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "020008")
        clParse.itsValueInStr = "0"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "00")

        //U512 assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_U512
        clParse.itsValueInStr = "999888666555444999887988887777666655556666777888999666999"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "1837f578fca55492f299ea354eaca52b6e9de47d592453c728")
        clParse.itsValueInStr = "4096"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "020010")
        clParse.itsValueInStr = "0"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "00")
        clParse.itsValueInStr = "100000000"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0400e1f505")

        //String assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        clParse.itsValueInStr = "Hello, World!"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0d00000048656c6c6f2c20576f726c6421")
        clParse.itsValueInStr = "lWJWKdZUEudSakJzw1tn"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "140000006c574a574b645a5545756453616b4a7a7731746e")
        clParse.itsValueInStr = "S1cXRT3E1jyFlWBAIVQ8"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "140000005331635852543345316a79466c57424149565138")
        clParse.itsValueInStr = "123456789123456789123456789123456789123456789123456789"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "36000000313233343536373839313233343536373839313233343536373839313233343536373839313233343536373839313233343536373839")
        clParse.itsValueInStr = "target"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "06000000746172676574")
        clParse.itsValueInStr = "Weather"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0700000057656174686572")
        clParse.itsValueInStr = "aa"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "020000006161")
        clParse.itsValueInStr = "I want to know, really!"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "17000000492077616e7420746f206b6e6f772c207265616c6c7921")

        //Unit assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_UNIT
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "")
        //Key assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_KEY
        clParse.itsValueInStr = "account-hash-d0bc9cA1353597c4004B8F881b397a89c1779004F5E547e04b57c2e7967c6269"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "00d0bc9cA1353597c4004B8F881b397a89c1779004F5E547e04b57c2e7967c6269")
        clParse.itsValueInStr = "hash-8cf5e4acf51f54eb59291599187838dc3bc234089c46fc6ca8ad17e762ae4401"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "018cf5e4acf51f54eb59291599187838dc3bc234089c46fc6ca8ad17e762ae4401")
        clParse.itsValueInStr = "uref-be1dc0fd639a3255c1e3e5e2aa699df66171e40fa9450688c5d718b470e057c6-007"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "02be1dc0fd639a3255c1e3e5e2aa699df66171e40fa9450688c5d718b470e057c607")
        //Negative test, test with invalid key value
        clParse.itsValueInStr = "abcdef"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == ConstValues.INVALID_VALUE)

        //URef assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_UREF
        clParse.itsValueInStr = "uref-be1dc0fd639a3255c1e3e5e2aa699df66171e40fa9450688c5d718b470e057c6-007"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "02be1dc0fd639a3255c1e3e5e2aa699df66171e40fa9450688c5d718b470e057c607")
        clParse.itsValueInStr = "123abc123"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == ConstValues.INVALID_VALUE)

        //PublicKey assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_PUBLIC_KEY
        clParse.itsValueInStr = "01394476bd8202887ac0e42ae9d8f96d7e02d81cc204533506f1fd199e95b1fd2b"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "01394476bd8202887ac0e42ae9d8f96d7e02d81cc204533506f1fd199e95b1fd2b")
        clParse.itsValueInStr = "abc8de76bd8202887ac0e42ae9d8f96d7e02d81cc204533506f1fd199e95b1fcec"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "abc8de76bd8202887ac0e42ae9d8f96d7e02d81cc204533506f1fd199e95b1fcec")

        //Option assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_OPTION
        //Option(Null) assertion
        clParse.itsValueInStr = ConstValues.VALUE_NULL
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "00")
        //Option(U32(10)) assertion
        clParse.innerParsed1 = CLParsed()
        clParse.itsValueInStr = ""
        clParse.innerParsed1.itsCLType = CLType()
        clParse.innerParsed1.itsCLType.itsTypeStr = ConstValues.CLTYPE_U32
        clParse.innerParsed1.itsValueInStr = "10"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "010a000000")
        //Option(U64(123456)) assertion
        clParse.innerParsed1.itsCLType.itsTypeStr = ConstValues.CLTYPE_U64
        clParse.innerParsed1.itsValueInStr = "123456"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0140e2010000000000")
        //Option(String("Hello, World!")) assertion
        clParse.innerParsed1.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        clParse.innerParsed1.itsValueInStr = "Hello, World!"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "010d00000048656c6c6f2c20576f726c6421")

        //List assertion
        //Empty list assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_LIST
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "")
        //List of 3 CLParse U32 number assertion
        val u321 = CLParsed()
        u321.itsCLType.itsTypeStr = ConstValues.CLTYPE_U32
        u321.itsValueInStr = "1"
        val u322 = CLParsed()
        u322.itsCLType.itsTypeStr = ConstValues.CLTYPE_U32
        u322.itsValueInStr = "2"
        val u323 = CLParsed()
        u323.itsCLType.itsTypeStr = ConstValues.CLTYPE_U32
        u323.itsValueInStr = "3"
        clParse.itsArrayValue.add(u321)
        clParse.itsArrayValue.add(u322)
        clParse.itsArrayValue.add(u323)
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "03000000010000000200000003000000")
        clParse.itsArrayValue.removeAt(0)
        clParse.itsArrayValue.removeAt(0)
        clParse.itsArrayValue.removeAt(0)
        //List of 3 CLParse String assertion
        val string1 = CLParsed()
        string1.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        string1.itsValueInStr = "Hello, World!"
        val string2 = CLParsed()
        string2.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        string2.itsValueInStr = "Bonjour le monde"
        val string3 = CLParsed()
        string3.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        string3.itsValueInStr = "Hola Mundo"
        clParse.itsArrayValue.add(string1)
        clParse.itsArrayValue.add(string2)
        clParse.itsArrayValue.add(string3)
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "030000000d00000048656c6c6f2c20576f726c642110000000426f6e6a6f7572206c65206d6f6e64650a000000486f6c61204d756e646f")
        clParse.itsArrayValue.removeAt(0)
        clParse.itsArrayValue.removeAt(0)
        clParse.itsArrayValue.removeAt(0)
        //List of 3 CLParse U8 assertion
        val u81 = CLParsed()
        u81.itsCLType.itsTypeStr = ConstValues.CLTYPE_U8
        u81.itsValueInStr = "100"
        val u82 = CLParsed()
        u82.itsCLType.itsTypeStr = ConstValues.CLTYPE_U8
        u82.itsValueInStr = "0"
        val u83 = CLParsed()
        u83.itsCLType.itsTypeStr = ConstValues.CLTYPE_U8
        u83.itsValueInStr = "255"
        clParse.itsArrayValue.add(u81)
        clParse.itsArrayValue.add(u82)
        clParse.itsArrayValue.add(u83)
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "030000006400ff")
        clParse.itsArrayValue.removeAt(0)
        clParse.itsArrayValue.removeAt(0)
        clParse.itsArrayValue.removeAt(0)
        //List(Map(String,String) assertion
        //base on the deploy at this address: https://testnet.cspr.live/deploy/AaB4aa0C14a37Bc9386020609aa1CabaD895c3E2E104d877B936C6Ffa2302268
        //refer to session section of the deploy, args item number 2
        val mapParse = CLParsed()
        mapParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_MAP
        val mapKey1 = CLParsed()
        mapKey1.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapKey1.itsValueInStr = "token_uri"
        val mapValue1 = CLParsed()
        mapValue1.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapValue1.itsValueInStr = "https://gateway.pinata.cloud/ipfs/QmZNz3zVNyV383fn1ZgbroxCLSxVnx7jrq4yjGyFJoZ5Vk"
        mapParse.innerParsed1 = CLParsed()
        mapParse.innerParsed1.itsArrayValue.add(mapKey1)
        mapParse.innerParsed2 = CLParsed()
        mapParse.innerParsed2.itsArrayValue.add(mapValue1)
        clParse.itsArrayValue.add(mapParse)
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "010000000100000009000000746f6b656e5f7572695000000068747470733a2f2f676174657761792e70696e6174612e636c6f75642f697066732f516d5a4e7a337a564e7956333833666e315a6762726f78434c5378566e78376a727134796a4779464a6f5a35566b")
        clParse.itsArrayValue.removeAt(0)

        //CLParse Map assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_MAP
        clParse.innerParsed1 = CLParsed()
        clParse.innerParsed2 = CLParsed()
        //CLParse Map(String,String) assertion
        //Test based on the deploy at this address
        //https://testnet.cspr.live/deploy/430df377ae04726de907f115bb06c52e40f6cd716b4b475a10e4cd9226d1317e
        //please refer to execution_results item 86 to see the real data

        //Key generation
        val mapKey11 = CLParsed()
        mapKey11.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapKey11.itsValueInStr = "contract_package_hash"
        val mapKey12 = CLParsed()
        mapKey12.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapKey12.itsValueInStr = "event_type"
        val mapKey13 = CLParsed()
        mapKey13.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapKey13.itsValueInStr = "reserve0"
        val mapKey14 = CLParsed()
        mapKey14.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapKey14.itsValueInStr = "reserve1"
        clParse.innerParsed1.itsArrayValue.add(mapKey11)
        clParse.innerParsed1.itsArrayValue.add(mapKey12)
        clParse.innerParsed1.itsArrayValue.add(mapKey13)
        clParse.innerParsed1.itsArrayValue.add(mapKey14)

        //Value generation
        val mapValue11 = CLParsed()
        mapValue11.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapValue11.itsValueInStr = "d32DE152c0bBFDcAFf5b2a6070Cd729Fc0F3eaCF300a6b5e2abAB035027C49bc"
        val mapValue12 = CLParsed()
        mapValue12.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapValue12.itsValueInStr = "sync"
        val mapValue13 = CLParsed()
        mapValue13.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapValue13.itsValueInStr = "412949147973569321536747"
        val mapValue14 = CLParsed()
        mapValue14.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapValue14.itsValueInStr = "991717147268569848142418"
        clParse.innerParsed2.itsArrayValue.add(mapValue11)
        clParse.innerParsed2.itsArrayValue.add(mapValue12)
        clParse.innerParsed2.itsArrayValue.add(mapValue13)
        clParse.innerParsed2.itsArrayValue.add(mapValue14)
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0400000015000000636f6e74726163745f7061636b6167655f6861736840000000643332444531353263306242464463414666356232613630373043643732394663304633656143463330306136623565326162414230333530323743343962630a0000006576656e745f747970650400000073796e630800000072657365727665301800000034313239343931343739373335363933323135333637343708000000726573657276653118000000393931373137313437323638353639383438313432343138")
        for(i in 0..3) {
            clParse.innerParsed1.itsArrayValue.removeAt(0)
            clParse.innerParsed2.itsArrayValue.removeAt(0)
        }
        //Map(String,String) 2
        //Test based on the deploy at this address
        //https://testnet.cspr.live/deploy/93b24bf34eba63a157b60b696eae83424904263679dff1cd1c8d6d3f3d73afaa
        //please refer to execution_results item 30 to see the real data
        //Key generation
        val mapKey21 = CLParsed()
        mapKey21.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapKey21.itsValueInStr = "contract_package_hash"
        val mapKey22 = CLParsed()
        mapKey22.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapKey22.itsValueInStr = "event_type"
        val mapKey23 = CLParsed()
        mapKey23.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapKey23.itsValueInStr = "from"
        val mapKey24 = CLParsed()
        mapKey24.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapKey24.itsValueInStr = "pair"
        val mapKey25 = CLParsed()
        mapKey25.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapKey25.itsValueInStr = "to"
        val mapKey26 = CLParsed()
        mapKey26.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapKey26.itsValueInStr = "value"
        clParse.innerParsed1.itsArrayValue.add(mapKey21)
        clParse.innerParsed1.itsArrayValue.add(mapKey22)
        clParse.innerParsed1.itsArrayValue.add(mapKey23)
        clParse.innerParsed1.itsArrayValue.add(mapKey24)
        clParse.innerParsed1.itsArrayValue.add(mapKey25)
        clParse.innerParsed1.itsArrayValue.add(mapKey26)

        //Value generation
        val mapValue21 = CLParsed()
        mapValue21.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapValue21.itsValueInStr = "26526c30383e5c02d684ac68d7845e576a87166926f7500bdaa303cdab52aea7"
        val mapValue22 = CLParsed()
        mapValue22.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapValue22.itsValueInStr = "transfer"
        val mapValue23 = CLParsed()
        mapValue23.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapValue23.itsValueInStr = "Key::Account(8b217a09296d5ce360847a7d20f623476157c5f022333c4e988a464035cadd80)"
        val mapValue24 = CLParsed()
        mapValue24.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapValue24.itsValueInStr = "Key::Hash(53a8121f219ad2c6420f007a2016ed320c519579112b81d505cb15715404b264)"
        val mapValue25 = CLParsed()
        mapValue25.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapValue25.itsValueInStr = "Key::Hash(26526c30383e5c02d684ac68d7845e576a87166926f7500bdaa303cdab52aea7)"
        val mapValue26 = CLParsed()
        mapValue26.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        mapValue26.itsValueInStr = "1763589511"
        clParse.innerParsed2.itsArrayValue.add(mapValue21)
        clParse.innerParsed2.itsArrayValue.add(mapValue22)
        clParse.innerParsed2.itsArrayValue.add(mapValue23)
        clParse.innerParsed2.itsArrayValue.add(mapValue24)
        clParse.innerParsed2.itsArrayValue.add(mapValue25)
        clParse.innerParsed2.itsArrayValue.add(mapValue26)
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0600000015000000636f6e74726163745f7061636b6167655f6861736840000000323635323663333033383365356330326436383461633638643738343565353736613837313636393236663735303062646161333033636461623532616561370a0000006576656e745f74797065080000007472616e736665720400000066726f6d4e0000004b65793a3a4163636f756e7428386232313761303932393664356365333630383437613764323066363233343736313537633566303232333333633465393838613436343033356361646438302904000000706169724b0000004b65793a3a4861736828353361383132316632313961643263363432306630303761323031366564333230633531393537393131326238316435303563623135373135343034623236342902000000746f4b0000004b65793a3a486173682832363532366333303338336535633032643638346163363864373834356535373661383731363639323666373530306264616133303363646162353261656137290500000076616c75650a00000031373633353839353131")
        for(i in 0..5) {
            clParse.innerParsed1.itsArrayValue.removeAt(0)
            clParse.innerParsed2.itsArrayValue.removeAt(0)
        }

        // CLParse ByteArray assertion
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_BYTEARRAY
        clParse.itsValueInStr = "006d0be2fb64bcc8d170443fbadc885378fdd1c71975e2ddd349281dd9cc59cc"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "006d0be2fb64bcc8d170443fbadc885378fdd1c71975e2ddd349281dd9cc59cc")

        //Test for CLValue Result serialization
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_RESULT
        //For Result Ok String
        //Take this deploy address https://testnet.cspr.live/deploy/2ad794272a1a805082f171f96f1ea0e71fcac3ae6dd0c525343199b553be8a61
        //in execution_results item 16
        clParse.itsValueInStr = ConstValues.CLPARSE_RESULT_OK
        clParse.innerParsed1 = CLParsed()
        clParse.innerParsed1.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        clParse.innerParsed1.itsValueInStr = "goodresult"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "010a000000676f6f64726573756c74")

        clParse.itsValueInStr = ConstValues.CLPARSE_RESULT_ERR
        clParse.innerParsed1.itsCLType.itsTypeStr = ConstValues.CLTYPE_U512
        clParse.innerParsed1.itsValueInStr = "999888666555444999887988887777666655556666777888999666999"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "001837f578fca55492f299ea354eaca52b6e9de47d592453c728")
        //For Result Err2
        //Take this deploy address https://testnet.cspr.live/deploy/2ad794272a1a805082f171f96f1ea0e71fcac3ae6dd0c525343199b553be8a61
        //in execution_results item 21
        clParse.innerParsed1.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        clParse.innerParsed1.itsValueInStr = "badresult"
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0009000000626164726573756c74")

        //Test for CLValue Tuple1 serialization
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_TUPLE1
        //CLParse Tuple1(I32(1000)) assertion
        val clParseTuple1 = CLParsed()
        clParseTuple1.itsCLType.itsTypeStr = ConstValues.CLTYPE_I32
        clParseTuple1.itsValueInStr = "1000"
        clParse.innerParsed1 = clParseTuple1
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "e8030000")

        //CLParse Tuple1(String("Hello, World!")) assertion
        val clParseTuple2 = CLParsed()
        clParseTuple2.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        clParseTuple2.itsValueInStr = "Hello, World!"
        clParse.innerParsed1 = clParseTuple2
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "0d00000048656c6c6f2c20576f726c6421")

        //CLParse Tuple2 assertion
        //Tuple2(String("abc"),U512(1)) assertion
        //Take this deploy address https://testnet.cspr.live/deploy/2ad794272a1a805082f171f96f1ea0e71fcac3ae6dd0c525343199b553be8a61
        //in execution_results item 31
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_TUPLE2
        val clParseTuple21 = CLParsed()
        clParseTuple21.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        clParseTuple21.itsValueInStr = "abc"
        val clParseTuple22 = CLParsed()
        clParseTuple22.itsCLType.itsTypeStr = ConstValues.CLTYPE_U512
        clParseTuple22.itsValueInStr = "1"
        clParse.innerParsed1 = clParseTuple21
        clParse.innerParsed2 = clParseTuple22
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "030000006162630101")

        //CLParse Tuple3 assertion
        //Tuple3(PublicKey,Option,U512) assertion
        //Take this deploy address https://testnet.cspr.live/deploy/2ad794272a1a805082f171f96f1ea0e71fcac3ae6dd0c525343199b553be8a61
        //in execution_results item 36
        clParse.itsCLType.itsTypeStr = ConstValues.CLTYPE_TUPLE3
        clParse.innerParsed1 = CLParsed()
        clParse.innerParsed2 = CLParsed()
        clParse.innerParsed3 = CLParsed()
        //CLParse PublicKey generation
        val clParsePublicKey = CLParsed()
        clParsePublicKey.itsCLType.itsTypeStr = ConstValues.CLTYPE_PUBLIC_KEY
        clParsePublicKey.itsValueInStr = "01a018bf278f32fdb7b06226071ce399713ace78a28d43a346055060a660ba7aa9"
        //CLParse Option(String) generation
        val clParseOption = CLParsed()
        clParseOption.itsCLType.itsTypeStr = ConstValues.CLTYPE_OPTION
        val clParseOptionString = CLParsed()
        clParseOptionString.itsCLType.itsTypeStr = ConstValues.CLTYPE_STRING
        clParseOptionString.itsValueInStr = "abc"
        clParseOption.innerParsed1 = CLParsed()
        clParseOption.innerParsed1 = clParseOptionString
        //CLParse U512 generation
        val clParseTuple3U512 = CLParsed()
        clParseTuple3U512.itsCLType.itsTypeStr = ConstValues.CLTYPE_U512
        clParseTuple3U512.itsValueInStr = "2"
        clParse.innerParsed1 = clParsePublicKey
        clParse.innerParsed2 = clParseOption
        clParse.innerParsed3 = clParseTuple3U512
        assert(CLParseSerialization.serializeFromCLParse(clParse) == "01a018bf278f32fdb7b06226071ce399713ace78a28d43a346055060a660ba7aa901030000006162630102")

    }
}