package com.mhy.cescsap.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidatorUtils {

    // ================= 预编译模式 =================
    private static final Map<String, Pattern> PRECOMPILED_PATTERNS = new HashMap<>();
    private static final Properties REGEX_CONFIG = new Properties();
    private static final String DOMAIN_PATTERN ="^https?://(?!-)(?:[A-Za-z0-9-]{1,63}(?<!-)\\.)+(?:[A-Za-z]{2,63})(?:/[^?#]*)?(?:\\?[^#]*)?(?:#.*)?$";

    static {
        // 初始化预编译正则
        initPrecompiledPatterns();
        // 加载外部配置（示例代码，实际需从文件加载）
        initConfig();
    }

    // ================= 国际化扩展 =================
    /** 国际电话号码 */
    public enum InternationalPhone {
        CHINA("^(\\+?86)?1[3-9]\\d{9}$"),
        US("^\\+1\\d{10}$"),
        UK("^\\+44\\d{9,10}$"),
        JAPAN("^\\+81\\d{9,10}$");

        final String regex;
        InternationalPhone(String regex) { this.regex = regex; }
    }

    /** 国际邮编 */
    public enum InternationalPostalCode {
        CN("^[1-9]\\d{5}$"),     // 中国
        US("^\\d{5}(-\\d{4})?$"),// 美国
        UK("^[A-Z]{1,2}\\d[A-Z\\d]? \\d[A-Z]{2}$");// 英国

        final String regex;
        InternationalPostalCode(String regex) { this.regex = regex; }
    }

    // ================= 错误处理 =================
    public enum ValidationError {
        MOBILE_FORMAT_ERROR("手机号格式错误"),
        ID_CARD_INVALID("身份证号不合法"),
        // ...其他错误类型
        CUSTOM_ERROR("自定义验证错误");

        final String message;
        ValidationError(String message) { this.message = message; }
    }

    // ================= 行业专用正则 =================
    /** ISBN 国际标准书号 */
    public static final String ISBN = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";

    /** IMEI 移动设备标识 */
    public static final String IMEI = "^\\d{15,17}$";

    /** 快递单号（主流快递公司） */
    public static final String EXPRESS_NO = "^[A-Za-z0-9]{10,20}$";

    // ================= 初始化方法 =================
    private static void initPrecompiledPatterns() {
        addPattern("MOBILE", InternationalPhone.CHINA.regex);
        addPattern("ID_CARD", "(^\\d{15}$)|(^\\d{17}([0-9]|X|x)$)");
        addPattern("EMAIL", "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        // 添加其他预编译模式...
    }

    private static void addPattern(String key, String regex) {
        PRECOMPILED_PATTERNS.put(key, Pattern.compile(regex));
    }

    private static void initConfig() {
        REGEX_CONFIG.put("custom.date", "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-...");
        // 从regex-config.properties加载配置
    }

    // ================= 增强验证方法 =================
    /**
     * 带错误信息的验证
     * @return 错误类型，验证通过返回null
     */
    public static ValidationError validateWithError(String input, String patternKey) {
        Pattern pattern = PRECOMPILED_PATTERNS.get(patternKey);
        if (pattern == null) return ValidationError.CUSTOM_ERROR;
        return pattern.matcher(input).matches() ? null : mapError(patternKey);
    }

    /** 动态加载配置中的正则 */
    public static boolean validateByConfig(String input, String configKey) {
        String regex = REGEX_CONFIG.getProperty(configKey);
        return regex != null && Pattern.matches(regex, input);
    }

    // ================= 数据脱敏处理 =================
    public static String maskMobile(String mobile) {
        return replaceGroup(mobile, InternationalPhone.CHINA.regex, 0,
                m -> m.group().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
    }

    public static String maskIdCard(String idCard) {
        return replaceGroup(idCard, "(\\d{4})\\d{10}(\\w{4})", "$1****$2");
    }

    // ================= 工具方法 =================
    private static String replaceGroup(String input, String regex,
                                       String replacement) {
        return input.replaceAll(regex, replacement);
    }

    private static String replaceGroup(String input, String regex,
                                       int group, java.util.function.Function<Matcher, String> replacer) {
        Matcher m = Pattern.compile(regex).matcher(input);
        return m.replaceAll(r -> replacer.apply(m));
    }

    private static ValidationError mapError(String patternKey) {
        // 实现模式键到错误类型的映射
        return ValidationError.CUSTOM_ERROR;
    }

    // ================= 基础验证 =================
    /** 中国手机号（宽松匹配） */
    public static final String MOBILE = "^1[3-9]\\d{9}$";

    /** 身份证号（15位/18位，宽松） */
    public static final String ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X|x)$)";

    /** 中文姓名（2-10个汉字，含少数民族·符号） */
    public static final String CHINESE_NAME = "^[\\u4e00-\\u9fa5]{2,10}(·[\\u4e00-\\u9fa5]+)*$";

    /** 邮箱（简化版） */
    public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /** URL网址 */
    public static final String URL = "^((https|http|ftp|rtsp|mms)?://)[^\\s]+";


    // ================= 数字验证 =================
    /** 正数（包含小数） */
    public static final String POSITIVE_NUMBER = "^\\d+(\\.\\d+)?$";

    /** 整数（正负） */
    public static final String INTEGER = "^-?\\d+$";

    /** 百分比（0-100，可含小数） */
    public static final String PERCENT = "^(100(\\.0+)?|\\d{1,2}(\\.\\d+)?)%$";


    // ================= 格式验证 =================
    /** 日期（YYYY-MM-DD） */
    public static final String DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31))$";

    /** 时间（HH:mm:ss 24小时制） */
    public static final String TIME = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";

    /** 车牌号（新能源+传统） */
    public static final String LICENSE_PLATE = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-HJ-NP-Z][A-HJ-NP-Z0-9]{4,5}[A-HJ-NP-Z0-9挂学警港澳]$";


    // ================= 网络相关 =================
    /** IPv4地址 */
    public static final String IPV4 = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";

    /** IPv6地址（简化版） */
    public static final String IPV6 = "^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$";

    /** MAC地址 */
    public static final String MAC = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";


    // ================= 业务相关 =================
    /** 统一社会信用代码 */
    public static final String UNIFIED_CREDIT_CODE = "^[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}$";

    /** 银行卡号（16-19位数字） */
    public static final String BANK_CARD = "^\\d{16,19}$";

    /** 邮政编码（中国） */
    public static final String POSTAL_CODE = "^[1-9]\\d{5}$";


    // ================= 安全相关 =================
    /** 密码强度（至少8位，含大小写字母+数字+特殊符号） */
    public static final String STRONG_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=[\\]{};':\",./<>?]).{8,}$";

    /** 弱密码（纯数字或纯字母） */
    public static final String WEAK_PASSWORD = "^\\d+$|^[a-zA-Z]+$";


    // ================= 工具方法 =================
    /**
     * 通用正则验证
     * @param input 待验证字符串
     * @param regex 正则表达式
     * @return 是否匹配
     */
    public static boolean validate(String input, String regex) {
        if (input == null || regex == null) return false;
        return Pattern.compile(regex).matcher(input).matches();
    }

    /**
     * 批量验证（全通过返回true）
     * @param input 待验证字符串
     * @param regexes 多个正则表达式
     */
    public static boolean validateAll(String input, String... regexes) {
        for (String regex : regexes) {
            if (!validate(input, regex)) return false;
        }
        return true;
    }

    /**
     * 批量验证（任意一个通过返回true）
     * @param input 待验证字符串
     * @param regexes 多个正则表达式
     */
    public static boolean validateAny(String input, String... regexes) {
        for (String regex : regexes) {
            if (validate(input, regex)) return true;
        }
        return false;
    }

    // ================= 快捷方法 =================
    public static boolean isMobile(String input) {
        return validate(input, MOBILE);
    }

    public static boolean isEmail(String input) {
        return validate(input, EMAIL);
    }

    public static boolean isIdCard(String input) {
        return validate(input, ID_CARD);
    }

    /**
     * 验证域名是否合法  
     * @param domain 要验证的域名  
     * @return 如果域名合法，则返回true；否则返回false  
     */
    public static boolean isValidDomain(String domain) {
        return Pattern.matches(DOMAIN_PATTERN, domain);
    }

    // ...其他快捷方法根据需求添加
    
}