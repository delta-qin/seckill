package com.deltaqin.seckill.common.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author deltaqin
 * @date 2021/6/10 下午9:35
 */
public class Encode {
    public static String encodeByMd5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 先获取加密算法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();

        // 加密字符串
        String newP = base64Encoder.encode(md5.digest(password.getBytes("utf-8")));
        return newP;
    }
}
