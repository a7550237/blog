package com.glj.blog.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author guolongjie
 * @since 2019/5/4
 */

public class TimeUtils {

    public static String now(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String time = formatter.format(localDateTime);
        return time;
    }

    public static String now(String pattern){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String time = formatter.format(localDateTime);
        return time;
    }

}
