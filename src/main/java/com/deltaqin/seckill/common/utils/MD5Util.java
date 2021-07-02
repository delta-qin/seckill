package com.deltaqin.seckill.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author deltaqin
 * @date 2021/6/13 下午9:35
 */

// 密码两次md5工具类
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "deltaqin";

    /**
     *   两次md5
     *    使用了两个盐值，一个是默认的，一个是用户传递过来的
     * @param inputPass  输入值
     * @param saltDB 用户自定义的盐值
     * @return
     */
    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
        formPass = md5(formPass);
        String dbPass = ""+saltDB.charAt(0)+saltDB.charAt(2) + formPass +saltDB.charAt(5) + saltDB.charAt(4);
        dbPass = md5(dbPass);
        return dbPass;
    }

    public static void main(String[] args) {
		System.out.println(inputPassToDbPass("123456", "niubiqin"));
    }
}
