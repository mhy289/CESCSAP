package com.mhy.cescsap.utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AcademicTermUtils {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 将时间字符串归类到对应的学年度学期
     *
     * @param dateStr 时间字符串 (格式：yyyy-MM-dd HH:mm:ss)
     * @return 学年度学期描述 (示例：2024-2025学年度下学期)
     */
    public static String classifyAcademicTerm(String dateStr) {
        LocalDateTime date = parseDate(dateStr);
        int year = date.getYear();
        int month = date.getMonthValue();

        // 判断学期和学年
        if (month >= 2 && month <= 8) {  // 2月-8月：当前学年度的下学期
            return String.format("%d-%d学年度下学期", year - 1, year);
        } else {  // 9月-次年1月：新学年度的上学期
            return String.format("%d-%d学年度上学期", year, year + 1);
        }
    }

    /**
     * 解析时间字符串
     *
     * @throws IllegalArgumentException 如果格式无效
     */
    private static LocalDateTime parseDate(String dateStr) {
        try {
            return LocalDateTime.parse(dateStr, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("无效时间格式，要求格式：yyyy-MM-dd HH:mm:ss");
        }
    }
}