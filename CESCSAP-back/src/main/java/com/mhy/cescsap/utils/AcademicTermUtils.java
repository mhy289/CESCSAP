package com.mhy.cescsap.utils;

import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import io.micrometer.common.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class AcademicTermUtils {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //2025-04-19 00:59:34

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
            if (month == 1) {
                return String.format("%d-%d学年度上学期", year - 1, year);
            }
            return String.format("%d-%d学年度上学期", year, year + 1);
        }
    }

    /**
     * 解析时间字符串
     *
     * @throws IllegalArgumentException 如果格式无效
     */

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter
            .ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");

    private static LocalDateTime parseDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            throw new BusinessException(ExceptionType.TIME_ERR, "时间字符串为空");
        }

        try {
            // 1. 解析带时区的原始日期（处理英文格式）
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateStr, INPUT_FORMATTER);

            // 2. 转换为目标格式的本地时间（移除时区信息）
            String formattedDate = zonedDateTime.format(OUTPUT_FORMATTER);

            // 3. 解析为LocalDateTime（如需严格校验目标格式）
            return LocalDateTime.parse(formattedDate, OUTPUT_FORMATTER);

        } catch (DateTimeParseException e) {
            throw new BusinessException(
                    ExceptionType.TIME_ERR,
                    "无效时间格式，要求格式：EEE MMM dd HH:mm:ss zzz yyyy（如：Wed Apr 23 23:58:49 CST 2025）",
                    e
            );
        }
    }
}