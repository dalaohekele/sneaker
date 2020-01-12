package com.zl.common.utils;

import java.util.Random;

/**
 * @Auther: le
 * @Date: 2018/7/27 16:50
 * @Description:
 */
public class KeyUtils {
    public static synchronized String genUniqueKey() {
        Random random = new Random();

        Integer result = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(result);

    }
}
