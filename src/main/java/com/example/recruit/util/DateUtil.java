package com.example.recruit.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    // 计算与今天的天数差
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

    // 根据条件筛选符合的时间并返回boolean值
    public static Boolean DateTimeReverse(LocalDateTime localDateTime, String condition) {
        LocalDate now = LocalDate.now();
        LocalDate before = localDateTime.toLocalDate();
        long days = before.until(now, ChronoUnit.DAYS);
        if(condition.equals("不限")) {
            return true;
        } else if(condition.equals("今天")) {
            return days == 0;
        } else if(condition.equals("3天内")) {
            return days >= 0 && days <= 3;
        } else if(condition.equals("1周内")) {
            return days >= 0 && days <= 7;
        } else if(condition.equals("1个月内")) {
            return days >= 0 && days <= 31;
        } else if(condition.equals("3个月内")) {
            return days >= 0 && days <= 93;
        } else if(condition.equals("半年内")) {
            return days >= 0 && days <= 186;
        } else if(condition.equals("半年以上")) {
            return days > 186;
        } else {
            return false;
        }
    }
}
