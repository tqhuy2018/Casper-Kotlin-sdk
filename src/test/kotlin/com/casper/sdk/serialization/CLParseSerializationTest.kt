package com.casper.sdk.serialization

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLParsed
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CLParseSerializationTest {
    @Test
    fun clParseSerialize() {
        val clParse:CLParsed = CLParsed()
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
    }
}