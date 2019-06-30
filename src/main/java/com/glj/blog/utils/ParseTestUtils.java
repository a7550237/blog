package com.glj.blog.utils;

import org.jsoup.Jsoup;

/**
 * @author guolongjie
 * @since 2019/5/6
 */

public class ParseTestUtils {

    public static String parseHtmlForText(String html, int length) {
        String text = Jsoup.parse(html).text();
        if (length == -1) {
            return text;
        }
        text = text.replaceAll("#", "").replace("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+", "");
        return text.length() > length ? text.substring(0, length) + "..." : text;
    }

}
