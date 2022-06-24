package com.casper.sdk.getdictionary

import com.casper.sdk.ConstValues
import com.casper.sdk.clvalue.CLValue
import org.junit.jupiter.api.Test

internal class GetDictionaryItemRPCTest {

    @Test
    fun getDictionaryItem() {
        val getDictionaryItemRPC = GetDictionaryItemRPC()
        val getDictionaryItemParams = GetDictionaryItemParams()
        //Test 1:  Parameter DictionaryIdentifier of type AccountNamedKey
        val diAccountNamedKey = DIAccountNamedKey()
        diAccountNamedKey.key = "account-hash-ad7e091267d82c3b9ed1987cb780a005a550e6b3d1ca333b743e2dba70680877"
        diAccountNamedKey.dictionaryName = "dict_name"
        diAccountNamedKey.dictionaryItemKey = "abc_name"
        val di = DictionaryIdentifier()
        di.itsType = ConstValues.DI_ACCOUNT_NAMED_KEY
        di.itsValue.add(diAccountNamedKey)
        getDictionaryItemParams.dictionaryIdentifier = di
        getDictionaryItemParams.stateRootHash = "146b860f82359ced6e801cbad31015b5a9f9eb147ab2a449fd5cdb950e961ca8"
        val parameterStr: String = getDictionaryItemParams.generateParameterStr()
        val getDictionaryItemResult: GetDictionaryItemResult = getDictionaryItemRPC.getDictionaryItem(parameterStr)
        assert(getDictionaryItemResult.dictionaryKey == "dictionary-5d3e90f064798d54e5e53643c4fce0cbb1024aadcad1586cc4b7c1358a530373")
        assert(getDictionaryItemResult.apiVersion == "1.4.5")
        assert(getDictionaryItemResult.storedValue.itsType == ConstValues.STORED_VALUE_CLVALUE)
        val clValue: CLValue = getDictionaryItemResult.storedValue.itsValue[0] as CLValue
        assert(clValue.itsBytes == "090000006162635f76616c7565")
        assert(clValue.itsParse.itsValueInStr == "abc_value")
        assert(clValue.itsCLType.itsTypeStr == ConstValues.CLTYPE_STRING)
        assert(getDictionaryItemResult.merkleProof.length > 0)
        //Test 2:  Parameter DictionaryIdentifier of type ContractNamedKey
        val diContractNamedKey = DIContractNamedKey()
        diContractNamedKey.dictionaryName = "dictname"
        diContractNamedKey.dictionaryItemKey = "abcname"
        diContractNamedKey.key = "hash-d5308670dc1583f49a516306a3eb719abe0ba51651cb08e606fcfc1f9b9134cf"
        val di2 = DictionaryIdentifier()
        di2.itsType = ConstValues.DI_CONTRACT_NAMED_KEY
        di2.itsValue.add(diContractNamedKey)
        val getDictionaryItemParams2 = GetDictionaryItemParams()
        getDictionaryItemParams2.dictionaryIdentifier = di2
        getDictionaryItemParams2.stateRootHash = "146b860f82359ced6e801cbad31015b5a9f9eb147ab2a449fd5cdb950e961ca8"
        val parameterStr2: String = getDictionaryItemParams2.generateParameterStr()
        val getDictionaryItemResult2: GetDictionaryItemResult = getDictionaryItemRPC.getDictionaryItem(parameterStr2)
        assert(getDictionaryItemResult2.dictionaryKey == "dictionary-ac34673fa957fa8083306892815496b8fdee0aa1509f0080823979d869176060")
        assert(getDictionaryItemResult2.apiVersion == "1.4.5")
        assert(getDictionaryItemResult2.storedValue.itsType == ConstValues.STORED_VALUE_CLVALUE)
        val clValue2: CLValue = getDictionaryItemResult2.storedValue.itsValue[0] as CLValue
        assert(clValue2.itsBytes == "0800000061626376616c7565")
        assert(clValue2.itsParse.itsValueInStr == "abcvalue")
        assert(clValue2.itsCLType.itsTypeStr == ConstValues.CLTYPE_STRING)
        assert(getDictionaryItemResult2.merkleProof.length == 30178)
        //Test 3:  Parameter DictionaryIdentifier of type URef
        val diuRef = DIURef()
        diuRef.dictionaryItemKey = "abc_name"
        diuRef.seedUref = "uref-30074a46a79b2d80cff437594d2422383f6c754de453b732448cc711b9f7e129-007"
        val di3 = DictionaryIdentifier()
        di3.itsType = ConstValues.DI_UREF
        di3.itsValue.add(diuRef)
        val getDictionaryItemParams3 = GetDictionaryItemParams()
        getDictionaryItemParams3.dictionaryIdentifier = di3
        getDictionaryItemParams3.stateRootHash = "146b860f82359ced6e801cbad31015b5a9f9eb147ab2a449fd5cdb950e961ca8"
        val parameterStr3: String = getDictionaryItemParams3.generateParameterStr()

        val getDictionaryItemResult3: GetDictionaryItemResult = getDictionaryItemRPC.getDictionaryItem(parameterStr3)
        assert(getDictionaryItemResult3.dictionaryKey == "dictionary-5d3e90f064798d54e5e53643c4fce0cbb1024aadcad1586cc4b7c1358a530373")
        assert(getDictionaryItemResult3.apiVersion == "1.4.5")
        assert(getDictionaryItemResult3.storedValue.itsType == ConstValues.STORED_VALUE_CLVALUE)
        val clValue3: CLValue = getDictionaryItemResult3.storedValue.itsValue[0] as CLValue
        assert(clValue3.itsBytes == "090000006162635f76616c7565")
        assert(clValue3.itsParse.itsValueInStr == "abc_value")
        assert(clValue3.itsCLType.itsTypeStr == ConstValues.CLTYPE_STRING)
        assert(getDictionaryItemResult3.merkleProof.length == 30330)
        //Test 4:  Parameter DictionaryIdentifier of type Dictionary
        val diDictionary = DIDictionary()
        diDictionary.itsValue = "dictionary-5d3e90f064798d54e5e53643c4fce0cbb1024aadcad1586cc4b7c1358a530373"
        val di4 = DictionaryIdentifier()
        di4.itsType = ConstValues.DI_DICTIONARY
        di4.itsValue.add(diDictionary)
        val getDictionaryItemParams4 = GetDictionaryItemParams()
        getDictionaryItemParams4.dictionaryIdentifier = di4
        getDictionaryItemParams4.stateRootHash = "146b860f82359ced6e801cbad31015b5a9f9eb147ab2a449fd5cdb950e961ca8"
        val parameterStr4: String = getDictionaryItemParams4.generateParameterStr()
        val getDictionaryItemResult4: GetDictionaryItemResult = getDictionaryItemRPC.getDictionaryItem(parameterStr4)
        assert(getDictionaryItemResult4.dictionaryKey == "dictionary-5d3e90f064798d54e5e53643c4fce0cbb1024aadcad1586cc4b7c1358a530373")
        assert(getDictionaryItemResult4.apiVersion == "1.4.5")
        assert(getDictionaryItemResult4.storedValue.itsType == ConstValues.STORED_VALUE_CLVALUE)
        val clValue4: CLValue = getDictionaryItemResult4.storedValue.itsValue[0] as CLValue
        assert(clValue4.itsBytes == "090000006162635f76616c7565")
        assert(clValue4.itsParse.itsValueInStr == "abc_value")
        assert(clValue4.itsCLType.itsTypeStr == ConstValues.CLTYPE_STRING)
        assert(getDictionaryItemResult4.merkleProof.length == 30330)
        //Negative path
        //Test1:  Test with incorrect state_root_hash
        //Error is thrown
        val diDictionary2 = DIDictionary()
        diDictionary2.itsValue = "dictionary-5d3e90f064798d54e5e53643c4fce0cbb1024aadcad1586cc4b7c1358a530373"
        val di5 = DictionaryIdentifier()
        di5.itsType = ConstValues.DI_DICTIONARY
        di5.itsValue.add(diDictionary)
        val getDictionaryItemParams5 = GetDictionaryItemParams()
        getDictionaryItemParams5.dictionaryIdentifier = di5
        getDictionaryItemParams5.stateRootHash = "AAAA_146b860f82359ced6e801cbad31015b5a9f9eb147ab2a449fd5cdb950e961ca8"
        val parameterStr5: String = getDictionaryItemParams5.generateParameterStr()
        try {
            getDictionaryItemRPC.getDictionaryItem(parameterStr5)
        } catch (e: IllegalArgumentException) {
            println("Error with wrong state root hash")
        }
        //Test 2:  Test with incorrect parameter of DictionaryIdentifier
        val diAccountNamedKey2 = DIAccountNamedKey()
        diAccountNamedKey2.key = "uref-ad7e091267d82c3b9ed1987cb780a005a550e6b3d1ca333b743e2dba70680877"
        diAccountNamedKey2.dictionaryName = "dict_name_test"
        diAccountNamedKey2.dictionaryItemKey = "abc_name_test"
        val di6 = DictionaryIdentifier()
        di6.itsType = ConstValues.DI_ACCOUNT_NAMED_KEY
        di6.itsValue.add(diAccountNamedKey2)
        val getDictionaryItemParams6 = GetDictionaryItemParams()
        getDictionaryItemParams6.dictionaryIdentifier = di6
        getDictionaryItemParams6.stateRootHash = "146b860f82359ced6e801cbad31015b5a9f9eb147ab2a449fd5cdb950e961ca8"
        val parameterStr6: String = getDictionaryItemParams6.generateParameterStr()
        try {
            getDictionaryItemRPC.getDictionaryItem(parameterStr6)
        } catch (e: IllegalArgumentException) {
            println("Error wrong DictionayIdentifier parameter")
        }
    }
}