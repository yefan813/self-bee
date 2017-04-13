package com.dmall.bee.util;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Velocity工具
 */
public class VelocityTools {

    /**
     * 转换名称(首字母大写)
     * @param name
     * @return
     */
    public static String getClassName(String name) {
        char[] chars = name.toCharArray();
        StringBuffer sb = new StringBuffer(CharUtils.toString(chars[0]).toUpperCase());
        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));
                    i++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    /**
     * 转换名称(全部大写)
     * @param name
     * @return
     */
    public static String getUpperClassName(String name) {
        return getClassName(name).toUpperCase();
    }

    /**
     * 转换名称(首字母小写)
     * @param name
     * @return
     */
    public static String getVarName(String name) {
        char[] chars = name.toCharArray();
        StringBuffer sb = new StringBuffer(CharUtils.toString(chars[0]).toLowerCase());
        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));
                    i++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 日期格式化
     * @param fmt
     * @return
     */
    public static String dateFormat(String fmt) {
        return new SimpleDateFormat(fmt).format(new Date());
    }

    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String upperFirstLetter(String str) {
        return String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1);
    }
}
