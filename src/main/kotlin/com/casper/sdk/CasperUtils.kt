package com.casper.sdk

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoField

class CasperUtils {
    companion object {
        //This function change timestamp in format of "2020-11-17T00:39:24.072Z" to millisecondsSince1970 U64 number in String format like this "1605573564072"
        fun fromTimeStampToU64Str(timeStamp:String) : ULong {
            var ret:ULong = 0u
            val dateStr: String = timeStamp.substring(0,timeStamp.length-1)
            val dateTime = LocalDateTime.parse(dateStr)//2020-11-17T00:39:24.072Z
            val milisecond = dateTime.toEpochSecond(ZoneOffset.UTC) * 1000 + dateTime.get(ChronoField.MILLI_OF_SECOND)
            println("Miliseconde is:" + milisecond)
            ret = milisecond.toULong()
            return ret
        }
        fun ttlToMilisecond(ttl:String) :ULong {
            var ret:ULong = 0u
            if(ttl.contains(" ")) {
                val elements:List<String> = ttl.split(" ")
                for(element in elements) {
                    ret = ret + CasperUtils.ttlToMilisecond(element)
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
    }
}