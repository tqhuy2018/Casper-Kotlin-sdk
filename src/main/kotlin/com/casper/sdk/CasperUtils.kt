package com.casper.sdk

import org.bouncycastle.jcajce.provider.digest.Blake2b
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoField

class CasperUtils {
    companion object {
        fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
        //This function get the blake2b256 for the input string
        //input string is somehow in this format:
        //00000000000100000006000000616d6f756e74050000000400ca9a3b08050400000006000000616d6f756e740500000004005ed0b2080600000074617267657421000000015f12b5776c66d2782a4408d3910f64485dd4047448040955573aa026256cfa0a16020000006964090000000100000000000000000d05070000007370656e6465722100000001dde7472639058717a42e22d297d6cf3e07906bb57bc28efceac3677f8a3dc83b0b
        fun getBlake2bFromStr(from:String) : String {
            val hexaBytes:List<UByte> = fromStringToHexaArray(from)
            val totalBytes:Int = hexaBytes.size-1
            val hexaByteArray = ByteArray(totalBytes + 1)
            for(i in 0..totalBytes) {
                hexaByteArray.set(i,hexaBytes.get(i).toByte())
            }
            val blake2b: Blake2b.Blake2b256 = Blake2b.Blake2b256()
            val hash = blake2b.digest(hexaByteArray)
            val hexStr:String = hash.toHex()
            return hexStr
        }
        //This function turn string in form of hexa format to bytes array
        fun fromStringToHexaArray(from:String):List<UByte> {
            val ret:MutableList<UByte> = mutableListOf()
            val strLength:Int = from.length / 2 - 1
            for(i in 0 .. strLength) {
                val onePair:String = from.substring(i * 2, i* 2 + 2)
                val firstE:UByte = fromHexaToDecimal(onePair.substring(0,1))
                val secondE:UByte = fromHexaToDecimal(onePair.substring(1,2))
                val oneUInt: UInt = firstE * 16u + secondE
                val oneUByte = oneUInt.toUByte()
                ret.add(oneUByte)
            }
            return ret
        }
        fun fromStringToHexaBytes2(from:String): ByteArray {
            val ret = ByteArray(from.length/2)
            val strLength:Int = from.length/2-1
            for(i in 0..strLength) {
                val twoChar:String = from.substring(i*2,i*2 + 2)
                val firstChar:String = twoChar.substring(0,1)
                val secondChar:String = twoChar.substring(1,2)
                val hexaValue:Int = from16to10(firstChar) * 16 + from16to10(secondChar)
                ret.set(i,hexaValue.toByte())
            }
            return ret
        }
        fun fromStringToHexaBytes(from:String): ByteArray {
            val ret = ByteArray(32)
            val strLength:Int = from.length/2-1
            for(i in 0..strLength) {
                val twoChar:String = from.substring(i*2,i*2 + 2)
                val firstChar:String = twoChar.substring(0,1)
                val secondChar:String = twoChar.substring(1,2)
                val hexaValue:Int = from16to10(firstChar) * 16 + from16to10(secondChar)
                ret.set(i,hexaValue.toByte())
            }
            return ret
        }
        fun from16to10(from:String) : Int {
            if(from == "a") {
                return 10
            } else if(from == "b"){
                return 11
            } else if(from == "c"){
                return 12
            } else if(from == "d"){
                return 13
            } else if(from == "e"){
                return 14
            } else if(from == "f"){
                return 15
            } else {
                return from.toInt()
            }
        }
        fun fromHexaToDecimal(from:String) : UByte {
            if (from == "a") {
                return 10u
            } else if(from == "b") {
                return 11u
            } else if(from == "c") {
                return 12u
            } else if(from == "d") {
                return 13u
            } else if(from == "e") {
                return 14u
            } else if(from == "f") {
                return 15u
            } else {
                return from.toUByte()
            }
        }
        //This function change timestamp in format of "2020-11-17T00:39:24.072Z" to millisecondsSince1970 U64 number in String format like this "1605573564072"
        fun fromTimeStampToU64Str(timeStamp:String) : ULong {
            var ret:ULong = 0u
            val dateStr: String = timeStamp.substring(0,timeStamp.length-1)
            val dateTime = LocalDateTime.parse(dateStr)//2020-11-17T00:39:24.072Z
            val milisecond = dateTime.toEpochSecond(ZoneOffset.UTC) * 1000 + dateTime.get(ChronoField.MILLI_OF_SECOND)
            ret = milisecond.toULong()
            return ret
        }
        fun ttlToMilisecond(ttl:String) :ULong {
            var ret:ULong = 0u
            if(ttl.contains(" ")) {
                val elements:List<String> = ttl.split(" ")
                for(element in elements) {
                    ret = ret + ttlToMilisecond(element)
                }
                return  ret
            }
            if(ttl.contains("days")) {
                val numDay = ttl.substring(0,ttl.length - 4)
                ret = numDay.toULong() * 24u * 3600u * 1000u
            } else if(ttl.contains("day")) {
                val numDay = ttl.substring(0,ttl.length - 3)
                ret = numDay.toULong() * 24u * 3600u * 1000u
            } else if(ttl.contains("months")) {
                val numDay = ttl.substring(0,ttl.length - 6).toULong()
                ret = numDay * 30u * 24u * 3600u * 1000u + numDay * 3600u * 440u * 24u
            } else if(ttl.contains("month")) {
                val numDay = ttl.substring(0,ttl.length - 5).toULong()
                ret = numDay * 30u * 24u * 3600u * 1000u + numDay * 3600u * 440u * 24u
            } else if(ttl.contains("M")) {
                val numDay = ttl.substring(0,ttl.length - 1).toULong()
                ret = numDay * 30u * 24u * 3600u * 1000u + numDay * 3600u * 440u * 24u
            } else if(ttl.contains("minutes")) {
                val numMinute = ttl.substring(0,ttl.length - 7).toULong()
                ret = numMinute * 60u * 1000u
            } else if(ttl.contains("minute")) {
                val numMinute = ttl.substring(0,ttl.length - 6).toULong()
                ret = numMinute * 60u * 1000u
            } else if(ttl.contains("min")) {
                val numMinute = ttl.substring(0,ttl.length - 3).toULong()
                ret = numMinute * 60u * 1000u
            } else if(ttl.contains("hours")) {
                val numHour = ttl.substring(0,ttl.length - 5).toULong()
                ret = numHour * 3600u * 1000u
            } else if(ttl.contains("hour")) {
                val numHour = ttl.substring(0,ttl.length - 4).toULong()
                ret = numHour * 3600u * 1000u
            } else if(ttl.contains("hr")) {
                val numHour = ttl.substring(0,ttl.length - 2).toULong()
                ret = numHour * 3600u * 1000u
            } else if(ttl.contains("weeks")) {
                val num = ttl.substring(0,ttl.length - 5).toULong()
                ret = num * 7u * 24u * 3600u * 1000u
            } else if(ttl.contains("week")) {
                val num = ttl.substring(0,ttl.length - 4).toULong()
                ret = num * 7u * 24u * 3600u * 1000u
            } else if(ttl.contains("w")) {
                val num = ttl.substring(0,ttl.length - 1).toULong()
                ret = num * 7u * 24u * 3600u * 1000u
            } else if(ttl.contains("years")) {
                val num = ttl.substring(0,ttl.length - 5).toULong()
                ret = num * 3600u * 1000u * 24u * 365u + num * 3600u * 250u * 24u
            } else if(ttl.contains("year")) {
                val num = ttl.substring(0,ttl.length - 4).toULong()
                ret = num * 3600u * 1000u * 24u * 365u + num * 3600u * 250u * 24u
            } else if(ttl.contains("y")) {
                val num = ttl.substring(0,ttl.length - 1).toULong()
                ret = num * 3600u * 1000u * 24u * 365u + num * 3600u * 250u * 24u
            } else if(ttl.contains("msec")) {
                ret = ttl.substring(0,ttl.length - 4).toULong()
            } else if(ttl.contains("ms")) {
                ret = ttl.substring(0,ttl.length - 2).toULong()
            } else if(ttl.contains("seconds")) {
                val num = ttl.substring(0,ttl.length - 7).toULong()
                ret = num  * 1000u
            } else if(ttl.contains("second")) {
                val num = ttl.substring(0,ttl.length - 6).toULong()
                ret = num  * 1000u
            } else if(ttl.contains("sec")) {
                val num = ttl.substring(0,ttl.length - 3).toULong()
                ret = num  * 1000u
            } else if(ttl.contains("s")) { //second
                val num = ttl.substring(0,ttl.length - 1).toULong()
                ret = num  * 1000u
            } else if(ttl.contains("m")) { //minute
                val num = ttl.substring(0,ttl.length - 1).toULong()
                ret = num  * 60u * 1000u
            } else if(ttl.contains("h")) { //hour
                val num = ttl.substring(0,ttl.length - 1).toULong()
                ret = num  * 3600u * 1000u
            } else if(ttl.contains("d")) { //day
                val num = ttl.substring(0,ttl.length - 1).toULong()
                ret = num  * 24u * 3600u * 1000u
            }
            return  ret
        }
        /*@Throws(IOException::class)
        fun readFromInputStream(inputStream: InputStream): String? {
            val resultStringBuilder = StringBuilder()
            BufferedReader(InputStreamReader(inputStream)).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    resultStringBuilder.append(line)//.append("\n")
                    println(line)
                }
            }
            return resultStringBuilder.toString()
        }*/
    }
}