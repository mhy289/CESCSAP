package com.mhy.cescsap.utils;

import java.security.SecureRandom;

public class AccountGeneratorUtils {

    private static final long MIN = 1_000_000_000L;
    private static final long MAX = 9_999_999_999L;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成一个十位随机账号
     */
    public static long generateAccount() {
        long bound = MAX - MIN + 1;
        long r = Math.abs(RANDOM.nextLong()) % bound;
        return MIN + r;
    }
}
