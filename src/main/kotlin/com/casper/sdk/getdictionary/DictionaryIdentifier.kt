package com.casper.sdk.getdictionary

/** Class built for storing DictionaryIdentifier enum
 * This enum can have 4 possible values:
 * AccountNamedKey
 * ContractNamedKey
 * Dictionary
 * URef
 * This class has 2 attribute:
 * itsType: is for the type of the enum, which can be 1 among 4 possible values mentioned above
 * itsValue: a mutable list of just 1 element, to hold the real value of each enum type,
 * for example if the DictionaryIdentifier is of type AccountNamedKey, its values of (key, dictionary_name, dictionary_item_key) will be stored
 * in this itsValue variable.*/
class DictionaryIdentifier {
    var itsType: String = ""
    var itsValue: MutableList<Any> = mutableListOf()
}