package com.casper.sdk.serialization

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CLTypeSerializationTest {
    @Test
    fun clTypeSerialize() {
        val clType:CLType = CLType()

        //Assertion for CLType Primitive

        clType.itsTypeStr = ConstValues.CLTYPE_BOOL
        assert(CLTypeSerialization.serializeForCLType(clType) == "00")
        clType.itsTypeStr = ConstValues.CLTYPE_I32
        assert(CLTypeSerialization.serializeForCLType(clType) == "01")
        clType.itsTypeStr = ConstValues.CLTYPE_I64
        assert(CLTypeSerialization.serializeForCLType(clType) == "02")
        clType.itsTypeStr = ConstValues.CLTYPE_U8
        assert(CLTypeSerialization.serializeForCLType(clType) == "03")
        clType.itsTypeStr = ConstValues.CLTYPE_U32
        assert(CLTypeSerialization.serializeForCLType(clType) == "04")
        clType.itsTypeStr = ConstValues.CLTYPE_U64
        assert(CLTypeSerialization.serializeForCLType(clType) == "05")
        clType.itsTypeStr = ConstValues.CLTYPE_U128
        assert(CLTypeSerialization.serializeForCLType(clType) == "06")
        clType.itsTypeStr = ConstValues.CLTYPE_U256
        assert(CLTypeSerialization.serializeForCLType(clType) == "07")
        clType.itsTypeStr = ConstValues.CLTYPE_U512
        assert(CLTypeSerialization.serializeForCLType(clType) == "08")
        clType.itsTypeStr = ConstValues.CLTYPE_UNIT
        assert(CLTypeSerialization.serializeForCLType(clType) == "09")
        clType.itsTypeStr = ConstValues.CLTYPE_STRING
        assert(CLTypeSerialization.serializeForCLType(clType) == "0a")
        clType.itsTypeStr = ConstValues.CLTYPE_KEY
        assert(CLTypeSerialization.serializeForCLType(clType) == "0b")
        clType.itsTypeStr = ConstValues.CLTYPE_UREF
        assert(CLTypeSerialization.serializeForCLType(clType) == "0c")
        clType.itsTypeStr = ConstValues.CLTYPE_PUBLIC_KEY
        assert(CLTypeSerialization.serializeForCLType(clType) == "16")
        clType.itsTypeStr = ConstValues.CLTYPE_ANY
        assert(CLTypeSerialization.serializeForCLType(clType) == "15")
        //Assertion for CLType Compound

        // Option CLType assertion
        clType.itsTypeStr = ConstValues.CLTYPE_OPTION
        clType.innerCLType1 = CLType()
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_U512
        assert(CLTypeSerialization.serializeForCLType(clType) == "0d08")
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_BOOL
        assert(CLTypeSerialization.serializeForCLType(clType) == "0d00")
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_I32
        assert(CLTypeSerialization.serializeForCLType(clType) == "0d01")

        //Map CLType assertion
        clType.itsTypeStr = ConstValues.CLTYPE_MAP
        clType.innerCLType1 = CLType()
        clType.innerCLType2 = CLType()
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_STRING
        clType.innerCLType2.itsTypeStr = ConstValues.CLTYPE_STRING
        assert(CLTypeSerialization.serializeForCLType(clType) == "110a0a")

        // List CLType assertion
        clType.itsTypeStr = ConstValues.CLTYPE_LIST
        //List(Bool) assertion
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_BOOL
        assert(CLTypeSerialization.serializeForCLType(clType) == "0e00")
        //List(Key) assertion
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_KEY
        assert(CLTypeSerialization.serializeForCLType(clType) == "0e0b")
        //List(Map(String,String)) assertion
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_MAP
        clType.innerCLType1.innerCLType1 = CLType()
        clType.innerCLType1.innerCLType2 = CLType()
        clType.innerCLType1.innerCLType1.itsTypeStr = ConstValues.CLTYPE_STRING
        clType.innerCLType1.innerCLType2.itsTypeStr = ConstValues.CLTYPE_STRING
        assert(CLTypeSerialization.serializeForCLType(clType) == "0e110a0a")
        //List(Map(Bool,Option(U128))) assertion
        clType.innerCLType1.innerCLType1.itsTypeStr = ConstValues.CLTYPE_BOOL
        clType.innerCLType1.innerCLType2.itsTypeStr = ConstValues.CLTYPE_OPTION
        clType.innerCLType1.innerCLType2.innerCLType1 = CLType()
        clType.innerCLType1.innerCLType2.innerCLType1.itsTypeStr = ConstValues.CLTYPE_U128
        assert(CLTypeSerialization.serializeForCLType(clType) == "0e11000d06")

        //Result CLType assertion
        //Result(Ok(I64),Err(PublicKey))
        clType.itsTypeStr = ConstValues.CLTYPE_RESULT
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_I64
        clType.innerCLType2.itsTypeStr = ConstValues.CLTYPE_PUBLIC_KEY
        assert(CLTypeSerialization.serializeForCLType(clType) == "100216")
        //Result(Ok(U256),Err(Option(U32))
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_U256
        clType.innerCLType2.itsTypeStr = ConstValues.CLTYPE_OPTION
        clType.innerCLType2.innerCLType1 = CLType()
        clType.innerCLType2.innerCLType1.itsTypeStr = ConstValues.CLTYPE_U32
        assert(CLTypeSerialization.serializeForCLType(clType) == "10070d04")

        // Tuple1 CLType assertion
        clType.itsTypeStr = ConstValues.CLTYPE_TUPLE1
        //Tuple1(Any) assertion
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_ANY
        assert(CLTypeSerialization.serializeForCLType(clType) == "1215")
        //Tuple1(Key) assertion
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_KEY
        assert(CLTypeSerialization.serializeForCLType(clType) == "120b")
        //Tuple1(Option(String)) assertion
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_OPTION
        clType.innerCLType1.innerCLType1 = CLType()
        clType.innerCLType1.innerCLType1.itsTypeStr = ConstValues.CLTYPE_STRING
        assert(CLTypeSerialization.serializeForCLType(clType) == "120d0a")

        //Tuple2 CLType assertion
        clType.itsTypeStr = ConstValues.CLTYPE_TUPLE2
        //Tuple2(I32,I64) assertion
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_I32
        clType.innerCLType2.itsTypeStr = ConstValues.CLTYPE_I64
        assert(CLTypeSerialization.serializeForCLType(clType) == "130102")
        //Tuple2(U64,String) assertion
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_U64
        clType.innerCLType2.itsTypeStr = ConstValues.CLTYPE_STRING
        assert(CLTypeSerialization.serializeForCLType(clType) == "13050a")
        //Tuple2(Result(Ok(U8),Err(Unit)),URef) assertion
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_RESULT
        clType.innerCLType1.innerCLType1 = CLType()
        clType.innerCLType1.innerCLType2 = CLType()
        clType.innerCLType1.innerCLType1.itsTypeStr = ConstValues.CLTYPE_U8
        clType.innerCLType1.innerCLType2.itsTypeStr = ConstValues.CLTYPE_UNIT
        clType.innerCLType2.itsTypeStr = ConstValues.CLTYPE_UREF
        assert(CLTypeSerialization.serializeForCLType(clType) == "131003090c")

        // Tuple3 CLType assertion
        // Tuple3(Bool,I32,I64) assertion
        clType.itsTypeStr = ConstValues.CLTYPE_TUPLE3
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_BOOL
        clType.innerCLType2.itsTypeStr = ConstValues.CLTYPE_I32
        clType.innerCLType3 = CLType()
        clType.innerCLType3.itsTypeStr = ConstValues.CLTYPE_I64
        assert(CLTypeSerialization.serializeForCLType(clType) == "14000102")
        // Tuple3 (String,Key,PublicKey) assertion
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_STRING
        clType.innerCLType2.itsTypeStr = ConstValues.CLTYPE_KEY
        clType.innerCLType3.itsTypeStr = ConstValues.CLTYPE_PUBLIC_KEY
        assert(CLTypeSerialization.serializeForCLType(clType) == "140a0b16")
        // Tuple3(List(Any),Map(U8,I32),Result(String))
        clType.innerCLType1.itsTypeStr = ConstValues.CLTYPE_LIST
        //List(Any)
        clType.innerCLType1.innerCLType1 = CLType()
        clType.innerCLType1.innerCLType1.itsTypeStr = ConstValues.CLTYPE_ANY
        //Map(U8,I32)
        clType.innerCLType2.itsTypeStr = ConstValues.CLTYPE_MAP
        clType.innerCLType2.innerCLType1 = CLType()
        clType.innerCLType2.innerCLType2 = CLType()
        clType.innerCLType2.innerCLType1.itsTypeStr = ConstValues.CLTYPE_U8
        clType.innerCLType2.innerCLType2.itsTypeStr = ConstValues.CLTYPE_I32
        //Result(String)
        clType.innerCLType3.itsTypeStr = ConstValues.CLTYPE_RESULT
        clType.innerCLType3.innerCLType1 = CLType()
        clType.innerCLType3.innerCLType1.itsTypeStr = ConstValues.CLTYPE_STRING
        assert(CLTypeSerialization.serializeForCLType(clType) == "140e15110301100a")
    }
}