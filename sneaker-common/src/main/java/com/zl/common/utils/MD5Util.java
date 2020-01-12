package com.zl.common.utils;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Auther: le
 * @Date: 2018/8/13 11:32
 * @Description:
 */
public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    /**
     * 第一次md5加密，用于网络传输
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass){
        String str = ""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次Md5加密，用于存储
     * @param formPass
     * @return
     */
    public static String formPassToDbPass(String formPass){
        String str = ""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    /**
     * 合并
     * @param input
     * @return
     */
    public static String inputPassToDbPass(String input){
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDbPass(formPass);
        return dbPass;
    }


}
