package com.cs.proxy.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CommonUtils {

    public static String generatePrimaryKey() {
        // 获取当前时间戳（毫秒级别）
        long currentTimeMillis = System.currentTimeMillis();

        // 获取时间戳的后10位
        long timeComponent = currentTimeMillis % 10000000000L;

        // 生成一个5位的随机数
        Random random = new Random();
        int randomComponent = 10000 + random.nextInt(90000);

        // 组合时间戳和随机数，形成15位主键
        String primaryKey = String.format("%010d%05d", timeComponent, randomComponent);

        return primaryKey;
    }

    /**
     * 删除文本中的标点符号
     *
     * @param text
     * @return
     */
    public static String deletePunctuation(Object text) {
        if (StringUtils.isEmpty(text)) {
            return (String) text;
        }
        // 定义一个正则表达式来匹配所有中英文标点符号
        String regex = "[\\p{P}\\p{S}]";
        // 使用replaceAll方法将匹配到的标点符号替换为空字符串
        return text.toString().replaceAll(regex, "");
    }

    public static String encodeParameters(String url) throws UnsupportedEncodingException {
        // 正则表达式匹配查询字符串中的参数对
        Pattern pattern = Pattern.compile("([^&=]+)=([^&]*)");
        Matcher matcher = pattern.matcher(url);
        StringBuffer encodedUrl = new StringBuffer();

        while (matcher.find()) {
            String paramName = matcher.group(1);
            String paramValue = matcher.group(2);
            // 对参数值进行编码，参数名保持不变
            matcher.appendReplacement(encodedUrl, paramName + "=" + URLEncoder.encode(paramValue, "UTF-8"));
        }
        matcher.appendTail(encodedUrl);

        return encodedUrl.toString();
    }

    public static String convertStringFromList(List<String> stringList) {
        if (CollectionUtils.isEmpty(stringList)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String string : stringList) {
            sb.append(string).append(" \n ");
        }
        return sb.toString();
    }
}
