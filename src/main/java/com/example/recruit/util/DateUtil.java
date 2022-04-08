package com.example.recruit.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static Object DateTimeTransform(LocalDateTime localDateTime) {
        LocalDate now = LocalDate.now();
        LocalDate before = localDateTime.toLocalDate();
        long days = before.until(now, ChronoUnit.DAYS);
        String str = "";
        if (days == 0){
            str = "今天";
        } else if(days == 1) {
            str = "昨天";
        } else if(days > 1 && days <= 3){
            str = "2天前";
        } else if(days > 3 && days <= 7){
            str = "3天前";
        } else if(days > 7 && days <= 14){
            str = "1周前";
        } else if(days > 14 && days <= 31){
            str = "2周前";
        } else if(days > 31 && days <= 93){
            str = "1个月前";
        } else if(days > 93 && days <= 186){
            str = "3个月前";
        } else if(days > 186 && days <= 365){
            str = "半年前";
        } else{
            str = "1年前";
        }
        return str;
    }
}
