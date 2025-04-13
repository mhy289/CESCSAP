package com.mhy.cescsap.utils;

public class DataMaskingUtils {
    // 姓名脱敏（显示首字）
    public static String maskName(String name) {
        if (name == null) return "";
        return name.charAt(0) + "**";
    }

    // 身份证脱敏
    public static String maskIDCard(String idCard) {
        if (idCard.length() != 18) return "****";
        return idCard.substring(0, 6) + "********" + idCard.substring(14);
    }

    // 成绩脱敏（区间化处理）
    public static String maskScore(double score) {
        if (score >= 90) return "优秀";
        else if (score >= 80) return "良好";
        else if (score >= 60) return "合格";
        else return "待提升";
    }

    // 联系方式脱敏
    public static String maskPhone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
}