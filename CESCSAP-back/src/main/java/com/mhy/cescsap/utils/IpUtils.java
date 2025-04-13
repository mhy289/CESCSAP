package com.mhy.cescsap.utils;

import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class IpUtils {

    private static final Pattern IPv4_PATTERN =
            Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern IPv6_PATTERN =
            Pattern.compile("^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    /**
     * 获取客户端真实IP地址（增强IPv6支持）
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = Arrays.stream(HEADERS_TO_TRY)
                .map(request::getHeader)
                .filter(IpUtils::isValidIp)
                .findFirst()
                .orElseGet(request::getRemoteAddr);

        return processIp(ip.trim());
    }

    /**
     * 获取服务器IPv4/IPv6地址
     */
    public static String getServerIpAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            return processIp("::1");
        }
    }

    private static String processIp(String ip) {
        // 处理本地地址
        if (isLocalAddress(ip)) {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                return "127.0.0.1";
            }
        }

        // 标准化IPv6地址
        if (isIPv6Address(ip)) {
            return normalizeIPv6(ip);
        }
        return ip;
    }

    private static boolean isValidIp(String ip) {
        return ip != null &&
                !ip.isEmpty() &&
                !"unknown".equalsIgnoreCase(ip) &&
                (isIPv4Address(ip) || isIPv6Address(ip));
    }

    public static boolean isIPv4Address(String ip) {
        return IPv4_PATTERN.matcher(ip).matches();
    }

    public static boolean isIPv6Address(String ip) {
        String compact = ip.replace("::", ":");
        return IPv6_PATTERN.matcher(compact).matches() ||
                ip.contains(":");
    }

    private static boolean isLocalAddress(String ip) {
        return ip.equals("127.0.0.1") ||
                ip.equals("0:0:0:0:0:0:0:1") ||
                ip.equals("::1");
    }

    /**
     * 标准化IPv6地址表示
     */
    private static String normalizeIPv6(String ip) {
        try {
            return InetAddress.getByName(ip).getHostAddress()
                    .replaceAll("((?::0\\b){2,}):?(?!\\S*\\b\\1:0\\b)(\\S*)", "::$2")
                    .toLowerCase();
        } catch (UnknownHostException e) {
            return ip;
        }
    }
}